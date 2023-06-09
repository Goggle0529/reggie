package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLInput;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
//捕获具有花括号内注解的类抛出的异常。
@ControllerAdvice(annotations = {RestController.class, Controller.class})
//将结果封装为JSON返回。
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理方法,自己定义需要捕获的异常名称。
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error(ex.getMessage());

        if (ex.getMessage().contains("Duplicate entry")) {
            String[] s = ex.getMessage().split(" ");
            String msg = s[2] + "已存在";
            return R.error(msg);
        }

        return R.error("失败了");
    }

    /**
     * 异常处理方法,自己定义需要捕获的异常名称。
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex) {
        log.error(ex.getMessage());

        return R.error(ex.getMessage());
    }

}
