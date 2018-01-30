package com.yikejian.data.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.data.api.v1.dto.Pagination;
import com.yikejian.data.api.v1.dto.DataDto;
import com.yikejian.data.api.v1.dto.RequestDataDto;
import com.yikejian.data.api.v1.dto.ResponseDataDto;
import com.yikejian.data.domain.data.Data;
import com.yikejian.data.repository.DataRepository;
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
 * <code>DataService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Service
public class DataService {

    private DataRepository dataRepository;

    @Autowired
    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @HystrixCommand
    public Data saveData(DataDto dataDto) {
        Data data;
        if (dataDto.getDataId() != null) {
            data = dataRepository.findByDataId(dataDto.getDataId());
        } else {
            data = new Data();
        }
        data.fromDataDto(dataDto);
        return dataRepository.save(data);
    }

    @HystrixCommand
    public List<Data> saveDatas(List<DataDto> dataDtoList) {
        List<Data> dataList = Lists.newArrayList();
        for (DataDto dataDto : dataDtoList) {
            Data data;
            if (dataDto.getDataId() != null) {
                data = dataRepository.findByDataId(dataDto.getDataId());
            } else {
                data = new Data();
            }
            data.fromDataDto(dataDto);
            dataList.add(data);
        }
        return (List<Data>) dataRepository.save(dataList);
    }

    @HystrixCommand
    public DataDto getDataById(Long dataId) {
        Data data = dataRepository.findByDataId(dataId);
        return data.toDataDto();
    }

    @HystrixCommand
    public ResponseDataDto getAll() {
        List<Data> dataList = (List<Data>) dataRepository.findAll();
        List<DataDto> dataDtoList = Lists.newArrayList(
                dataList.stream().map(Data::toDataDto).collect(Collectors.toList())
        );
        return new ResponseDataDto(dataDtoList);
    }

    @HystrixCommand
    public ResponseDataDto getDatas(RequestDataDto requestDataDto) {
        Pagination pagination;
        if (requestDataDto != null && requestDataDto.getPagination() != null) {
            pagination = requestDataDto.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestDataDto != null && requestDataDto.getSort() != null) {
            sort = new Sort(requestDataDto.getSort().getOrder(), requestDataDto.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrent(),
                pagination.getPageSize(),
                sort);
        Page<Data> page = dataRepository.findAll(dataSpec(requestDataDto.getData()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotal(page.getTotalElements());
        List<DataDto> dataDtoList = Lists.newArrayList(page.getContent().stream().
                map(Data::toDataDto).collect(Collectors.toList()));
        return new ResponseDataDto(dataDtoList, pagination);
    }

    private Specification<Data> dataSpec(final DataDto dataDto) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (dataDto != null) {
                if (StringUtils.isNotBlank(dataDto.getDataName())) {
                    predicateList.add(cb.like(root.get("dataName").as(String.class), "%" + dataDto.getDataName() + "%"));
                }
                if (dataDto.getStartTime() != null) {
                    predicateList.add(cb.equal(root.get("startTime").as(Integer.class), dataDto.getStartTime()));
                }
                if (dataDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("endTime").as(Integer.class), dataDto.getEndTime()));
                }
                if (dataDto.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), dataDto.getEffective()));
                }
                if (dataDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), dataDto.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
