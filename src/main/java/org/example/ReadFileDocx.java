//package org.example;
//
//import org.apache.poi.xwpf.usermodel.*;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Iterator;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class ReadFileDocx {
//    public static void main(String[] args) {
//
////        try {
////            // Đường dẫn đến tệp DOCX chứa văn bản và bảng
////            String docxFilePath = "C:\\Users\\THINKPAD\\Desktop\\Công việc\\Data test\\BA Nguyễn Thị Hương .docx";
////            int count = 0 ;
////            // Mở tệp DOCX
////            FileInputStream fis = new FileInputStream(new File(docxFilePath));
////            XWPFDocument document = new XWPFDocument(fis);
////
////            // Duyệt qua từng phần trong tài liệu
////            List<IBodyElement> elements = document.getBodyElements();
////            for (IBodyElement element : elements) {
////                if (element instanceof XWPFParagraph) {
////                    // Xử lý dòng văn bản
////                    XWPFParagraph paragraph = (XWPFParagraph) element;
////                    String text = paragraph.getText();
////                    count ++;
////                    System.out.println("Line :"+count+" - Text : " + text );
////                } else if (element instanceof XWPFTable) {
////                    // Xử lý bảng
////                    XWPFTable table = (XWPFTable) element;
////                    List<XWPFTableRow> rows = table.getRows();
////                    for (XWPFTableRow row : rows) {
////                        List<XWPFTableCell> cells = row.getTableCells();
////                        for (XWPFTableCell cell : cells) {
////                                for (XWPFParagraph cellParagraph : cell.getParagraphs()) {
////                                    // Đọc nội dung của đoạn văn bản trong ô
////                                    String cellParagraphText = cellParagraph.getText();
////                                    count ++;
////                                        System.out.println("Line :"+count+" - Table :" + cellParagraphText);
////                                }
////                        }
////                    }
////                }else if (element instanceof XWPFPicture) {
////                    count ++;
////                    System.out.println("Line :"+count+" - Image:");
////                }
////            }
////            System.out.println(count);
////            fis.close();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//        String filePath = "C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\response.docx";
//      Long value =   countLineBufferedReader(filePath);
//        System.out.println(value);
//    }
//    public static long countLineBufferedReader(String fileName) {
//
//        long lines = 0;
//        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//            while (reader.readLine() != null) lines++;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return lines;
//
//    }
//    public static String highlightKeywords(String text, String keyword) {
//        // Tạo biểu thức chính quy để tìm từ khóa
//        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(text);
//
//        // Đánh dấu từ khóa bằng thẻ <mark>
//        StringBuffer result = new StringBuffer();
//        while (matcher.find()) {
//            matcher.appendReplacement(result, "<mark>" + matcher.group() + "</mark>");
//        }
//        matcher.appendTail(result);
//
//        return result.toString();
//    }
//
//}
