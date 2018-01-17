package com.yikejian.order.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.order.api.v1.dto.Pagination;
import com.yikejian.order.api.v1.dto.OrderDto;
import com.yikejian.order.api.v1.dto.RequestOrderDto;
import com.yikejian.order.api.v1.dto.ResponseOrderDto;
import com.yikejian.order.domain.order.Order;
import com.yikejian.order.repository.OrderRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @HystrixCommand
    public Order saveOrder(OrderDto orderDto) {
        Order order;
        if (orderDto.getOrderId() != null) {
            order = orderRepository.findByOrderId(orderDto.getOrderId());
        } else {
            order = new Order();
        }
        order.fromOrderDto(orderDto);
        return orderRepository.save(order);
    }

    @HystrixCommand
    public List<Order> saveOrders(List<OrderDto> orderDtoList) {
        List<Order> orderList = Lists.newArrayList();
        for (OrderDto orderDto : orderDtoList) {
            Order order;
            if (orderDto.getOrderId() != null) {
                order = orderRepository.findByOrderId(orderDto.getOrderId());
            } else {
                order = new Order();
            }
            order.fromOrderDto(orderDto);
            orderList.add(order);
        }
        return (List<Order>) orderRepository.save(orderList);
    }

    @HystrixCommand
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        return order.toOrderDto();
    }

    @HystrixCommand
    public ResponseOrderDto getAll() {
        List<Order> orderList = (List<Order>) orderRepository.findAll();
        List<OrderDto> orderDtoList = Lists.newArrayList(
                orderList.stream().map(Order::toOrderDto).collect(Collectors.toList())
        );
        return new ResponseOrderDto(orderDtoList);
    }

    @HystrixCommand
    public ResponseOrderDto getOrders(RequestOrderDto requestOrderDto) {
        Pagination pagination;
        if (requestOrderDto != null && requestOrderDto.getPagination() != null) {
            pagination = requestOrderDto.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestOrderDto != null && requestOrderDto.getSort() != null) {
            sort = new Sort(requestOrderDto.getSort().getDirection(), requestOrderDto.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrentPage(),
                pagination.getPageSize(),
                sort);
        Page<Order> page = orderRepository.findAll(orderSpec(requestOrderDto.getOrder()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalSize(page.getTotalElements());
        List<OrderDto> orderDtoList = Lists.newArrayList(page.getContent().stream().
                map(Order::toOrderDto).collect(Collectors.toList()));
        return new ResponseOrderDto(orderDtoList, pagination);
    }

    private Specification<Order> orderSpec(final OrderDto orderDto) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (orderDto != null) {
                if (StringUtils.isNotBlank(orderDto.getOrderName())) {
                    predicateList.add(cb.like(root.get("orderName").as(String.class), "%" + orderDto.getOrderName() + "%"));
                }
                if (orderDto.getStartTime() != null) {
                    predicateList.add(cb.equal(root.get("startTime").as(Integer.class), orderDto.getStartTime()));
                }
                if (orderDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("endTime").as(Integer.class), orderDto.getEndTime()));
                }
                if (orderDto.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), orderDto.getEffective()));
                }
                if (orderDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), orderDto.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
