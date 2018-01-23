package com.yikejian.customer.api.v1;

import com.yikejian.customer.api.v1.dto.Pagination;
import com.yikejian.customer.api.v1.dto.RequestTitle;
import com.yikejian.customer.domain.title.Title;
import com.yikejian.customer.exception.CustomerServiceException;
import com.yikejian.customer.service.TitleService;
import com.yikejian.customer.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1")
public class TitleControllerV1 {

    private TitleService titleService;

    @Autowired
    public TitleControllerV1(TitleService titleService) {
        this.titleService = titleService;
    }

    @PostMapping("/title")
    public ResponseEntity addTitle(@RequestBody final Title title) {
        title.setTitleId(null);
        // todo send log
        return Optional.ofNullable(titleService.saveTitle(title))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CustomerServiceException("Not found title."));
    }

    @PutMapping("/title")
    public ResponseEntity updateTitle(@RequestBody final Title title) {
        // todo send log
        return Optional.ofNullable(titleService.saveTitle(title))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CustomerServiceException("Not found title."));
    }

    @PutMapping("/titles")
    public ResponseEntity updateTitles(@RequestBody final List<Title> titleList) {
        // todo send log
        return Optional.ofNullable(titleService.saveTitles(titleList))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CustomerServiceException("Not found title."));
    }

    @GetMapping("/titles")
    public ResponseEntity getTitles(final @RequestParam(value = "params", required = false) String params) {
        RequestTitle requestTitle;
        if (StringUtils.isBlank(params)) {
            requestTitle = new RequestTitle(new Title(), new Pagination(), null);
        } else {
            try {
                requestTitle = JsonUtils.fromJson(URLDecoder.decode(params, "UTF-8"), RequestTitle.class);
            } catch (UnsupportedEncodingException e) {
                throw new CustomerServiceException(e.getLocalizedMessage());
            }
        }
        requestTitle.getTitle().setDeleted(0);
        // todo send log
        return Optional.ofNullable(titleService.getTitles(requestTitle))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CustomerServiceException("Not found any title."));
    }

    @RequestMapping(value = "/title/{title_id}", method = RequestMethod.GET)
    public ResponseEntity getTitles(final @PathVariable(value = "title_id") Long titleId) {
        // todo send log
        return Optional.ofNullable(titleService.getTitleById(titleId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CustomerServiceException("Not found title."));
    }

}
