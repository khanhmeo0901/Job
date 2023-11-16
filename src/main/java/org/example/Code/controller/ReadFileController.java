package org.example.Code.controller;

import org.elasticsearch.client.RestHighLevelClient;
import org.example.Code.base.BaseAbstract;
import org.example.Code.service.ReadFileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReadFileController extends BaseAbstract {
    private ReadFileService readFileService;

    public ReadFileController(RestHighLevelClient client, ReadFileService readFileService) {
        super(client);
        this.readFileService = readFileService;
    }

    @PostMapping("/")
    private void test(String file,String index) {
        readFileService.filePath(file,index);
    }


    @PostMapping("/test1611")
    public void test2(String index) {
        pushData(index);
    }

}
