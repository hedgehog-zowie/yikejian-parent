package com.yikejian.product.api.vi;

import com.yikejian.product.api.vi.dto.ProductDto;
import com.yikejian.product.api.vi.dto.RequestProductDto;
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
public class ProductControllerV1 {

    private ProductService productService;

    @Autowired
    public ProductControllerV1(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/product/{product_id}", method = RequestMethod.GET)
    public ResponseEntity getProducts(final @PathVariable(value = "product_id") Long productId) {
        // todo send log
        return Optional.ofNullable(productService.getProductById(productId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new ProductServiceException("Not found product."));
    }

    @PostMapping("/product")
    public ResponseEntity addProduct(final ProductDto productDto) {
        // todo send log
        return Optional.ofNullable(productService.saveProduct(productDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new ProductServiceException("Not found product."));
    }

    @PutMapping("/product")
    public ResponseEntity updateProduct(final ProductDto productDto) {
        // todo send log
        return Optional.ofNullable(productService.saveProduct(productDto))
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
