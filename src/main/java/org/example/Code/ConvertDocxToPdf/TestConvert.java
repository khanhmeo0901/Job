//package org.example.Code.ConvertDocxToPdf;
//
//import org.apache.poi.xwpf.converter.pdf.PdfConverter;
//import org.apache.poi.xwpf.converter.pdf.PdfOptions;
//import org.apache.poi.xwpf.usermodel.*;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.Executor;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class TestConvert {
//    public static void main(String[] args) {
//        try {
//            // Tạo ThreadPoolExecutor cho CompletableFuture
//            Executor executor = Executors.newFixedThreadPool(5);
//            String folder = "D:\\Data 04_10_2023\\Folder File Docx";
//            File dir = new File(folder);
//            File[] files = dir.listFiles();
//            if (files != null) {
//                // Tạo một danh sách chứa CompletableFuture
//                List<CompletableFuture<Void>> futures = new ArrayList<>();
//                for (File file : files) {
//                    CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
//                        try {
//                            InputStream is = new FileInputStream(file);
//                            String fileName = file.getName();
//                            if (fileName.contains(".docx")) {
//                                String newFileName = fileName.replace(".docx", ".pdf");
//                                OutputStream out = new FileOutputStream("C:\\Users\\khanh.nguyen01\\Desktop\\data test\\" + newFileName);
//                                long start = System.currentTimeMillis();
//                                XWPFDocument document = new XWPFDocument(is);
//                                PdfOptions options = PdfOptions.create();
//                                PdfConverter.getInstance().convert(document, out, options);
//                                System.out.println("Success :: " + (System.currentTimeMillis() - start) + " milli seconds");
//
//                            }
//                        } catch (Exception e) {
//                            e.getMessage();
//                        }
//
//                        return null;
//                    }, executor);
//                    futures.add(future);
//                }
//                // Chờ cho tất cả CompletableFuture hoàn thành
//                CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
//                allOf.get(); // Đợi hoàn thành
//                System.out.println("------------------------END-----------------------------");
//            }
//            ((ExecutorService) executor).shutdown();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//    }
//
//}
