package com.titarenko.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CustomErrorResponse {
    private Integer status;
    private String message;
}
