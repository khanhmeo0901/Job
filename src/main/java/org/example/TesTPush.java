//package org.example;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.apache.http.HttpHost;
//import org.apache.poi.ooxml.POIXMLDocument;
//import org.apache.poi.xwpf.usermodel.*;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//public class TesTPush {
//    private static final RestHighLevelClient client = new RestHighLevelClient(
//            RestClient.builder(new HttpHost("localhost", 9200, "http")));
//
//    public static void main(String[] args) throws JsonProcessingException {
//
////        String data = readFile();
////        System.out.println(data);
////        Map<String, String> data = readFile();
////        String data = readFile();
////        getCountPage();
//        //   getDataFromELK();
//    }
//
//    public static void getCountPage() {
//        String docxFilePath = "C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Nguyễn Thị Hương .docx";
//        try{
//            XWPFDocument docx = new XWPFDocument(POIXMLDocument.openPackage(docxFilePath));
//           int pageCount =   docx.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
//            System.out.println("Tổng số trang: " + pageCount);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    public static void getDataFromELK() {
//        try {
//            SearchRequest request = new SearchRequest();
//            request.indices("test");
//            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//            request.source(searchSourceBuilder);
//
//            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//            if (response.getHits().getTotalHits().value > 0) {
//                SearchHit[] searchHit = response.getHits().getHits();
//                for (SearchHit hit : searchHit) {
////                    Map<String,Object>  map = hit.getSourceAsMap();
////                    System.out.println("map:" + Arrays.toString(map.values().toArray()));
//                    String value = hit.getSourceAsMap().get("value").toString();
//                    System.out.println(value);
//                }
//            }
//        } catch (Exception e) {
//            e.getMessage();
//        }
//    }
//
////        public static void pushDataTest(String data) {
////        try {
////            IndexRequest request = new IndexRequest("test")
////                    .source(data, XContentType.JSON);
////            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
////            System.out.println("Document ID: " +response.getId());
////        }catch (Exception e) {
////            e.getMessage();
////        }
////    }
//    public static void pushData(Map<String, String> data) {
//        // version 7.17
//        try {
//            IndexRequest request = new IndexRequest("test")
//                    .source(data, XContentType.JSON);
//            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
//            System.out.println("Document ID: " + response.getId());
//        } catch (Exception e) {
//            e.getMessage();
//        }
//    }
//
//    public static String readFile() {
//
//        try {
//            String listFile = "";
//            String docxFilePath = "C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Nguyễn Thị Hương .docx";
//            FileInputStream fis = new FileInputStream(new File(docxFilePath));
//            XWPFDocument document = new XWPFDocument(fis);
//            JSONArray jsonArray = new JSONArray();
//            List<IBodyElement> elements = document.getBodyElements();
//            for (IBodyElement element : elements) {
//                if (element instanceof XWPFParagraph) {
//                    JSONObject jsonObject = new JSONObject();
//                    XWPFParagraph paragraph = (XWPFParagraph) element;
//                    String text = paragraph.getText();
//                    jsonObject.put("value",text + "\n");
//                    jsonArray.put(jsonObject);
//                  //  listFile += text + "\n";
//                } else if (element instanceof XWPFTable) {
//                    JSONObject jsonObject = new JSONObject();
//                    XWPFTable table = (XWPFTable) element;
//                    List<XWPFTableRow> rows = table.getRows();
//                    for (XWPFTableRow row : rows) {
//                        List<XWPFTableCell> cells = row.getTableCells();
//                        for (XWPFTableCell cell : cells) {
//                            String cellText = cell.getText();
//                            jsonObject.put("value",cellText + "\t");
//                            //listFile += cellText + "\t";
//                        }
////                        listFile += "\n";
//                        jsonObject.put("value","\n");
//                    }
//                    jsonArray.put(jsonObject);
//                }
//            }
//            fis.close();
//            return jsonArray.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
////    public static Map<String,String> readFile() {
////        Map<String, String> data = new HashMap<>();
////        String listFile ="";
////        try {
////
////            String docxFilePath = "C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Nguyễn Thị Hương .docx";
////            data.put("key", new File(docxFilePath).getName());
////            FileInputStream fis = new FileInputStream(new File(docxFilePath));
////
////            long modifiedTimeMillis = new File(docxFilePath).lastModified();
////            Date modifiedDate = new Date(modifiedTimeMillis);
////            System.out.println(modifiedDate);
////
////            XWPFDocument document = new XWPFDocument(fis);
////            List<IBodyElement> elements = document.getBodyElements();
////            for (IBodyElement element : elements) {
////                if (element instanceof XWPFParagraph) {
////                    XWPFParagraph paragraph = (XWPFParagraph) element;
////                    String text = paragraph.getText();
////                    listFile += text+"\n";
////                } else if (element instanceof XWPFTable) {
////                    XWPFTable table = (XWPFTable) element;
////                    List<XWPFTableRow> rows = table.getRows();
////                    for (XWPFTableRow row : rows) {
////                        List<XWPFTableCell> cells = row.getTableCells();
////                        for (XWPFTableCell cell : cells) {
////                            String cellText = cell.getText();
////                            listFile += cellText+"\t";
////                        }
////                        listFile +="\n";
////                    }
////                }
////
////            }
////            System.out.println(listFile);
////            data.put("value",listFile);
////            fis.close();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        return data;
////    }
//}
