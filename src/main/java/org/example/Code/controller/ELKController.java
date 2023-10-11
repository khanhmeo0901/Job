package org.example.Code.controller;

import org.example.Code.contanst.ApiCode;
import org.example.Code.dto.ProjectResponse;
import org.example.Code.service.ELKService;
import org.example.Code.service.ReadFileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
public class ELKController {
    private ELKService elkService;
    private ReadFileService readFileService;

    public ELKController(ELKService elkService, ReadFileService readFileService) {
        this.elkService = elkService;
        this.readFileService = readFileService;
    }

    @GetMapping("/getData")
    public ProjectResponse<?> getDataFromELK(@RequestParam String keyword, @RequestParam(defaultValue = "1") int from
            , @RequestParam(defaultValue = "10") int size, List<String> listOption) {
        return new ProjectResponse<>(ApiCode.SUCCESS, elkService.getDataFromELk(keyword, from, size,listOption));
    }

    @GetMapping("/pushFile")
    public ProjectResponse<?> pushFileToELK(String folderPath) {
        readFileService.filePath(folderPath);
        return new ProjectResponse<>(ApiCode.SUCCESS);
    }

    @GetMapping("/downloadFile")
    public byte[] downloadFile(String fileName, HttpServletResponse response) throws IOException {
        String filePath = "D:\\Data 04_10_2023\\test";
        String fileDownload = filePath+"/"+fileName;
        Path path = Path.of(fileDownload);
        return Files.readAllBytes(path);

    }
}
