package org.example;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.extractor.WordExtractor;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
//        String path ="C:\\Users\\khanh.nguyen01\\Downloads\\BA-Nguyễn-Thị-Hương-Copy (1).doc";
//        List<String> data = readDocFile(new File(path));
//        System.out.println(data);
        test();
    }
    public static List<String> readDocFile(File filePath) {
        List<String> list = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(filePath);

            // Create an instance of HWPFDocument
            HWPFDocument document = new HWPFDocument(fis);

            // Get the range of the document
            Range range = document.getRange();

            // Print the text content of the document
            System.out.println(range.text());
            list.add(range.text());
            // Close the FileInputStream
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void test() throws IOException {
        //            // Đường dẫn đến tệp tin DOC
            String filePath = "C:\\Users\\khanh.nguyen01\\Downloads\\BA-Nguyễn-Thị-Hương-Copy (1).doc";

            // Sử dụng thư viện Apache POI để đọc tệp tin DOC
            FileInputStream fis = new FileInputStream(filePath);
            HWPFDocument document = new HWPFDocument(fis);

            // Lấy đối tượng Range để đọc toàn bộ nội dung của tài liệu
            Range range = document.getRange();

            // Lặp qua các đoạn văn bản trong Range và in ra
            for (int i = 0; i < range.numParagraphs(); i++) {
                Paragraph paragraph = range.getParagraph(i);
                System.out.println(paragraph.text());
            }

            // Đóng FileInputStream sau khi sử dụng
            fis.close();
//        String FilePath = "C:\\Users\\khanh.nguyen01\\Downloads\\BA-Nguyễn-Thị-Hương-Copy (1).doc";
//        FileInputStream fis;
//
//        if(FilePath.substring(FilePath.length() -1).equals("x")){ //is a docx
//            try {
//                fis = new FileInputStream(new File(FilePath));
//                XWPFDocument doc = new XWPFDocument(fis);
//                XWPFWordExtractor extract = new XWPFWordExtractor(doc);
//                System.out.println(extract.getText());
//            } catch (IOException e) {
//
//                e.printStackTrace();
//            }
//        } else { //is not a docx
//            try {
//                fis = new FileInputStream(new File(FilePath));
//                HWPFDocument doc = new HWPFDocument(fis);
//                WordExtractor extractor = new WordExtractor(doc);
//                System.out.println(extractor.getText());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}



