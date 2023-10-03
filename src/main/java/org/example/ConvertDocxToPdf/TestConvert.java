package org.example.ConvertDocxToPdf;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.List;

public class TestConvert {

    // Check conflict repo : org.apache.xmlbeans.XmlOptions org.apache.xmlbeans.XmlOptions.setEntityExpansionLimit(int)


    public static void main(String[] args) {
        try (InputStream is = new FileInputStream(new File("C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Nguyễn Thị Hương .docx"));
             OutputStream out = new FileOutputStream(new File("C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\test03102023.pdf"));) {
            long start = System.currentTimeMillis();
            // 1) Load DOCX into XWPFDocument
            XWPFDocument document = new XWPFDocument(is);
//            for (XWPFParagraph paragraph : document.getParagraphs()) {
//                for (XWPFRun run : paragraph.getRuns()) {
//                    if (run.isBold()) {
//                        // Nếu từ hoặc đoạn văn bản này đã được đặt in đậm, tiếp tục chỉ đặt chữ in đậm
//                        run.setBold(true);
//                        run.setFontSize(10);
//                    }
//                }
//            }
//            for (XWPFTable table : document.getTables()) {
//                List<XWPFTableRow> rows = table.getRows();
//                for (XWPFTableRow row : rows) {
//                    List<XWPFTableCell> cells = row.getTableCells();
//                    for (XWPFTableCell cell : cells) {
//                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
//                            for (XWPFRun run : paragraph.getRuns()) {
//                                if (run.isBold()) {
//                                    run.setBold(true);
//                                    run.setFontSize(10);
//                                }
//                            }
//                        }
//                    }
//                }
//            }

            // Duyệt qua từng phần trong tài liệu
            List<IBodyElement> elements = document.getBodyElements();
            for (IBodyElement element : elements) {
                if (element instanceof XWPFParagraph) {
                    // Xử lý dòng văn bản
                    XWPFParagraph paragraph = (XWPFParagraph) element;
                    String text = paragraph.getText();
                    for (XWPFRun run : paragraph.getRuns()) {
                        if (run.isBold()) {
                            run.setBold(true);
//                            run.setFontSize(12);
                            run.setFontFamily("Times New Roman");
                        }
                    }
                } else if (element instanceof XWPFTable) {
                    XWPFTable table = (XWPFTable) element;
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                                for (XWPFParagraph cellParagraph : cell.getParagraphs()) {
                                    String cellParagraphText = cellParagraph.getText();
                                    for (XWPFRun run : cellParagraph.getRuns()) {
                                        if (run.isBold()) {
                                            run.setBold(true);
//                                            run.setFontSize(10);
                                            run.setFontFamily("Times New Roman");
                                        }
                                    }
                                }
                        }
                    }
                }
            }

            // 2) Prepare Pdf options
            PdfOptions options = PdfOptions.create();
            // 3) Convert XWPFDocument to Pdf
            PdfConverter.getInstance().convert(document, out, options);
            System.out.println("Success :: " + (System.currentTimeMillis() - start) + " milli seconds");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
