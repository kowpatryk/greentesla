package com.maracuya.app.common;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.FileCopyUtils.copyToString;

public class Resources {

    @SneakyThrows
    public static String loadResourceAsString(String filePath) {
        return Files.readString(new ClassPathResource(filePath).getFile().toPath());
//        File file = new ClassPathResource(filePath).getFile();
//        FileInputStream inputStream = new FileInputStream(file);
//        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
//        return copyToString(inputStreamReader);
    }
}
