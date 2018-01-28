package com.yikejian.product.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.product.api.v1.dto.RequestProduct;
import com.yikejian.product.api.v1.dto.ResponseProduct;
import com.yikejian.product.domain.product.Product;
import com.yikejian.product.repository.ProductRepository;
import com.yikejian.user.api.v1.dto.Pagination;
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
    public Product saveProduct(Product product) {
        return productRepository.save(transform(product));
    }

    @HystrixCommand
    public List<Product> saveProducts(List<Product> productList) {
        return (List<Product>) productRepository.save(
                Lists.newArrayList(productList.stream().
                        map(this::transform).collect(Collectors.toList()))
        );
    }

    private Product transform(Product product) {
        Product newProduct = product;
        if (product.getProductId() != null) {
            Product oldProduct = productRepository.findByProductId(product.getProductId());
            newProduct = oldProduct.mergeOther(product);
        }
        return newProduct;
    }

    @HystrixCommand
    public Product getProductById(Long productId) {
        return productRepository.findByProductId(productId);
    }

    @HystrixCommand
    public ResponseProduct getAll() {
        return new ResponseProduct((List<Product>) productRepository.findAll());
    }

    @HystrixCommand
    public List<Product> getAllEffectiveStores(){
        return productRepository.findByEffectiveAndDeleted(1, 0);
    }

    @HystrixCommand
    public ResponseProduct getProducts(RequestProduct requestProduct) {
        Pagination pagination;
        if (requestProduct != null && requestProduct.getPagination() != null) {
            pagination = requestProduct.getPagination();
        } else {
            pagination = new Pagination();
        }

        String filed = "lastModifiedAt";
        Sort.Direction direction = Sort.Direction.DESC;
        if (requestProduct != null && requestProduct.getSorter() != null) {
            if (requestProduct.getSorter().getField() != null) {
                filed = requestProduct.getSorter().getField();
            }
            if ("ascend".equals(requestProduct.getSorter().getOrder())) {
                direction = Sort.Direction.ASC;
            }
        }
        Sort sort = new Sort(direction, filed);

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrent() - 1,
                pagination.getPageSize(),
                sort);
        Page<Product> page = productRepository.findAll(productSpec(requestProduct.getProduct()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotal(page.getTotalElements());

        return new ResponseProduct(page.getContent(), pagination);
    }

    private Specification<Product> productSpec(final Product product) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (product != null) {
                if (StringUtils.isNotBlank(product.getProductName())) {
                    predicateList.add(cb.like(root.get("productName").as(String.class), "%" + product.getProductName() + "%"));
                }
                if (product.getStartTime() != null) {
                    predicateList.add(cb.equal(root.get("startTime").as(String.class), product.getStartTime()));
                }
                if (product.getEndTime() != null) {
                    predicateList.add(cb.equal(root.get("endTime").as(String.class), product.getEndTime()));
                }
                if (product.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), product.getEffective()));
                }
                if (product.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), product.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
