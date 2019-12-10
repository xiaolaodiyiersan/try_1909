package com.qf.controller;

import com.qf.entity.ResultData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice

public class exceptionController {
    /**
     * 错误回显
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object exceptionShow(HttpServletRequest request, Exception e) {
        System.out.println("错误为：" + e.getMessage());
        String header = request.getHeader("X-Requested-With");
        if (header != null || header.equals("XMLHttpRequest")) {
            return new ResultData<String>().setCode(ResultData.errorList.ERROR).setData("操作有误");
        } else {
            return new ModelAndView("myerror");
        }

    }
}
