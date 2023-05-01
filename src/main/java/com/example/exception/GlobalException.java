package com.example.exception;

import com.example.common.CustomException;
import com.example.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

    /**
     * 全局异常处理器
     */
    @Slf4j
    @ControllerAdvice(annotations = {RestControllerAdvice.class, Controller.class})
    @ResponseBody
    public class GlobalException extends Throwable {
        @ExceptionHandler(SQLIntegrityConstraintViolationException.class)//捕获
        public R<String> ex(SQLIntegrityConstraintViolationException ex){
            ex.printStackTrace();
            if (ex.getMessage().contains("Duplicate entry")){
                String msg = ex.getMessage().split(" ")[2] + "已存在";
                return R.error(msg);
            }
            return R.error("对不起,操作失败,请联系管理员");
        }

    @ExceptionHandler(CustomException.class)//捕获
    public R<String> ex(CustomException ex){
            log.info("捕获到了");


        return R.error(ex.getMessage());
    }
    }

