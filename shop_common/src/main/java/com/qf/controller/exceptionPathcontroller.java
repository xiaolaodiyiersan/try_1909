package com.qf.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class exceptionPathcontroller implements ErrorController {
    /**
     * 页面路径错误回填
     * @param response
     * @return
     */
    @RequestMapping("/error")
    public String pathError(HttpServletResponse response) {
        int status = response.getStatus();
        switch (status) {
            case 400:
                return "400";
            case 401:
                return "401";
            case 402:
                return "402";
            case 403:
                return "403";
            case 404:
                return "404";
        }
        return "myerror";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
