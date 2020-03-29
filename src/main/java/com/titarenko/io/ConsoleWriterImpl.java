package com.titarenko.io;

import com.titarenko.di.annotation.Brick;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ConsoleWriterImpl implements Writer {

    @Override
    public void writeToOutputStream(String text) {
        System.out.println(text);
    }
}
