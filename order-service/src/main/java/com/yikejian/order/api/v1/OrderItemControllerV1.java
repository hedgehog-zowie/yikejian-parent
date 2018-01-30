package com.yikejian.order.api.v1;

import com.yikejian.order.api.v1.dto.RequestOrderItem;
import com.yikejian.order.exception.OrderServiceException;
import com.yikejian.order.service.OrderService;
import com.yikejian.order.util.JsonUtils;
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
public class OrderItemControllerV1 {

    private OrderService orderService;

    @Autowired
    public OrderItemControllerV1(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/order/{order_id}/items", method = RequestMethod.GET)
    public ResponseEntity getOrderItemsOfOneOrder(final @PathVariable(value = "order_id") Long orderId) {
        // todo send log
        return Optional.ofNullable(orderService.getOrderById(orderId).getOrderItems())
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new OrderServiceException("Not found order item."));
    }

    @GetMapping("/items")
    public ResponseEntity getOrderItems(final @RequestParam(value = "params", required = false) String params) {
        // todo send log
        RequestOrderItem requestOrderItem;
        try {
            requestOrderItem = JsonUtils.fromJson(URLDecoder.decode(params, "UTF-8"), RequestOrderItem.class);
        } catch (UnsupportedEncodingException e) {
            throw new OrderServiceException(e.getLocalizedMessage());
        }
        requestOrderItem.getOrderItem().setDeleted(0);
        return Optional.ofNullable(orderService.getOrderItems(requestOrderItem))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new OrderServiceException("Not found any order item."));
    }

}
