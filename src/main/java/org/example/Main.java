//package org.example;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.text.PDFTextStripper;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
//// then press Enter. You can now see whitespace characters in your code.
//public class Main {
//    public static void main(String[] args) {
//        try {
//            // Đường dẫn đến tệp PDF cần đọc
//            String pdfFilePath = "C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Vũ Tùng Lâm.pdf";
//
//            // Mở tệp PDF
//            PDDocument document = PDDocument.load(new File(pdfFilePath));
//
//            // Tạo một đối tượng PDFTextStripper để trích xuất nội dung
//            PDFTextStripper textStripper = new PDFTextStripper();
//
//            // Trích xuất nội dung từ tệp PDF
//            String pdfContent = textStripper.getText(document);
//
//            // Từ khóa bạn muốn tìm kiếm
//            String keyword = "VNPT";
//
//            // Chuyển nội dung PDF thành danh sách các dòng
//            String[] lines = pdfContent.split("\\r?\\n");
//            // Duyệt qua từng dòng để tìm kiếm từ khóa
//            String separator = "--------";
//            Map<String , List<String>> data = new HashMap<>();
//            List<String> resultList = new ArrayList<>();
////            List<PDFLine> resultList = new ArrayList<>();
//            for (int i = 0; i < lines.length; i++) {
////                if (lines[i].contains(keyword)) {
////                    // Nếu tìm thấy từ khóa, thêm dòng trước và dòng sau vào kết quả
////                    if (i > 0) {
////                        result.add(lines[i - 1]);
////                    }
////                    result.add(lines[i]);
////                    if (i < lines.length - 1) {
////                        result.add(lines[i + 1]);
////                    }
////                }
//                if (lines[i].contains(keyword)) {
//                    // Nếu tìm thấy từ khóa, thêm dòng trước và dòng sau vào danh sách
//                    String lineBefore = (i > 0) ? lines[i - 1] : "";
//                    String lineCurrent = lines[i];
//                    String lineAfter = (i < lines.length - 1) ? lines[i + 1] : "";
//
//                    resultList.add(lineBefore);
//                    resultList.add(lineCurrent);
//                    resultList.add(lineAfter);
//                    resultList.add(separator); // Thêm dấu phân biệt
//                }
////                if (lines[i].contains(keyword)) {
////                    // Nếu tìm thấy từ khóa, thêm dòng trước và dòng sau vào danh sách
////                    String lineBefore = (i > 0) ? lines[i - 1] : "";
////                    String lineCurrent = lines[i];
////                    String lineAfter = (i < lines.length - 1) ? lines[i + 1] : "";
////
////                    PDFLine pdfLine = new PDFLine(lineBefore + "\n" + lineCurrent + "\n" + lineAfter);
////                    resultList.add(pdfLine);
////                }
//            }
//            data.put(new File(pdfFilePath).getName(),resultList);
//            // In kết quả ra màn hình
////            for (PDFLine pdfLine : resultList) {
////                System.out.println(pdfLine.getLine());
////            }
////
//            for (String line : resultList) {
//                System.out.println(line);
//            }
////            for (Map.Entry<String, List<String>> entry : data.entrySet()) {
////                String key = entry.getKey();
////                List<String> value = entry.getValue();
////                System.out.println(key + " : " + value);
////            }
//            document.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}