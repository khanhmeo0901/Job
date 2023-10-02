package org.example.Code;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ELK {
    private static final RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http")));

    public static void getDataFromELk(String keyword) {
        try {
            SearchRequest request = new SearchRequest();
            request.indices("test");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            request.source(searchSourceBuilder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            if (response.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = response.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    Object ob = map.get("key");
                    ArrayList<Object> arrayListValue = (ArrayList<Object>) ob;
                    for (Object item : arrayListValue) {
                        String itemText = String.valueOf(item);
                        if (itemText.contains(keyword)) {
                            if (arrayListValue.indexOf(item) > 0) {
                                String previousLine = String.valueOf(arrayListValue.get(arrayListValue.indexOf(item) - 1));
                                System.out.println("Dòng trước: " + previousLine);
                            }
                            String highlightedText = highlightKeywords(itemText, keyword);
                            System.out.println("Dòng chứa keyword được highlight: " + highlightedText);
                            if (arrayListValue.indexOf(item) < arrayListValue.size() - 1) {
                                String nextLine = String.valueOf(arrayListValue.get(arrayListValue.indexOf(item) + 1));
                                System.out.println("Dòng sau: " + nextLine);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
    public static void pushDataFilePdfToELK(File file) {
        try {
            List<String> list = ReadFile.getDataFileDocx(file);
            System.out.println(list);
            Map<String, List<String>> data = new HashMap<>();
            data.put("value", list);
            IndexRequest request = new IndexRequest("test")
                    .source(data, XContentType.JSON); // Field "text" chứa nội dung cần đẩy

            // Thực hiện yêu cầu và nhận phản hồi từ Elasticsearch
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void pushDataFileDocxToELK(File file) {
        try {
            List<String> list = ReadFile.getDataFileDocx(file);
            System.out.println(list);
            Map<String, List<String>> data = new HashMap<>();
            data.put("value", list);
            IndexRequest request = new IndexRequest("test")
                    .source(data, XContentType.JSON); // Field "text" chứa nội dung cần đẩy

            // Thực hiện yêu cầu và nhận phản hồi từ Elasticsearch
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String highlightKeywords(String text, String keyword) {
        // Tạo biểu thức chính quy để tìm từ khóa
        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        // Đánh dấu từ khóa bằng thẻ <mark>
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, "<mark>" + matcher.group() + "</mark>");
        }
        matcher.appendTail(result);

        return result.toString();
    }
}
