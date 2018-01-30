package com.yikejian.order.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.order.api.v1.dto.*;
import com.yikejian.order.domain.order.Order;
import com.yikejian.order.domain.order.OrderItem;
import com.yikejian.order.domain.order.OrderItemStatus;
import com.yikejian.order.domain.product.Product;
import com.yikejian.order.domain.store.Store;
import com.yikejian.order.repository.OrderItemRepository;
import com.yikejian.order.repository.OrderRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * <code>OrderService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Service
public class OrderService {

    private static final String ORDER_TIME_FORMAT = "yyyyMMddHHmmss";
    private static final DateTimeFormatter orderTimeFormatter = DateTimeFormatter.ofPattern(ORDER_TIME_FORMAT);
    private static final AtomicLong sequenceNumber = new AtomicLong();
    private static final Integer loopNumber = 10000;

    @Value("${yikejian.api.store.url}")
    private String storeApi;
    @Value("${yikejian.api.product.url}")
    private String productApi;

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        OAuth2RestTemplate oAuth2RestTemplate) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @HystrixCommand
    public Order saveOrder(Order order) {
        return orderRepository.save(transform(order));
    }

    @HystrixCommand
    public List<Order> saveOrders(List<Order> orderList) {
        return (List<Order>) orderRepository.save(
                Lists.newArrayList(orderList.stream().
                        map(this::transform).collect(Collectors.toList()))
        );
    }

    @HystrixCommand
    public Order getOrderById(Long orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        Long storeId = order.getStoreId();
        Store store = oAuth2RestTemplate.getForObject(storeApi + "/store/" + storeId, Store.class);
        order.setStoreName(store.getStoreName());
        for (OrderItem orderItem : order.getOrderItems()) {
            Long productId = orderItem.getProductId();
            Product product = oAuth2RestTemplate.getForObject(productApi + "/product/" + productId, Product.class);
            orderItem.setProductName(product.getProductName());
        }
        return order;
    }

    @HystrixCommand
    public List<Order> getAllEffectiveOrders() {
        List<Order> orderList = orderRepository.findByEffectiveAndDeleted(1, 0);
        return orderList;
    }

    @HystrixCommand
    public ResponseOrder getAll() {
        return new ResponseOrder((List<Order>) orderRepository.findAll());
    }

    @HystrixCommand
    public ResponseOrder getOrders(RequestOrder requestOrder) {
        Pagination pagination;
        if (requestOrder != null && requestOrder.getPagination() != null) {
            pagination = requestOrder.getPagination();
        } else {
            pagination = new Pagination();
        }

        String filed = "lastModifiedAt";
        Sort.Direction direction = Sort.Direction.DESC;
        if (requestOrder != null && requestOrder.getSorter() != null) {
            if (requestOrder.getSorter().getField() != null) {
                filed = requestOrder.getSorter().getField();
            }
            if ("ascend".equals(requestOrder.getSorter().getOrder())) {
                direction = Sort.Direction.ASC;
            }
        }
        Sort sort = new Sort(direction, filed);

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrent() - 1,
                pagination.getPageSize(),
                sort);
        Page<Order> page = orderRepository.findAll(orderSpec(requestOrder.getOrder()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotal(page.getTotalElements());

//        for (Order order : page.getContent()) {
//            Long storeId = order.getStoreId();
//            Store store = oAuth2RestTemplate.getForObject(storeApi + "/" + storeId, Store.class);
//            order.setStoreName(store.getStoreName());
//            for (OrderItem orderItem : order.getOrderItems()) {
//                Long productId = orderItem.getProductId();
//                Product product = oAuth2RestTemplate.getForObject(productApi + "/" + productId, Product.class);
//                orderItem.setProductName(product.getProductName());
//            }
//        }
        return new ResponseOrder(page.getContent(), pagination);
    }

    private Specification<Order> orderSpec(final Order order) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (order != null) {
                if (order.getMobileNumber() != null) {
                    predicateList.add(cb.equal(root.get("mobileNumber").as(Integer.class), order.getMobileNumber()));
                }
                if (order.getStoreId() != null) {
                    predicateList.add(cb.equal(root.get("storeId").as(Integer.class), order.getStoreId()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

    @HystrixCommand
    public ResponseOrderItem getOrderItems(RequestOrderItem requestOrderItem) {
        Pagination pagination;
        if (requestOrderItem != null && requestOrderItem.getPagination() != null) {
            pagination = requestOrderItem.getPagination();
        } else {
            pagination = new Pagination();
        }

        String filed = "lastModifiedAt";
        Sort.Direction direction = Sort.Direction.DESC;
        if (requestOrderItem != null && requestOrderItem.getSorter() != null) {
            if (requestOrderItem.getSorter().getField() != null) {
                filed = requestOrderItem.getSorter().getField();
            }
            if ("ascend".equals(requestOrderItem.getSorter().getOrder())) {
                direction = Sort.Direction.ASC;
            }
        }
        Sort sort = new Sort(direction, filed);

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrent() - 1,
                pagination.getPageSize(),
                sort);
        Page<OrderItem> page = orderItemRepository.findAll(orderItemSpec(requestOrderItem.getOrderItem()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotal(page.getTotalElements());

        for (OrderItem orderItem : page.getContent()) {
            Product product = oAuth2RestTemplate.getForObject(productApi + "/product/" + orderItem.getProductId(), Product.class);
            orderItem.setProductName(product.getProductName());
            orderItem.setDuration(product.getDuration());
            if (orderItem.getStartAt() != null) {
                Date now = new Date();
                int progress = (int) ((now.getTime() - orderItem.getStartAt().getTime()) / (1000 * 60) / orderItem.getDuration());
                orderItem.setProgress((progress > 1 ? 1 : progress) * 100);
            }
        }
        return new ResponseOrderItem(page.getContent(), pagination);
    }

    private Specification<OrderItem> orderItemSpec(final OrderItem orderItem) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (orderItem != null) {
                if (orderItem.getOrder() != null) {
                    if (StringUtils.isNotBlank(orderItem.getOrder().getOrderCode())) {
                        Join<OrderItem, Order> join = root.join("order");
                        predicateList.add(cb.like(join.<String>get("orderCode"), "%" + orderItem.getOrder().getOrderCode() + "%"));
                    }
                    if (StringUtils.isNotBlank(orderItem.getOrder().getMobileNumber())) {
                        Join<OrderItem, Order> join = root.join("order");
                        predicateList.add(cb.like(join.<String>get("mobileNumber"), "%" + orderItem.getOrder().getMobileNumber() + "%"));
                    }
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

    private Order transform(Order order) {
        Order newOrder = order;
        if (order.getOrderId() != null) {
            orderItemRepository.deleteByOrder(order);
            Order oldOrder = orderRepository.findByOrderId(order.getOrderId());
            newOrder = oldOrder.mergeOther(order);
        }
        if (StringUtils.isBlank(newOrder.getOrderCode())) {
            newOrder.setOrderCode(generateOrderCode());
        }
        Double amount = 0D;
        if (newOrder.getOrderItems() != null && newOrder.getOrderItems().size() > 0) {
            for (OrderItem orderItem : newOrder.getOrderItems()) {
                orderItem.setEffective(1);
                orderItem.setDeleted(0);
                orderItem.setOrderCode(newOrder.getOrderCode());
                Product product = oAuth2RestTemplate.getForObject(productApi + "/product/" + orderItem.getProductId(), Product.class);
                orderItem.setProductName(product.getProductName());
                amount += product.getPrice();
                orderItem.setOrderItemStatus(OrderItemStatus.NOT_SERVE);
            }
        }
        newOrder.setAmount(amount);
        return newOrder;
    }

    private String generateOrderCode() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(orderTimeFormatter) + String.format("%04d", sequenceNumber.incrementAndGet() % loopNumber);
    }

}
