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
            int lineNumber = 0; // Biến để theo dõi số dòng

            String keyword = "fellowship";
            FileInputStream fis = new FileInputStream(new File("C:\\Users\\THINKPAD\\Desktop\\Công việc\\Data test\\BA Nguyễn Thị Hương .docx"));
            XWPFDocument document = new XWPFDocument(fis);

            // Get paragraphs and tables
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            List<XWPFTable> tables = document.getTables();

            Iterator<XWPFParagraph> paragraphIterator = paragraphs.iterator();
            Iterator<XWPFTable> tableIterator = tables.iterator();

//            while (paragraphIterator.hasNext() || tableIterator.hasNext()) {
//                if (paragraphIterator.hasNext()) {
//                    XWPFParagraph paragraph = paragraphIterator.next();
//                    System.out.println(paragraph.getText());
//                }
//
//                if (tableIterator.hasNext()) {
//                    XWPFTable table = tableIterator.next();
//                    for (XWPFTableRow row : table.getRows()) {
//                        for (XWPFTableCell cell : row.getTableCells()) {
////                            String[] lines = cell.getText().split("\n");
////                            for (String line : lines) {
////                                System.out.println(line);
////                            }
////                            System.out.print(cell.getText() + "\t");
//                            for (XWPFParagraph paragraph : cell.getParagraphs()) {
//                                // Đọc nội dung của đoạn văn bản
//                                String paragraphText = paragraph.getText();
//                                System.out.println(paragraphText);
//                                String[] lines = paragraphText.split("\n");
//                                // Biến để lưu dòng hiện tại và dòng trước đó
////                                String previousLine = "";
////                                for (String line : lines) {
////                                    lineNumber++; // Tăng số dòng sau mỗi dòng
////                                    // Tìm từ khóa trong dòng
////                                    if (line.contains(keyword)) {
////                                        // In ra dòng trước (nếu có)
////                                        if (!previousLine.isEmpty()) {
////                                            System.out.println("Dòng trước: " + previousLine);
////                                        }
////                                        // In ra dòng chứa từ khóa
////                                        System.out.println("Dòng số: " + lineNumber);
////                                        System.out.println("Dòng chứa từ khóa: " + line);
////
////                                        previousLine = line;
////                                    }
////                                }
//                            }
//                        }
//                        System.out.println();
//                    }
//                }
//            }
            while (paragraphIterator.hasNext() || tableIterator.hasNext()) {
                if (paragraphIterator.hasNext()) {
                    XWPFParagraph paragraph = paragraphIterator.next();
                    String paragraphText = paragraph.getText();
                    if (!paragraphText.isEmpty()) {
                        System.out.println(paragraphText);
                    }
                }
                if (tableIterator.hasNext()) {
                    XWPFTable table = tableIterator.next();
                    for (XWPFTableRow row : table.getRows()) {
                        for (XWPFTableCell cell : row.getTableCells()) {
                            for (XWPFParagraph cellParagraph : cell.getParagraphs()) {
                                // Đọc nội dung của đoạn văn bản trong ô
                                String cellParagraphText = cellParagraph.getText();
                                if (!cellParagraphText.isEmpty()) {
                                    System.out.println(cellParagraphText);
                                }
                            }
                        }
                        System.out.println();
                    }
                }
            }


            // Close the document stream
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
