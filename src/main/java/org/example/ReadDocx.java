package org.example;

import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ReadDocx {
    public static void main(String[] args) {
        try {
            String list = "";
            String keyword = "Knowledge";
            String docxFilePath = "C:\\Users\\THINKPAD\\Desktop\\Công việc\\Data test\\BA Nguyễn Thị Hương .docx";

            FileInputStream fis = new FileInputStream(new File(docxFilePath));
            long modifiedTimeMillis = new File(docxFilePath).lastModified();
            Date modifiedDate = new Date(modifiedTimeMillis);
            System.out.println(modifiedDate);

            XWPFDocument document = new XWPFDocument(fis);
            StringBuilder previousText = null;
            List<IBodyElement> elements = document.getBodyElements();
            for (IBodyElement element : elements) {

                if (element instanceof XWPFParagraph) {
                    XWPFParagraph paragraph = (XWPFParagraph) element;
                    String text = paragraph.getText();
                    list += text+"\n";
                } else if (element instanceof XWPFTable) {
                    XWPFTable table = (XWPFTable) element;
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            String cellText = cell.getText();
                            list +=cellText+ "\t";
                        }
                        list +="\n";
                    }
                }
            }
//            System.out.println(list);

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
