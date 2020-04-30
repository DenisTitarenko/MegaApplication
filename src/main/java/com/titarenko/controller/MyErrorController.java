package com.titarenko.controller;

import com.titarenko.model.CustomErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("error",
                    new CustomErrorResponse(HttpStatus.valueOf(statusCode), HttpStatus.valueOf(statusCode).name()));
            if(statusCode >= 400 && statusCode <= 499) {
                return "error/4xx_error";
            } else if(statusCode >= 500 && statusCode <= 599) {
                return "error/5xx_error";
            }
        }
        return "/";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
