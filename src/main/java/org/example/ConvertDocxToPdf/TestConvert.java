package org.example.ConvertDocxToPdf;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.List;

public class TestConvert {
    public static void main(String[] args) {
        try (InputStream is = new FileInputStream(new File("C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Nguyễn Thị Hương .docx"));
             OutputStream out = new FileOutputStream(new File("C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\test03102023.pdf"));) {
            long start = System.currentTimeMillis();
            XWPFDocument document = new XWPFDocument(is);
            List<IBodyElement> elements = document.getBodyElements();
            for (IBodyElement element : elements) {
                if (element instanceof XWPFParagraph) {
                    XWPFParagraph paragraph = (XWPFParagraph) element;
                    String text = paragraph.getText();
                    for (XWPFRun run : paragraph.getRuns()) {
                        if (run.isBold()) {
                            run.setBold(true);
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
                                            run.setFontFamily("Times New Roman");
                                        }
                                    }
                                }
                        }
                    }
                }
            }

            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(document, out, options);
            System.out.println("Success :: " + (System.currentTimeMillis() - start) + " milli seconds");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
