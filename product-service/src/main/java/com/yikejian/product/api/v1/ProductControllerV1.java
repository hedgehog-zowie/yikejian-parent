package com.yikejian.product.api.v1;

import com.yikejian.product.api.v1.dto.RequestProduct;
import com.yikejian.product.domain.product.Product;
import com.yikejian.product.exception.ProductServiceException;
import com.yikejian.product.service.ProductService;
import com.yikejian.product.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

/**
 * <code>ProductControllerV1</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:57
 */
@RestController
@RequestMapping(path = "/v1")
public class ProductControllerV1 {

    private ProductService productService;

    @Autowired
    public ProductControllerV1(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity addProduct(@RequestBody final Product product) {
        product.setProductId(null);
        // todo send log
        return Optional.ofNullable(productService.saveProduct(product))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new ProductServiceException("Not found product."));
    }

    @PutMapping("/product")
    public ResponseEntity updateProduct(@RequestBody final Product product) {
        // todo send log
        return Optional.ofNullable(productService.saveProduct(product))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new ProductServiceException("Not found product."));
    }

    @PutMapping("/products")
    public ResponseEntity updateProducts(@RequestBody final List<Product> productList) {
        // todo send log
        return Optional.ofNullable(productService.saveProducts(productList))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new ProductServiceException("Not found product."));
    }

    @RequestMapping(value = "/product/{product_id}", method = RequestMethod.GET)
    public ResponseEntity getProduct(final @PathVariable(value = "product_id") Long productId) {
        // todo send log
        return Optional.ofNullable(productService.getProductById(productId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new ProductServiceException("Not found product."));
    }

    @GetMapping("/products")
    public ResponseEntity getProducts(final @RequestParam(value = "params", required = false) String params) {
        // todo send log
        if (StringUtils.isBlank(params)) {
            return Optional.ofNullable(productService.getAllEffectiveStores())
                    .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                    .orElseThrow(() -> new ProductServiceException("Not found any store."));
        }
        RequestProduct requestProduct;
        try {
            requestProduct = JsonUtils.fromJson(URLDecoder.decode(params, "UTF-8"), RequestProduct.class);
        } catch (UnsupportedEncodingException e) {
            throw new ProductServiceException(e.getLocalizedMessage());
        }
        requestProduct.getProduct().setDeleted(0);
        return Optional.ofNullable(productService.getProducts(requestProduct))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new ProductServiceException("Not found any product."));
    }

}
