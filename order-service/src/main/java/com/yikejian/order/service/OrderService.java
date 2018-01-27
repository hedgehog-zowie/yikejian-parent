package com.yikejian.order.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.order.api.v1.dto.Pagination;
import com.yikejian.order.api.v1.dto.RequestOrder;
import com.yikejian.order.api.v1.dto.ResponseOrder;
import com.yikejian.order.domain.order.Order;
import com.yikejian.order.domain.order.OrderItem;
import com.yikejian.order.domain.product.Product;
import com.yikejian.order.domain.store.Store;
import com.yikejian.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
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

    @Value("${yikejian.api.store.url}")
    private String storeApi;
    @Value("${yikejian.api.product.url}")
    private String productApi;

    private OrderRepository orderRepository;
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OAuth2RestTemplate oAuth2RestTemplate) {
        this.orderRepository = orderRepository;
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
        Store store = oAuth2RestTemplate.getForObject(storeApi + "/" + storeId, Store.class);
        order.setStoreName(store.getStoreName());
        for (OrderItem orderItem : order.getOrderItemSet()) {
            Long productId = orderItem.getProductId();
            Product product = oAuth2RestTemplate.getForObject(productApi + "/" + productId, Product.class);
            orderItem.setProductName(product.getProductName());
        }
        return order;
    }

    @HystrixCommand
    public List<Order> getAllEffectiveStores() {
        List<Order> storeList = orderRepository.findByEffectiveAndDeleted(1, 0);
        return storeList;
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
//            for (OrderItem orderItem : order.getOrderItemSet()) {
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
                if (order.getCustomerId() != null) {
                    predicateList.add(cb.equal(root.get("customerId").as(Integer.class), order.getCustomerId()));
                }
                if (order.getStoreId() != null) {
                    predicateList.add(cb.equal(root.get("storeId").as(Integer.class), order.getStoreId()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

    private Order transform(Order order) {
        Order newInventory = order;
        if (order.getOrderId() != null) {
            Order oldInventory = orderRepository.findByOrderId(order.getOrderId());
            newInventory = oldInventory.mergeOther(order);
        }
        return newInventory;
    }

}
