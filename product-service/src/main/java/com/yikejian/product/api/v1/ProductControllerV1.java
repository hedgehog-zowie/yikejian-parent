package com.yikejian.product.api.v1;

import com.yikejian.product.api.v1.dto.RequestProductDto;
import com.yikejian.product.domain.product.Product;
import com.yikejian.product.exception.ProductServiceException;
import com.yikejian.product.service.ProductService;
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
    public ResponseEntity addProduct(final Product product) {
        // todo send log
        return Optional.ofNullable(productService.saveProduct(product))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new ProductServiceException("Not found product."));
    }

    @PutMapping("/product")
    public ResponseEntity updateProduct(final Product product) {
        // todo send log
        return Optional.ofNullable(productService.saveProduct(product))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new ProductServiceException("Not found product."));
    }

    @PutMapping("/products")
    public ResponseEntity updateProducts(final List<Product> productList) {
        // todo send log
        return Optional.ofNullable(productService.saveProducts(productList))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new ProductServiceException("Not found product."));
    }

    @RequestMapping(value = "/product/{product_id}", method = RequestMethod.GET)
    public ResponseEntity getProducts(final @PathVariable(value = "product_id") Long productId) {
        // todo send log
        return Optional.ofNullable(productService.getProductById(productId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new ProductServiceException("Not found product."));
    }

    @GetMapping("/products")
    public ResponseEntity getProducts(final RequestProductDto requestProductDto) {
        // todo send log
        return Optional.ofNullable(productService.getProducts(requestProductDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new ProductServiceException("Not found any product."));
    }

}
