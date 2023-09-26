package org.example;

import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ReadDocx {
    public static void main(String[] args) {
        try {
            // Đường dẫn đến tệp DOCX chứa văn bản và bảng
            String docxFilePath = "C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Nguyễn Thị Hương .docx";

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
                    System.out.println("Text: " + text);
                } else if (element instanceof XWPFTable) {
                    // Xử lý bảng
                    XWPFTable table = (XWPFTable) element;
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
//                            // Xử lý nội dung trong ô
                            String cellText = cell.getText();
                            System.out.print(cellText + "\t");

//                            for (XWPFParagraph cellParagraph : cell.getParagraphs()) {
//                                // Đọc nội dung của đoạn văn bản trong ô
//                                String cellParagraphText = cellParagraph.getText();
//                                if (!cellParagraphText.isEmpty()) {
//                                    System.out.println(cellParagraphText);
//                                }
//                            }

                        }
                        System.out.println(); // Xuống dòng sau mỗi hàng trong bảng
                    }
                }
            }

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
