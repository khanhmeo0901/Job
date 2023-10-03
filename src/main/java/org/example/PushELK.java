//package org.example;
//
//import org.apache.http.HttpHost;
//import org.apache.poi.xwpf.usermodel.*;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.xcontent.XContentType;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.List;
//
//public class PushELK {
//    private static final RestHighLevelClient client = new RestHighLevelClient(
//            RestClient.builder(new HttpHost("localhost", 9200, "http")));
//
//    public static void main(String[] args) {
////        String data = getData();
////        System.out.println(data);
////
////        pushDataToELK(data);
////        try {
////
////
////            BulkRequest bulkRequest = new BulkRequest();
////            Map<String, Object> data = new HashMap<>();
////            data.put("field1", "value1");
////            data.put("field2", "value2");
////
////            IndexRequest request = new IndexRequest("test").source(new Gson().toJson(data),XContentType.JSON);
////            bulkRequest.add(request);
////            client.bulk(bulkRequest, RequestOptions.DEFAULT);
////            data.put("field1", "value1");
////            data.put("field2", "value2");
////            String data1 = getData();
////
////            // Dữ liệu dạng Map<String, String>
////            Map<String, String> data = new HashMap<>();
////            data.put("field1", "value1");
////            data.put("field2", "value2");
////
////            // Thêm các trường và giá trị dữ liệu vào Map
////
////            // Tạo một IndexRequest để đẩy dữ liệu lên Elasticsearch
////            IndexRequest request = new IndexRequest("test") // Thay thế bằng tên chỉ mục của bạn
////                    .source(data);
////
////            // Thực hiện đẩy dữ liệu lên Elasticsearch và nhận kết quả
////            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
////
////            // In thông tin về kết quả
////            System.out.println("Document ID: " + response.getId());
////            System.out.println("Index: " + response.getIndex());
////            System.out.println("Result: " + response.getResult());
////            // In response body
////            String responseBody = response.toString();
////            System.out.println("Response Body: " + responseBody);
////            // Đóng kết nối client
////            client.close();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//        String data = getData();
//
//        pushDataToELK(data);
//    }
//
//
//    public static void pushDataToELK(String data) {
//
//        try {
//
//            // Tạo một IndexRequest để đẩy chuỗi lên Elasticsearch
//            IndexRequest request = new IndexRequest("test")
//                    .source(data, XContentType.JSON);
//            // Thực hiện đẩy chuỗi lên Elasticsearch và nhận kết quả
//            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
//
//            // In thông tin về kết quả
//            System.out.println("Document ID: " + response.getId());
//            System.out.println("Index: " + response.getIndex());
//            System.out.println("Result: " + response.getResult());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            // Đóng RestHighLevelClient khi đã sử dụng xong
//            try {
//                client.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    public static  String getData() {
//        StringBuilder content = new StringBuilder();
//        try {
//            // Đường dẫn đến tệp DOCX chứa văn bản và bảng
//            String docxFilePath = "C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Nguyễn Thị Hương .docx";
//
//            FileInputStream fis = new FileInputStream(new File(docxFilePath));
//            XWPFDocument document = new XWPFDocument(fis);
//
//            List<IBodyElement> elements = document.getBodyElements();
//            for (IBodyElement element : elements) {
//                if (element instanceof XWPFParagraph) {
//                    XWPFParagraph paragraph = (XWPFParagraph) element;
//                    String text = paragraph.getText();
//                    content.append(text+ "\n");
//                } else if (element instanceof XWPFTable) {
//                    XWPFTable table = (XWPFTable) element;
//                    List<XWPFTableRow> rows = table.getRows();
//                    for (XWPFTableRow row : rows) {
//                        List<XWPFTableCell> cells = row.getTableCells();
//                        for (XWPFTableCell cell : cells) {
//                            String cellText = cell.getText();
//
//                            content.append(cellText+ "\t");
//
//                        }
//                        content.append("\n");
////                        System.out.println("\n"); // Xuống dòng sau mỗi hàng trong bảng
//                    }
//                }
//            }
//
//            fis.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return content.toString();
//    }
//}
//
