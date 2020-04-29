package com.titarenko.controller;

import com.titarenko.exception.ResourceNotFoundException;
import com.titarenko.model.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundExceptionJsp(ResourceNotFoundException ex, Model model) {
        model.addAttribute("error", new CustomErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
        return "/error/exception_page";
    }
}
