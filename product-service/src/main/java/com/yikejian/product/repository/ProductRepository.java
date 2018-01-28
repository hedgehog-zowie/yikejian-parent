package com.yikejian.product.repository;

import com.yikejian.product.domain.product.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <code>ProductRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Product findByProductId(Long productId);

    List<Product> findByEffectiveAndDeleted(Integer effective, Integer deleted);

}
