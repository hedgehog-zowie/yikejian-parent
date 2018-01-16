package com.yikejian.order.api.vi;

import com.yikejian.order.api.vi.dto.OrderDto;
import com.yikejian.order.api.vi.dto.RequestOrderDto;
import com.yikejian.order.exception.OrderServiceException;
import com.yikejian.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * <code>OrderControllerV1</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:57
 */
@RestController
public class OrderControllerV1 {

    private OrderService orderService;

    @Autowired
    public OrderControllerV1(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/order/{order_id}", method = RequestMethod.GET)
    public ResponseEntity getOrders(final @PathVariable(value = "order_id") Long orderId) {
        // todo send log
        return Optional.ofNullable(orderService.getOrderById(orderId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new OrderServiceException("Not found order."));
    }

    @PostMapping("/order")
    public ResponseEntity addOrder(final OrderDto orderDto) {
        // todo send log
        return Optional.ofNullable(orderService.saveOrder(orderDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new OrderServiceException("Not found order."));
    }

    @PutMapping("/order")
    public ResponseEntity updateOrder(final OrderDto orderDto) {
        // todo send log
        return Optional.ofNullable(orderService.saveOrder(orderDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new OrderServiceException("Not found order."));
    }

    @GetMapping("/orders")
    public ResponseEntity getOrders(final RequestOrderDto requestOrderDto) {
        // todo send log
        return Optional.ofNullable(orderService.getOrders(requestOrderDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new OrderServiceException("Not found any order."));
    }

}
