package com.maracuya.app.common;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import static java.nio.file.Files.readString;

public class Resources {

    @SneakyThrows
    public static String loadResourceAsString(String filePath) {
        return readString(new ClassPathResource(filePath).getFile().toPath());
    }
}
