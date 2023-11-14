package org.example.Code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.poi.xwpf.usermodel.*;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.example.Code.base.BaseAbstract;
import org.example.Code.contanst.ApiCode;
import org.example.Code.dto.ProjectResponse;
import org.example.Code.entity.KetQua;
import org.example.Code.entity.ListObjectKeyWord;
import org.example.Code.entity.ObjectKeyWord;
import org.example.Code.entity.ObjectTest;
import org.example.Code.service.ELKService;
import org.example.Code.service.ReadFileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@RestController
public class ELKController {
    private RestHighLevelClient client;
    private ELKService elkService;
    private ReadFileService readFileService;

    public ELKController(RestHighLevelClient client, ELKService elkService, ReadFileService readFileService) {
        super();
        this.client = client;
        this.elkService = elkService;
        this.readFileService = readFileService;
    }

    //    @GetMapping("/getData")
//    public ProjectResponse<?> getDataFromELK(@RequestParam String keyword, @RequestParam(defaultValue = "1") int from
//            , @RequestParam(defaultValue = "10") int size, List<String> listOption) {
//        return new ProjectResponse<>(ApiCode.SUCCESS, elkService.getDataFromELk(keyword, from, size,listOption));
//    }
    private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        return file;
    }
    public List<String> getDataFileDocx(File file) {
        try {
            List<String> list = new ArrayList<>();
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(fis);
            List<IBodyElement> elements = document.getBodyElements();
            for (IBodyElement element : elements) {
                if (element instanceof XWPFParagraph) {
                    XWPFParagraph paragraph = (XWPFParagraph) element;
                    String text = paragraph.getText();
                    list.add(text);
                } else if (element instanceof XWPFTable) {
                    XWPFTable table = (XWPFTable) element;
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            for (XWPFParagraph cellParagraph : cell.getParagraphs()) {
                                String cellParagraphText = cellParagraph.getText();
                                list.add(cellParagraphText);
                            }
                        }
                    }
                } else if (element instanceof XWPFPicture) {
                }
            }
            fis.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/importFile")
    public ProjectResponse<?> testImportFile(@RequestBody MultipartFile multipartFile, String index) throws IOException {

        // Chuyển đổi MultipartFile sang File
        File file = convertMultiPartToFile(multipartFile);

        try {
            List<String> list = getDataFileDocx(file);
            Map<String, List<String>> data = new HashMap<>();
            String kq = new ObjectMapper().writeValueAsString(list);
            System.out.println("==============================");
            System.out.println(kq);
            data.put("value", Collections.singletonList(kq));
            data.put("fileName", Collections.singletonList(file.getName()));
            IndexRequest request = new IndexRequest(index)
                    .source(data, XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            System.out.println("Push data to ELk Successful !");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ProjectResponse<>(ApiCode.SUCCESS);
    }

    @GetMapping("/getData")
    public ProjectResponse<?> getDataFromELK(@RequestParam String keyword, @RequestParam(defaultValue = "1") int from
            , @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "false") boolean and,
                                             @RequestParam(defaultValue = "false") boolean or, @RequestParam(defaultValue = "false") boolean not) {
        return new ProjectResponse<>(ApiCode.SUCCESS, elkService.getDataFromELk(keyword, from, size, and, or, not));
    }

    @GetMapping("/pushFile")
    public ProjectResponse<?> pushFileToELK(String folderPath, String index) {
        readFileService.filePath(folderPath, index);
        return new ProjectResponse<>(ApiCode.SUCCESS);
    }

    @GetMapping("/downloadFile")
    public byte[] downloadFile(String fileName, HttpServletResponse response) throws IOException {
        String filePath = "D:\\Data 04_10_2023\\test";
        String fileDownload = filePath + "/" + fileName;
        Path path = Path.of(fileDownload);
        return Files.readAllBytes(path);
    }

    @PostMapping("/updateMapping")
    public void update(@RequestBody String mapping) throws IOException {
        PutMappingRequest request = new PutMappingRequest("test");
        request.source(mapping, XContentType.JSON);

        client.indices().putMapping(request, RequestOptions.DEFAULT);
    }

    @GetMapping("/reindex")
    public void reindexELK(@RequestParam String index1, @RequestParam String index2) {
        Map<String, Object> data = getDataFromELk(index1);
        String key = (String) data.get("key");
        String value = (String) data.get("value");
    }

    public void bulkDataToELK() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        // Dữ liệu index mà bạn muốn thêm vào Elasticsearch
        String jsonData = "{\"value\": [\"Line :1 - Text : Curriculum Vitae\", \"Line :2 - Table :\", \"Line :3 - Table :Full name: Nguyen Thi Huong\"],\"key\":\"2\"}";

        // Tạo yêu cầu index
        IndexRequest indexRequest = new IndexRequest("test")
                .source(jsonData, XContentType.JSON);

        // Thêm yêu cầu index vào yêu cầu bulk
        bulkRequest.add(indexRequest);

        // Thực hiện yêu cầu bulk và nhận phản hồi
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);

    }

    public Map<String, Object> getDataFromELk(String index) {
        try {
            SearchRequest request = new SearchRequest();
            request.indices(index);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            request.source(searchSourceBuilder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            if (response.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = response.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    System.out.println(map);
                    return map;
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

}
