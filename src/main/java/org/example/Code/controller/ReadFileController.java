package org.example.Code.controller;

import org.elasticsearch.client.RestHighLevelClient;
import org.example.Code.base.BaseAbstract;
import org.example.Code.service.ReadFileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Controller
public class ReadFileController extends BaseAbstract {
    private ReadFileService readFileService;

    public ReadFileController(RestHighLevelClient client, ReadFileService readFileService) {
        super(client);
        this.readFileService = readFileService;
    }

    @PostMapping("/")
    private void test(String file) {
        readFileService.filePath(file);
    }



}
