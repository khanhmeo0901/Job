package org.example;

import org.apache.http.HttpHost;
import org.apache.poi.xwpf.usermodel.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TesTPush {
    private static final RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http")));

    public static void main(String[] args) {
        Map<String, StringBuilder> data = readFile();

        pushData(data);
    }
    public static void pushData(Map<String,StringBuilder> data) {
        // version 7.17

        try {
            IndexRequest request = new IndexRequest("test")
                    .source(data, XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            System.out.println("Document ID: " +response.getId());
        }catch (Exception e) {
            e.getMessage();
        }
    }
    public static Map<String,StringBuilder> readFile() {
        Map<String, StringBuilder> data = null;
        try {
            data = new HashMap<>();
            StringBuilder kq = new StringBuilder();
            String docxFilePath = "C:\\Users\\THINKPAD\\Desktop\\Công việc\\Data test\\BA Nguyễn Thị Hương .docx";
            data.put("key", new StringBuilder(new File(docxFilePath).getName()));
            FileInputStream fis = new FileInputStream(new File(docxFilePath));
            long modifiedTimeMillis = new File(docxFilePath).lastModified();
            Date modifiedDate = new Date(modifiedTimeMillis);
            System.out.println(modifiedDate);

            XWPFDocument document = new XWPFDocument(fis);
            List<IBodyElement> elements = document.getBodyElements();
            for (IBodyElement element : elements) {

                if (element instanceof XWPFParagraph) {
                    XWPFParagraph paragraph = (XWPFParagraph) element;
                    String text = paragraph.getText();
                    kq.append(text+"\n");
                } else if (element instanceof XWPFTable) {
                    XWPFTable table = (XWPFTable) element;
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            String cellText = cell.getText();
                            kq.append(cellText+"\t");
                        }
                    }
                }

            }
            kq.append("\n");
            System.out.println(kq.toString());
            data.put("value",kq);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
