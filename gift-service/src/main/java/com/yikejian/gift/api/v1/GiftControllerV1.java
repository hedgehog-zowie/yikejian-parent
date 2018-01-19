package com.yikejian.gift.api.v1;

import com.yikejian.gift.api.v1.dto.RequestGift;
import com.yikejian.gift.domain.gift.Gift;
import com.yikejian.gift.exception.GiftServiceException;
import com.yikejian.gift.service.GiftService;
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
 * <code>GiftControllerV1</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:57
 */
@RestController
public class GiftControllerV1 {

    private GiftService giftService;

    @Autowired
    public GiftControllerV1(GiftService giftService) {
        this.giftService = giftService;
    }

    @RequestMapping(value = "/gift/{gift_id}", method = RequestMethod.GET)
    public ResponseEntity getGifts(final @PathVariable(value = "gift_id") Long giftId) {
        // todo send log
        return Optional.ofNullable(giftService.getGiftById(giftId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new GiftServiceException("Not found gift."));
    }

    @PostMapping("/gift")
    public ResponseEntity addGift(final Gift gift) {
        // todo send log
        return Optional.ofNullable(giftService.saveGift(gift))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new GiftServiceException("Not found gift."));
    }

    @PutMapping("/gift")
    public ResponseEntity updateGift(final Gift gift) {
        // todo send log
        return Optional.ofNullable(giftService.saveGift(gift))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new GiftServiceException("Not found gift."));
    }

    @GetMapping("/gifts")
    public ResponseEntity getGifts(final RequestGift requestGift) {
        // todo send log
        return Optional.ofNullable(giftService.getGifts(requestGift))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new GiftServiceException("Not found any gift."));
    }

}
