package com.yikejian.data.api.v1;

import com.yikejian.data.api.v1.dto.DataDto;
import com.yikejian.data.api.v1.dto.RequestDataDto;
import com.yikejian.data.exception.DataServiceException;
import com.yikejian.data.service.DataService;
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
 * <code>DataControllerV1</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:57
 */
@RestController
public class DataControllerV1 {

    private DataService dataService;

    @Autowired
    public DataControllerV1(DataService dataService) {
        this.dataService = dataService;
    }

    @RequestMapping(value = "/data/{data_id}", method = RequestMethod.GET)
    public ResponseEntity getDatas(final @PathVariable(value = "data_id") Long dataId) {
        // todo send log
        return Optional.ofNullable(dataService.getDataById(dataId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new DataServiceException("Not found data."));
    }

    @PostMapping("/data")
    public ResponseEntity addData(final DataDto dataDto) {
        // todo send log
        return Optional.ofNullable(dataService.saveData(dataDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new DataServiceException("Not found data."));
    }

    @PutMapping("/data")
    public ResponseEntity updateData(final DataDto dataDto) {
        // todo send log
        return Optional.ofNullable(dataService.saveData(dataDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new DataServiceException("Not found data."));
    }

    @GetMapping("/datas")
    public ResponseEntity getDatas(final RequestDataDto requestDataDto) {
        // todo send log
        return Optional.ofNullable(dataService.getDatas(requestDataDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new DataServiceException("Not found any data."));
    }

}
