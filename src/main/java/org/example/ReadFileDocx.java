package org.example;

import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.io.LineNumberReader;

public class ReadFileDocx {
    public static void main(String[] args) {

        try {
            // Đường dẫn đến tệp DOCX chứa văn bản và bảng
            String docxFilePath = "C:\\Users\\THINKPAD\\Desktop\\Công việc\\Data test\\BA Nguyễn Thị Hương .docx";
            int count = 0 ;
            // Mở tệp DOCX
            FileInputStream fis = new FileInputStream(new File(docxFilePath));
            XWPFDocument document = new XWPFDocument(fis);

            // Duyệt qua từng phần trong tài liệu
            List<IBodyElement> elements = document.getBodyElements();
            for (IBodyElement element : elements) {
                if (element instanceof XWPFParagraph) {
                    // Xử lý dòng văn bản
                    XWPFParagraph paragraph = (XWPFParagraph) element;
                    String text = paragraph.getText();
                    count ++;
                    System.out.println("Line :"+count+" - Text : " + text );
                } else if (element instanceof XWPFTable) {
                    // Xử lý bảng
                    XWPFTable table = (XWPFTable) element;
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                                for (XWPFParagraph cellParagraph : cell.getParagraphs()) {
                                    // Đọc nội dung của đoạn văn bản trong ô
                                    String cellParagraphText = cellParagraph.getText();
                                    count ++;
                                        System.out.println("Line :"+count+" - Table :" + cellParagraphText);
                                }
                        }
                    }
                }else if (element instanceof XWPFPicture) {
                    count ++;
                    System.out.println("Line :"+count+" - Image:");
                }
            }
            System.out.println(count);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
