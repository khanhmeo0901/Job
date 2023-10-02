package org.example;

import org.apache.http.HttpHost;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.*;
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

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Test {
    private static final RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http")));

    public static void main(String[] args) {

     //   readWordDocument();
//          pushDataFromELK();
         getDataFromELk();
    }

    public static void readWordDocument() {
        try {
            String fileName = "C:\\Users\\THINKPAD\\Desktop\\Công việc\\Data test\\BA Nguyễn Thị Hương .docx";


            XWPFDocument doc = new XWPFDocument(new FileInputStream(fileName));
            List<XWPFTable> table = doc.getTables();
            for (XWPFTable xwpfTable : table) {
                List<XWPFTableRow> row = xwpfTable.getRows();
                for (XWPFTableRow xwpfTableRow : row) {
                    List<XWPFTableCell> cell = xwpfTableRow.getTableCells();
                    for (XWPFTableCell xwpfTableCell : cell) {
                        if (xwpfTableCell != null) {
                            System.out.println(xwpfTableCell.getText());
                            List<XWPFTable> itable = xwpfTableCell.getTables();
                            if (itable.size() != 0) {
                                for (XWPFTable xwpfiTable : itable) {
                                    List<XWPFTableRow> irow = xwpfiTable.getRows();
                                    for (XWPFTableRow xwpfiTableRow : irow) {
                                        List<XWPFTableCell> icell = xwpfiTableRow.getTableCells();
                                        for (XWPFTableCell xwpfiTableCell : icell) {
                                            if (xwpfiTableCell != null) {
                                                System.out.println(xwpfiTableCell.getText());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getDataFromELk() {
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
                        // Xử lý mỗi phần tử trong mảng
                        System.out.println(item);
                    }
//                    System.out.println(map);
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public static void pushDataFromELK() {
        try {
            List<String> list = getData();
            System.out.println(list);
            Map<String, List<String>> data = new HashMap<>();
            data.put("key", list);
            IndexRequest request = new IndexRequest("test")
                    .source(data, XContentType.JSON); // Field "text" chứa nội dung cần đẩy

            // Thực hiện yêu cầu và nhận phản hồi từ Elasticsearch
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getData() {
        try {
            List<String> list = new ArrayList<>();
            String docxFilePath = "C:\\Users\\THINKPAD\\Desktop\\Công việc\\Data test\\BA Nguyễn Thị Hương .docx";
            FileInputStream fis = new FileInputStream(new File(docxFilePath));
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
                            String cellText = cell.getText();
                            list.add(cellText);
                        }
                    }
                }
            }
            System.out.println(list);
            fis.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
