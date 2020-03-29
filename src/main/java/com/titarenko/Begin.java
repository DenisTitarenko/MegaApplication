package com.titarenko;

import com.titarenko.config.SpringConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Begin {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(SpringConfiguration.class);
    }
}
