package com.yikejian.order.api.v1;

import com.yikejian.order.api.v1.dto.RequestOrder;
import com.yikejian.order.domain.order.Order;
import com.yikejian.order.exception.OrderServiceException;
import com.yikejian.order.service.OrderService;
import com.yikejian.order.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class OrderControllerV1 {

    private OrderService orderService;

    @Autowired
    public OrderControllerV1(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity addOrder(final @RequestBody Order order) {
        // todo send log
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
    public ResponseEntity getOrders(final @PathVariable(value = "order_id") Long orderId) {
        // todo send log
        return Optional.ofNullable(orderService.getOrderById(orderId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new OrderServiceException("Not found order."));
    }

    @GetMapping("/orders")
    public ResponseEntity getOrders(final @RequestParam(value = "params", required = false) String params) {
        // todo send log
        if (StringUtils.isBlank(params)) {
            return Optional.ofNullable(orderService.getAllEffectiveStores())
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

}
