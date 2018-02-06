package com.yikejian.message.api.v1;

import com.yikejian.message.domain.message.Message;
import com.yikejian.message.exception.MessageServiceException;
import com.yikejian.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping(path = "/v1")
public class MessageControllerV1 {

    private MessageService messageService;

    @Autowired
    public MessageControllerV1(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/message")
    public ResponseEntity addMessage(@RequestBody final Message message) {
        // todo send log
        return Optional.ofNullable(messageService.sendMessage(message))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new MessageServiceException("Message not saved."));
    }

}
