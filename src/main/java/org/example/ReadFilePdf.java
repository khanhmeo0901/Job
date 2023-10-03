package org.example;

import org.apache.http.HttpHost;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFilePdf {
    private static final RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http")));

    public static void main(String[] args) {
//            pushDataFromELK();
//            getDataFromELk("Misa");
       List<String> data = getDataTest();
        System.out.println(data);
    }
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
                    Object ob = map.get("value");
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
    public static void pushDataFromELK() {
        try {
            List<String> list = getData();
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

    public static List<String> getDataTest() {
        try {
//            String pdfFilePath = "C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Vũ Tùng Lâm.pdf";
            String pdfFilePath =  "C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Nguyễn Thị Hương.pdf";
            PDDocument document = PDDocument.load(new File(pdfFilePath));

            int countPage = document.getNumberOfPages();
            List<String> listData = new ArrayList<>();

            // Tạo một đối tượng PDFTextStripper để lấy dữ liệu từng trang
            PDFTextStripper textStripper = new PDFTextStripper();
            for (int i = 0; i < countPage; i++) {
                // Đặt trang đang xử lý
                textStripper.setStartPage(i + 1);
                textStripper.setEndPage(i + 1);

//                // Lấy nội dung từng trang và thêm vào danh sách
                String pdfContent = textStripper.getText(document);
//                listData.add("Page " + (i + 1) + ": " + pdfContent);
                // Sử dụng BufferedReader để đọc từng dòng của chuỗi văn bản
                BufferedReader reader = new BufferedReader(new StringReader(pdfContent));
                String line;
                int lineNumber = 1;
                while ((line = reader.readLine()) != null){
                    if(!(line.contains("Evaluation Only. Created with Aspose.Words. Copyright 2003-2023 Aspose Pty Ltd."))
                    && !(line.contains("Created with an evaluation copy of Aspose.Words. To discover the full versions of our APIs"))
                            && !(line.contains("please visit: https://products.aspose.com/words/"))) {

                        System.out.println("Page " + (i + 1) + ", Line " + lineNumber + ": " + line);
                        lineNumber++;
                    }
                }

                reader.close();
            }

            document.close();

            // In số trang và dữ liệu từng trang
            System.out.println("Số trang: " + countPage);
            for (String pageContent : listData) {
                System.out.println(pageContent);
            }

            return listData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<String> getData() {
        try {
            int count = 0 ;
            String pdfFilePath = "C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Vũ Tùng Lâm.pdf";
            PDDocument document = PDDocument.load(new File(pdfFilePath));
            int countPage = document.getNumberOfPages();
            PDFTextStripper textStripper = new PDFTextStripper();
            String pdfContent = textStripper.getText(document);
            String[] lines = pdfContent.split("\\r?\\n");
            List<String> listData = new ArrayList<>();
            for (int i = 0; i < lines.length; i++) {
                count++;
                listData.add("Line :" +count +" - Text : "+lines[i]);
            }
            document.close();
            System.out.println(countPage);
            return listData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
