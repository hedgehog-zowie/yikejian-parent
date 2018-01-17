package com.yikejian.inventory.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.inventory.api.v1.dto.Pagination;
import com.yikejian.inventory.api.v1.dto.InventoryDto;
import com.yikejian.inventory.api.v1.dto.RequestInventoryDto;
import com.yikejian.inventory.api.v1.dto.ResponseInventoryDto;
import com.yikejian.inventory.domain.inventory.Inventory;
import com.yikejian.inventory.repository.InventoryRepository;
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
 * <code>InventoryService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Service
public class InventoryService {

    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @HystrixCommand
    public Inventory saveInventory(InventoryDto inventoryDto) {
        Inventory inventory;
        if (inventoryDto.getInventoryId() != null) {
            inventory = inventoryRepository.findByInventoryId(inventoryDto.getInventoryId());
        } else {
            inventory = new Inventory();
        }
        inventory.fromInventoryDto(inventoryDto);
        return inventoryRepository.save(inventory);
    }

    @HystrixCommand
    public List<Inventory> saveInventorys(List<InventoryDto> inventoryDtoList) {
        List<Inventory> inventoryList = Lists.newArrayList();
        for (InventoryDto inventoryDto : inventoryDtoList) {
            Inventory inventory;
            if (inventoryDto.getInventoryId() != null) {
                inventory = inventoryRepository.findByInventoryId(inventoryDto.getInventoryId());
            } else {
                inventory = new Inventory();
            }
            inventory.fromInventoryDto(inventoryDto);
            inventoryList.add(inventory);
        }
        return (List<Inventory>) inventoryRepository.save(inventoryList);
    }

    @HystrixCommand
    public InventoryDto getInventoryById(Long inventoryId) {
        Inventory inventory = inventoryRepository.findByInventoryId(inventoryId);
        return inventory.toInventoryDto();
    }

    @HystrixCommand
    public ResponseInventoryDto getAll() {
        List<Inventory> inventoryList = (List<Inventory>) inventoryRepository.findAll();
        List<InventoryDto> inventoryDtoList = Lists.newArrayList(
                inventoryList.stream().map(Inventory::toInventoryDto).collect(Collectors.toList())
        );
        return new ResponseInventoryDto(inventoryDtoList);
    }

    @HystrixCommand
    public ResponseInventoryDto getInventorys(RequestInventoryDto requestInventoryDto) {
        Pagination pagination;
        if (requestInventoryDto != null && requestInventoryDto.getPagination() != null) {
            pagination = requestInventoryDto.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestInventoryDto != null && requestInventoryDto.getSort() != null) {
            sort = new Sort(requestInventoryDto.getSort().getDirection(), requestInventoryDto.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrentPage(),
                pagination.getPageSize(),
                sort);
        Page<Inventory> page = inventoryRepository.findAll(inventorySpec(requestInventoryDto.getInventory()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalSize(page.getTotalElements());
        List<InventoryDto> inventoryDtoList = Lists.newArrayList(page.getContent().stream().
                map(Inventory::toInventoryDto).collect(Collectors.toList()));
        return new ResponseInventoryDto(inventoryDtoList, pagination);
    }

    private Specification<Inventory> inventorySpec(final InventoryDto inventoryDto) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (inventoryDto != null) {
                if (StringUtils.isNotBlank(inventoryDto.getInventoryName())) {
                    predicateList.add(cb.like(root.get("inventoryName").as(String.class), "%" + inventoryDto.getInventoryName() + "%"));
                }
                if (inventoryDto.getStartTime() != null) {
                    predicateList.add(cb.equal(root.get("startTime").as(Integer.class), inventoryDto.getStartTime()));
                }
                if (inventoryDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("endTime").as(Integer.class), inventoryDto.getEndTime()));
                }
                if (inventoryDto.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), inventoryDto.getEffective()));
                }
                if (inventoryDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), inventoryDto.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
