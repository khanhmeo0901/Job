package org.example.Code.base;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseAbstract {
    private RestHighLevelClient client;

    public BaseAbstract(RestHighLevelClient client) {
        this.client = client;
    }
    public List<String> getDataFileDocx(File file) {
        try {
            List<String> list = new ArrayList<>();
           // int count = 0;
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(fis);
            List<IBodyElement> elements = document.getBodyElements();
            for (IBodyElement element : elements) {
                if (element instanceof XWPFParagraph) {
                    XWPFParagraph paragraph = (XWPFParagraph) element;
                    String text = paragraph.getText();
                  //  count++;
//                    list.add("Line :" + count + " - Text : " + text);
                    list.add(text);
                } else if (element instanceof XWPFTable) {
                    XWPFTable table = (XWPFTable) element;
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            for (XWPFParagraph cellParagraph : cell.getParagraphs()) {
                                String cellParagraphText = cellParagraph.getText();
//                                count++;
//                                list.add("Line :" + count + " - Table :" + cellParagraphText);
                                list.add(cellParagraphText);
                            }
                        }
                    }
                } else if (element instanceof XWPFPicture) {
                  //  count++;
//                    System.out.println("Line :" + count + " - Image:");
                   // System.out.println("Line :" + count + " - Image:");
                }
            }
           // System.out.println(count);
            fis.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<String> getDataFilePdf(File file) {
        try {
           // int count = 0;
            PDDocument document = PDDocument.load(file);
            PDFTextStripper textStripper = new PDFTextStripper();
            String pdfContent = textStripper.getText(document);
            String[] lines = pdfContent.split("\\r?\\n");
            List<String> listData = new ArrayList<>();
            for (int i = 0; i < lines.length; i++) {
             //   count++;
//                listData.add("Line :" + count + " - Text : " + lines[i]);
                listData.add(lines[i]);
            }
            document.close();
            return listData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public  void pushDataFilePdfToELK(File file) {
        try {
            List<String> list = getDataFilePdf(file);
            System.out.println(list);
            Map<String, List<String>> data = new HashMap<>();
            data.put("value", list);
            IndexRequest request = new IndexRequest("test")
                    .source(data, XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void pushDataFileDocxToELK(File file) {
        try {
            List<String> list = getDataFileDocx(file);
            System.out.println(list);
            Map<String, List<String>> data = new HashMap<>();
            data.put("value", list);
            IndexRequest request = new IndexRequest("test")
                    .source(data, XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
