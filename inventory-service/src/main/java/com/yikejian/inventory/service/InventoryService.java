package com.yikejian.inventory.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.inventory.api.v1.dto.InventoryItemsDto;
import com.yikejian.inventory.api.v1.dto.OrderItemDto;
import com.yikejian.inventory.api.v1.dto.RequestOrderItem;
import com.yikejian.inventory.api.v1.dto.ResponseOrderItem;
import com.yikejian.inventory.domain.inventory.Inventory;
import com.yikejian.inventory.domain.inventory.InventoryEvent;
import com.yikejian.inventory.domain.inventory.InventoryEventType;
import com.yikejian.inventory.domain.order.Order;
import com.yikejian.inventory.domain.order.OrderItem;
import com.yikejian.inventory.domain.product.Product;
import com.yikejian.inventory.domain.store.Device;
import com.yikejian.inventory.domain.store.DeviceProduct;
import com.yikejian.inventory.domain.store.Store;
import com.yikejian.inventory.domain.store.StoreProduct;
import com.yikejian.inventory.exception.InventoryExceptionCode;
import com.yikejian.inventory.exception.InventoryServiceException;
import com.yikejian.inventory.repository.InventoryEventRepository;
import com.yikejian.inventory.repository.InventoryRepository;
import com.yikejian.inventory.util.DateUtils;
import com.yikejian.inventory.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * <code>InventoryService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Service
public class InventoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

    private InventoryRepository inventoryRepository;
    private InventoryEventRepository inventoryEventRepository;
    private OAuth2RestTemplate oAuth2RestTemplate;

    private static final Integer INIT_DAYS = 5;

    private ReentrantReadWriteLock inventoryLock = new ReentrantReadWriteLock();

    @Value("${yikejian.api.store.url}")
    private String storeApi;
    @Value("${yikejian.api.product.url}")
    private String productApi;
    @Value("${yikejian.api.order.item.url}")
    private String orderItemApi;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository,
                            InventoryEventRepository inventoryEventRepository,
                            OAuth2RestTemplate oAuth2RestTemplate) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryEventRepository = inventoryEventRepository;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @HystrixCommand
    public Inventory getInventoryById(Long inventoryId) {
        Inventory inventory = inventoryRepository.findByInventoryId(inventoryId);
//        Flux<InventoryEvent> inventoryEvents =
//                Flux.fromStream(inventoryEventRepository.findByInventoryId(inventory.getInventoryId()));
//        Inventory newInventory = inventoryEvents.reduceWith(() -> inventory, Inventory::incorporate).get();
//        if (!inventory.equals(newInventory)) {
//            saveInventory(inventory);
//        }
        return inventory;
    }

    @HystrixCommand
    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(transform(inventory));
    }

    @HystrixCommand
    public List<Inventory> saveInventories(List<Inventory> inventoryList) {
        return (List<Inventory>) inventoryRepository.save(
                Lists.newArrayList(inventoryList.stream().
                        map(this::transform).collect(Collectors.toList()))
        );
    }

    private Inventory transform(Inventory inventory) {
        Inventory newInventory = inventory;
        if (inventory.getInventoryId() != null) {
            Inventory oldInventory = inventoryRepository.findByInventoryId(inventory.getInventoryId());
            newInventory = oldInventory.mergeOther(inventory);
        }
        return newInventory;
    }

    @HystrixCommand
    public List<InventoryItemsDto> getInventoryWithItems(Inventory param) {
        List<Inventory> inventoryList = getInventories(param);
        List<InventoryItemsDto> inventoryItemsDtoList = Lists.newArrayList();
        for (Inventory inventory : inventoryList) {
            Order order = new Order();
            order.setStoreId(inventory.getStoreId());
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(inventory.getProductId());
            orderItem.setBookedTime(inventory.getPieceTime());
            orderItem.setOrder(order);
            RequestOrderItem requestOrderItem = new RequestOrderItem();
            requestOrderItem.setOrderItem(orderItem);
            String queryItemsParams = JsonUtils.toJson(requestOrderItem);
            String url;
            try {
                url = orderItemApi + "?params=" + URLEncoder.encode(queryItemsParams, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new InventoryServiceException(e, InventoryExceptionCode.OTHER_ERROR);
            }
            ResponseOrderItem responseOrderItem = oAuth2RestTemplate.getForObject(url, ResponseOrderItem.class);
            List<OrderItemDto> orderItemDtoList = Lists.newArrayList();
            for (OrderItem item : responseOrderItem.getList()) {
                OrderItemDto orderItemDto = new OrderItemDto(item.getOrderItemId(), item.getOrderCode(), item.getExperiencer(), item.getOrderItemStatus());
                orderItemDtoList.add(orderItemDto);
            }
            InventoryItemsDto inventoryItemsDto = new InventoryItemsDto();
            inventoryItemsDto.setPieceTime(inventory.getPieceTime());
            inventoryItemsDto.setRestStock(inventory.getRestStock());
            inventoryItemsDto.setBookedStock(inventory.getBookedStock());
            inventoryItemsDto.setItems(orderItemDtoList);
            inventoryItemsDtoList.add(inventoryItemsDto);
        }
        return inventoryItemsDtoList;
    }

    @HystrixCommand
    public List<Inventory> getInventories(Inventory inventory) {
        Sort sort = new Sort(Sort.Direction.ASC, "pieceTime");
        List<Inventory> inventoryList = inventoryRepository.findAll(inventorySpec(inventory), sort);
        List<Inventory> computedInventoryList = Lists.newArrayList(
                inventoryList.stream().map(this::recomputeInventory).collect(Collectors.toList())
        );
        for (Inventory computedInventory : computedInventoryList) {
            Integer restStock = computedInventory.getStock() - computedInventory.getBookedStock();
            Integer restResourceNumber = computedInventory.getRestResourceNumber();
            restStock = restStock > restResourceNumber ? restResourceNumber : restStock;
            computedInventory.setRestStock(restStock);
        }
        return computedInventoryList;
    }

    private Specification<Inventory> inventorySpec(final Inventory inventory) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (inventory != null) {
                if (StringUtils.isNotBlank(inventory.getDay())) {
                    predicateList.add(cb.equal(root.get("day").as(String.class), inventory.getDay()));
                }
                if (inventory.getStoreId() != null) {
                    predicateList.add(cb.equal(root.get("storeId").as(Integer.class), inventory.getStoreId()));
                }
                if (inventory.getProductId() != null) {
                    predicateList.add(cb.equal(root.get("productId").as(Integer.class), inventory.getProductId()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

    @Transactional
    public List<Inventory> initInventoryOfStore(Store store) {
        LocalDate currentDate = LocalDate.now();
        List<Inventory> allInventorySet = Lists.newArrayList();
        int i = 0;
        while (i < INIT_DAYS) {
            String day = DateUtils.dateToDayStr(currentDate);
            currentDate = currentDate.plusDays(1);
            allInventorySet.addAll(initInventoryOfStore(store, day));
            i++;
        }
        return allInventorySet;
    }

    @Transactional
    public List<Inventory> initInventoryOfStore(Store store, String day) {
        if (store == null) {
            String msg = "init inventory error. store is null.";
            LOGGER.error(msg);
            throw new InventoryServiceException(InventoryExceptionCode.ILLEGAL_STORE, msg);
        }
        try {
            DateUtils.dayStrToDate(day);
        } catch (RuntimeException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new InventoryServiceException(InventoryExceptionCode.ILLEGAL_DAY, e.getLocalizedMessage());
        }

        List<Inventory> result;
        inventoryLock.writeLock().lock();
        try {
            Long storeId = store.getStoreId();
            Integer unitDuration = store.getUnitDuration();
            Set<Device> deviceSet = store.getDeviceSet();
            Map<Long, Integer> productDeviceNums = getProductDeviceNum(deviceSet);
            Set<Inventory> allInventorySet = Sets.newHashSet();
            for (StoreProduct storeProduct : store.getStoreProductSet()) {
                Long productId = storeProduct.getProductId();
                Product product = oAuth2RestTemplate.getForObject(productApi + "/product/" + productId, Product.class);
                if (product == null) {
                    String msg = "init inventory error, not found product for id: " + productId;
                    LOGGER.error(msg);
                    throw new InventoryServiceException(InventoryExceptionCode.ILLEGAL_PRODUCT, msg);
                }
                inventoryRepository.deleteByStoreIdAndProductIdAndDay(storeId, productId, day);
                List<String> pieceTimeList = DateUtils.generatePieceTimeOfDay(
                        day,
                        store.getStartTime().compareTo(product.getStartTime()) < 0 ? product.getStartTime() : store.getStartTime(),
                        store.getEndTime().compareTo(product.getEndTime()) > 0 ? product.getEndTime() : store.getEndTime(),
                        unitDuration
                );
                Set<Inventory> newInventorySet = Sets.newHashSet();
                Integer resourceNumber = productDeviceNums.get(productId) == null ? 0 : productDeviceNums.get(productId);
                for (String pieceTime : pieceTimeList) {
                    Inventory inventory = new Inventory(storeId,
                            productId,
                            store.getUnitTimes() > resourceNumber ? resourceNumber : store.getUnitTimes(),
                            resourceNumber,
                            resourceNumber,
                            day,
                            pieceTime);
                    newInventorySet.add(recomputeInventory(inventory));
                }
                allInventorySet.addAll(newInventorySet);
            }
            for (Inventory inventory : allInventorySet) {
                checkInventory(inventory);
            }
            result = (List<Inventory>) inventoryRepository.save(allInventorySet);
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new InventoryServiceException(InventoryExceptionCode.OTHER_ERROR, e.getLocalizedMessage());
        } finally {
            inventoryLock.writeLock().unlock();
        }
        return result;
    }

    /**
     * 获得各个产品支持的设备数量
     *
     * @param deviceSet
     */
    private Map<Long, Integer> getProductDeviceNum(Set<Device> deviceSet) {
        Map<Long, Integer> productDeviceNums = Maps.newHashMap();
        for (Device device : deviceSet) {
            for (DeviceProduct deviceProduct : device.getDeviceProductSet()) {
                if (deviceProduct.getDeleted() == 1 || deviceProduct.getEffective() == 0) {
                    continue;
                }
                Long productId = deviceProduct.getProductId();
                Integer number = productDeviceNums.get(productId);
                if (number == null) {
                    productDeviceNums.put(productId, 0);
                }
                productDeviceNums.put(productId, productDeviceNums.get(productId) + 1);
            }
        }
        return productDeviceNums;
    }

    @Deprecated
    public InventoryEvent addInventoryEvent(InventoryEvent inventoryEvent) {
        return inventoryEventRepository.save(inventoryEvent);
    }

    @HystrixCommand
    @Transactional
    public List<Inventory> changeInventoryOnOrderChange(Order order) {
        Long storeId = order.getStoreId();
        Store store = oAuth2RestTemplate.getForObject(storeApi + "/store/" + storeId, Store.class);
        Integer unitDuration = store.getUnitDuration();
        List<Inventory> inventoryList = Lists.newArrayList();
        List<InventoryEvent> inventoryEventList = Lists.newArrayList();
        List<Inventory> computedInventoryList;
        inventoryLock.writeLock().lock();
        try {
            for (OrderItem orderItem : order.getOrderItems()) {
                String pieceTime = orderItem.getBookedTime();
                Long productId = orderItem.getProductId();
                Product product = oAuth2RestTemplate.getForObject(productApi + "/product/" + productId, Product.class);
                Integer duration = product.getDuration();
                List<String> affectedPieceTimeList = DateUtils.getNeighborPieceTime(
                        pieceTime,
                        store.getStartTime().compareTo(product.getStartTime()) < 0 ? product.getStartTime() : store.getStartTime(),
                        store.getEndTime().compareTo(product.getEndTime()) > 0 ? product.getEndTime() : store.getEndTime(),
                        duration,
                        unitDuration);
                Inventory inventory = inventoryRepository.findByStoreIdAndProductIdAndPieceTime(storeId, productId, pieceTime);
                InventoryEvent inventoryEvent = new InventoryEvent(inventory.getStoreId(), inventory.getProductId(), inventory.getPieceTime());
                switch (order.getOrderStatus()) {
                    case CREATED:
                        inventoryEvent.setInventoryEventType(InventoryEventType.INCREASE_STOCK);
                        break;
                    case CANCELED:
                        inventoryEvent.setInventoryEventType(InventoryEventType.DECREASE_STOCK);
                        break;
                    default:
                        String msg = "order status is not illegal.";
                        LOGGER.error(msg);
                        throw new InventoryServiceException(InventoryExceptionCode.ILLEGAL_ORDER, msg);
                }
                inventoryEventList.add(inventoryEvent);
                for (String affectedPieceTime : affectedPieceTimeList) {
                    Inventory affectedInventory = inventoryRepository.findByStoreIdAndProductIdAndPieceTime(storeId, productId, affectedPieceTime);
                    if (affectedInventory == null) {
                        LOGGER.error("not exists inventory. storeId = {}, productId = {}, pieceTime = {}.", storeId, productId, affectedPieceTime);
                        throw new InventoryServiceException(InventoryExceptionCode.NOT_EXISTS_INVENTORY, "not exists inventory.");
                    }
                    inventoryList.add(affectedInventory);
                    InventoryEvent affectedInventoryEvent = new InventoryEvent(affectedInventory.getStoreId(), affectedInventory.getProductId(), affectedInventory.getPieceTime());
                    switch (order.getOrderStatus()) {
                        case CREATED:
                            affectedInventoryEvent.setInventoryEventType(InventoryEventType.DECREASE_RESOURCE);
                            break;
                        case CANCELED:
                            affectedInventoryEvent.setInventoryEventType(InventoryEventType.INCREASE_RESOURCE);
                            break;
                        default:
                            String msg = "order status is not illegal.";
                            LOGGER.error(msg);
                            throw new InventoryServiceException(InventoryExceptionCode.ILLEGAL_ORDER, msg);
                    }
                    inventoryEventList.add(affectedInventoryEvent);
                }
            }
            inventoryEventRepository.save(inventoryEventList);
            computedInventoryList = Lists.newArrayList(
                    inventoryList.stream().map(this::recomputeInventory).collect(Collectors.toList())
            );
            for (Inventory inventory : computedInventoryList) {
                checkInventory(inventory);
            }
            // TODO: 2018/2/4 need not change computedInventory, don't need to persist bookedInventory.
            inventoryRepository.save(computedInventoryList);
        } finally {
            inventoryLock.writeLock().unlock();
        }
        return computedInventoryList;
    }

    private void checkInventory(Inventory inventory) {
        Integer restStock = inventory.getStock() - inventory.getBookedStock();
        Integer restResourceNumber = inventory.getRestResourceNumber();
        restStock = restStock > restResourceNumber ? restResourceNumber : restStock;
        if (restStock < 0) {
            String msg = "not exist enough inventory.";
            LOGGER.error(msg);
            throw new InventoryServiceException(InventoryExceptionCode.INSUFFICIENT_INVENTORY, msg);
        }
        inventory.setRestStock(restStock);
    }

    private Inventory recomputeInventory(Inventory inventory) {
        inventory.setBookedStock(0);
        inventory.setRestResourceNumber(inventory.getResourceNumber());
        Flux<InventoryEvent> inventoryEvents =
                Flux.fromStream(inventoryEventRepository.findByStoreIdAndProductIdAndPieceTime(
                        inventory.getStoreId(), inventory.getProductId(), inventory.getPieceTime()));
        return inventoryEvents.reduceWith(() -> inventory, Inventory::incorporate).get();
    }

}
