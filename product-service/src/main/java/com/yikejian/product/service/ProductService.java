package com.yikejian.product.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.product.api.v1.dto.Pagination;
import com.yikejian.product.api.v1.dto.ProductDto;
import com.yikejian.product.api.v1.dto.RequestProductDto;
import com.yikejian.product.api.v1.dto.ResponseProductDto;
import com.yikejian.product.domain.product.Product;
import com.yikejian.product.repository.ProductRepository;
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
 * <code>ProductService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @HystrixCommand
    public Product saveProduct(ProductDto productDto) {
        Product product;
        if (productDto.getProductId() != null) {
            product = productRepository.findByProductId(productDto.getProductId());
        } else {
            product = new Product();
        }
        product.fromProductDto(productDto);
        return productRepository.save(product);
    }

    @HystrixCommand
    public List<Product> saveProducts(List<ProductDto> productDtoList) {
        List<Product> productList = Lists.newArrayList();
        for (ProductDto productDto : productDtoList) {
            Product product;
            if (productDto.getProductId() != null) {
                product = productRepository.findByProductId(productDto.getProductId());
            } else {
                product = new Product();
            }
            product.fromProductDto(productDto);
            productList.add(product);
        }
        return (List<Product>) productRepository.save(productList);
    }

    @HystrixCommand
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findByProductId(productId);
        return product.toProductDto();
    }

    @HystrixCommand
    public ResponseProductDto getAll() {
        List<Product> productList = (List<Product>) productRepository.findAll();
        List<ProductDto> productDtoList = Lists.newArrayList(
                productList.stream().map(Product::toProductDto).collect(Collectors.toList())
        );
        return new ResponseProductDto(productDtoList);
    }

    @HystrixCommand
    public ResponseProductDto getProducts(RequestProductDto requestProductDto) {
        Pagination pagination;
        if (requestProductDto != null && requestProductDto.getPagination() != null) {
            pagination = requestProductDto.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestProductDto != null && requestProductDto.getSort() != null) {
            sort = new Sort(requestProductDto.getSort().getDirection(), requestProductDto.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrentPage(),
                pagination.getPageSize(),
                sort);
        Page<Product> page = productRepository.findAll(productSpec(requestProductDto.getProduct()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalSize(page.getTotalElements());
        List<ProductDto> productDtoList = Lists.newArrayList(page.getContent().stream().
                map(Product::toProductDto).collect(Collectors.toList()));
        return new ResponseProductDto(productDtoList, pagination);
    }

    private Specification<Product> productSpec(final ProductDto productDto) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (productDto != null) {
                if (StringUtils.isNotBlank(productDto.getProductName())) {
                    predicateList.add(cb.like(root.get("productName").as(String.class), "%" + productDto.getProductName() + "%"));
                }
                if (productDto.getStartTime() != null) {
                    predicateList.add(cb.equal(root.get("startTime").as(Integer.class), productDto.getStartTime()));
                }
                if (productDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("endTime").as(Integer.class), productDto.getEndTime()));
                }
                if (productDto.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), productDto.getEffective()));
                }
                if (productDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), productDto.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
