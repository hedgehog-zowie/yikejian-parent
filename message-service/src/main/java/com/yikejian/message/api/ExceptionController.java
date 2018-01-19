package com.yikejian.message.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <code>ExceptionController</code>.
 * 异常处理
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2017/6/7 10:11
 */
@ControllerAdvice
@ResponseBody
public class ExceptionController {

    /**
     * 日志记录器.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    /**
     * 500 - Internal Server Error
     *
     * @param e 异常
     * @return 返回Response对象
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(final Exception e) {
        LOGGER.error("Error: ", e);
        return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
