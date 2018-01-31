package com.yikejian.order.api.v1;

import com.yikejian.order.api.v1.dto.RequestOrder;
import com.yikejian.order.domain.order.Order;
import com.yikejian.order.domain.order.OrderExtra;
import com.yikejian.order.domain.order.OrderStatus;
import com.yikejian.order.exception.OrderServiceException;
import com.yikejian.order.service.OrderService;
import com.yikejian.order.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
@RequestMapping("/v1")
public class OrderControllerV1 {

    private OrderService orderService;

    @Autowired
    public OrderControllerV1(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity addOrder(final @RequestBody Order order) {
        // todo send log
        order.setOrderId(null);
        order.setDeleted(0);
        order.setEffective(1);
        order.setOrderStatus(OrderStatus.CREATED);
        return Optional.ofNullable(orderService.saveOrder(order))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new OrderServiceException("Not found order."));
    }

    @PutMapping("/order")
    public ResponseEntity updateOrder(final @RequestBody Order order) {
        // todo send log
        return Optional.ofNullable(orderService.saveOrder(order))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new OrderServiceException("Not found order."));
    }

    @RequestMapping(value = "/order/{order_id}", method = RequestMethod.GET)
    public ResponseEntity getOrder(final @PathVariable(value = "order_id") Long orderId) {
        // todo send log
        return Optional.ofNullable(orderService.getOrderById(orderId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new OrderServiceException("Not found order."));
    }

    @RequestMapping(value = "/orders/customer/{mobile_number}", method = RequestMethod.GET)
    public ResponseEntity getOrdersOfCustomer(final @PathVariable(value = "mobile_number") String mobileNumber) {
        // todo send log
        Order order = new Order();
        order.setMobileNumber(mobileNumber);
        RequestOrder requestOrder = new RequestOrder();
        requestOrder.setOrder(order);
        return Optional.ofNullable(orderService.getOrders(requestOrder))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new OrderServiceException("Not found order."));
    }

    @GetMapping("/orders")
    public ResponseEntity getOrders(final @RequestParam(value = "params", required = false) String params) {
        // todo send log
        if (StringUtils.isBlank(params)) {
            return Optional.ofNullable(orderService.getAllEffectiveOrders())
                    .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                    .orElseThrow(() -> new OrderServiceException("Not found any store."));
        }
        RequestOrder requestOrder;
        try {
            requestOrder = JsonUtils.fromJson(URLDecoder.decode(params, "UTF-8"), RequestOrder.class);
        } catch (UnsupportedEncodingException e) {
            throw new OrderServiceException(e.getLocalizedMessage());
        }
        requestOrder.getOrder().setDeleted(0);
        return Optional.ofNullable(orderService.getOrders(requestOrder))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new OrderServiceException("Not found any order."));
    }

    @PostMapping("/order/{order_id}/extra")
    public ResponseEntity addOrderExtra(
            final @PathVariable(value = "order_id") Long orderId,
            final @RequestBody OrderExtra orderExtra) {
        // todo send log
        Order order = new Order(orderId);
        orderExtra.setOrder(order);
        orderExtra.setDeleted(0);
        orderExtra.setEffective(1);
        return Optional.ofNullable(orderService.saveOrderExtra(orderExtra))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new OrderServiceException("Not save order extra."));
    }

}
