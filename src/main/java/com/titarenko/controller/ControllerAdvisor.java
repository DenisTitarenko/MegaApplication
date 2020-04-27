package com.titarenko.controller;

import com.titarenko.model.CustomErrorResponse;
import com.titarenko.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Controller
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @GetMapping
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundExceptionJsp(ResourceNotFoundException ex, Model model) {
        model.addAttribute("error", CustomErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build());
        return "/error/exception_page";
    }

}
