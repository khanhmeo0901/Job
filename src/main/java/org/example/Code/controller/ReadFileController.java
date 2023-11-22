package org.example.Code.controller;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.xcontent.XContentType;
import org.elasticsearch.common.xcontent.XContentType;
import org.example.Code.base.BaseAbstract;
import org.example.Code.contanst.ApiCode;
import org.example.Code.dto.ProjectResponse;
import org.example.Code.service.ReadFileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReadFileController  {
    private ReadFileService readFileService;
    private RestHighLevelClient client;

    public ReadFileController(ReadFileService readFileService, RestHighLevelClient client) {
        this.readFileService = readFileService;
        this.client = client;
    }

    @PostMapping("/")
    private void test(String file,String index) {
        readFileService.filePath(file,index);
    }
    @GetMapping("/pushELKDocx")
    public  void pushDataFileDocxToELK() {
        try {
            List<String> list = readFileService.readFileDocx();
            Map<String, List<String>> data = new HashMap<>();
            data.put("value", list);
            data.put("fileName", Collections.singletonList("test"));
            IndexRequest request = new IndexRequest("test5")
                    .type("_doc")
                    .source(data, XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/pushELKPdf")
    public  void pushDataFilePdfToELK() {
        try {
            List<String> list = readFileService.readFilePdf();
            Map<String, List<String>> data = new HashMap<>();
            data.put("value", list);
            data.put("fileName", Collections.singletonList("test"));
            IndexRequest request = new IndexRequest("test5")
                    .type("_doc")
                    .source(data, XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    @PostMapping("/push1")
//    public void test1(String file,String index) {
//        File file1 = new File(file)
//        pushDataFileDocxToELK1(file,index);
//    }
//
//    @PostMapping("/push2")
//    public void test1(String file,String index) {
//        pushDataFileDocxToELK2();
//    }

//    @PostMapping("/importFile3")
//    public ProjectResponse<?> testImportFile3(@RequestBody MultipartFile multipartFile) throws IOException {
//
//        File file = convertMultiPartToFile(multipartFile);
//        try {
//            List<String> docContent = readDocFile(file);
//            System.out.println(docContent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return new ProjectResponse<>(ApiCode.SUCCESS);
//    }

//    private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
//        File file = new File(multipartFile.getOriginalFilename());
//        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
//        return file;
//    }

}
