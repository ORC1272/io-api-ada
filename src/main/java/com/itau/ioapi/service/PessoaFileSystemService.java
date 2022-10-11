package com.itau.ioapi.service;

import com.itau.ioapi.model.Pessoa;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaFileSystemService {

    public List<Pessoa> desserialize(Path path) throws IOException {

        return Files.readString(path)
                .lines()
                .parallel() // multi Threads
                .map(string -> {
                    String[] attr = string.split(",");
                    String nome = attr[0];
                    ZoneId zoneId = ZoneId.of(attr[1]);
                    LocalDateTime localDateTime = LocalDateTime.parse(attr[2]);

                    return new Pessoa(nome, zoneId, localDateTime);
                })
                .collect(Collectors.toList());
    }

    public void serialize(List<String> stringList, Path path) throws IOException {

        Files.write(path, stringList, StandardCharsets.UTF_8);

    }

    public void serialize(byte[] byteArray, Path path) throws IOException {

        Files.write(path, byteArray);
    }
}
