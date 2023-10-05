//package org.example.Code;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.text.PDFTextStripper;
//import org.apache.poi.xwpf.usermodel.*;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//public class ReadFile {
//    private static final int THREAD_POOL_SIZE = 5;
//    private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
//
//    public static void filePath(String folderPath) throws InterruptedException {
//        File folder = new File(folderPath);
//
//        if (folder.exists() && folder.isDirectory()) {
//            File[] files = folder.listFiles();
//
//            if (files != null) {
//                for (File file : files) {
//                    String fileName = file.getName();
//                    executorService.submit(() -> {
//                        if (fileName.endsWith(".docx")) {
//                            getDataFileDocx(file);
//                            ELK.pushDataFileDocxToELK(file);
//                        } else if (fileName.endsWith(".pdf")) {
//                            getDataFilePdf(file);
//                            ELK.pushDataFilePdfToELK(file);
//                        }
//                    });
//                }
//            }
//            // Chờ cho tất cả các luồng hoàn thành
//            executorService.shutdown();
//            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//        } else {
//            System.err.println("Folder does not exist or is not a directory.");
//        }
//    }
//    public static List<String> getDataFilePdf(File file) {
//        try {
//            int count = 0 ;
//           // String pdfFilePath = "C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Vũ Tùng Lâm.pdf";
//            PDDocument document = PDDocument.load(file);
//            PDFTextStripper textStripper = new PDFTextStripper();
//            String pdfContent = textStripper.getText(document);
//            String[] lines = pdfContent.split("\\r?\\n");
//            List<String> listData = new ArrayList<>();
//            for (int i = 0; i < lines.length; i++) {
//                count++;
//                listData.add("Line :" +count +" - Text : "+lines[i]);
//            }
//            document.close();
//            return listData;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public static List<String> getDataFileDocx(File file) {
//        try {
//            List<String> list = new ArrayList<>();
//            // Đường dẫn đến tệp DOCX chứa văn bản và bảng
//            //String docxFilePath = "C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Nguyễn Thị Hương .docx";
//            int count = 0;
//            FileInputStream fis = new FileInputStream(file);
//            XWPFDocument document = new XWPFDocument(fis);
//            List<IBodyElement> elements = document.getBodyElements();
//            for (IBodyElement element : elements) {
//                if (element instanceof XWPFParagraph) {
//                    XWPFParagraph paragraph = (XWPFParagraph) element;
//                    String text = paragraph.getText();
//                    count++;
//                    list.add("Line :" + count + " - Text : " + text);
//                } else if (element instanceof XWPFTable) {
//                    XWPFTable table = (XWPFTable) element;
//                    List<XWPFTableRow> rows = table.getRows();
//                    for (XWPFTableRow row : rows) {
//                        List<XWPFTableCell> cells = row.getTableCells();
//                        for (XWPFTableCell cell : cells) {
//                            for (XWPFParagraph cellParagraph : cell.getParagraphs()) {
//                                String cellParagraphText = cellParagraph.getText();
//                                count++;
//                                list.add("Line :" + count + " - Table :" + cellParagraphText);
//                            }
//                        }
//                    }
//                } else if (element instanceof XWPFPicture) {
//                    count++;
//                    System.out.println("Line :" + count + " - Image:");
//                }
//            }
//            System.out.println(count);
//            fis.close();
//            return list;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
