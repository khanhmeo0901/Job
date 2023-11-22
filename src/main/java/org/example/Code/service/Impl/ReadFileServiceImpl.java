package org.example.Code.service.Impl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.example.Code.base.BaseAbstract;
import org.example.Code.service.ELKService;
import org.example.Code.service.ReadFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
@Service
public class ReadFileServiceImpl extends BaseAbstract implements ReadFileService {
    private ELKService elkService;
    private final int THREAD_POOL_SIZE = 5;
    private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    public ReadFileServiceImpl(RestHighLevelClient client, ELKService elkService) {
        super(client);
        this.elkService = elkService;
    }
    @Override
    public void filePath(String folderPath, String index) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            try {
                if (files != null) {
                    for (File file : files) {
                        String fileName = file.getName();
                        executorService.submit(() -> {
                            if (fileName.endsWith(".docx")) {
                                getDataFileDocx(file);
                                pushDataFileDocxToELK(file,index);
                            } else if (fileName.endsWith(".pdf")) {
                                getDataFilePdf(file);
                                pushDataFilePdfToELK(file,index);
                            }
//                            else if(fileName.endsWith(".doc")) {
//
//                            }
                        });
                    }
                }
                executorService.shutdown();
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                System.out.println("Successfully !!!");
            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            System.err.println("Folder does not exist or is not a directory.");
        }
    }

//    @Override
//    public void pushDataFromDB() {
//
//    }
//
//    @Override
//    public void test(MultipartFile file, String index) {
//
//    }

    @Override
    public List<String>  readFileDocx() {
        String filePath ="C:\\Users\\khanh.nguyen01\\Desktop\\BA Nguyễn Thị Hương  - Copy";
        byte[] byteData = readDocxFileToByteArray(new File(filePath));
        try {
            // Tạo InputStream từ byte array
            InputStream inputStream = new ByteArrayInputStream(byteData);
            List<String> list = new ArrayList<>();
            XWPFDocument document = new XWPFDocument(inputStream);
            List<IBodyElement> elements = document.getBodyElements();
            for (IBodyElement element : elements) {
                if (element instanceof XWPFParagraph) {
                    XWPFParagraph paragraph = (XWPFParagraph) element;
                    String text = paragraph.getText();
                    list.add(text);
                } else if (element instanceof XWPFTable) {
                    XWPFTable table = (XWPFTable) element;
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            for (XWPFParagraph cellParagraph : cell.getParagraphs()) {
                                String cellParagraphText = cellParagraph.getText();
                                list.add(cellParagraphText);
                            }
                        }
                    }
                } else if (element instanceof XWPFPicture) {
                }
            }
            inputStream.close();
            return list;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private byte[] readDocxFileToByteArray(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            byte[] byteArray = Files.readAllBytes(file.toPath());
            inputStream.close();
            return byteArray;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] readPdfFileToByteArray(File file) {
        try {
//            String filePath ="C:\\Users\\khanh.nguyen01\\Desktop\\data test\\BA Nguyễn Thị Hương  - Copy - Copy - Copy - Copy - Copy - Copy - Copy - Copy - Copy - Copy - Copy - Copy - Copy - Copy - Copy.pdf";
//            File file = new File(filePath);
            PDDocument document = PDDocument.load(file);
            byte[] byteArray = Files.readAllBytes(file.toPath());
            document.close();
            return byteArray;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> readFilePdf() {
        byte[] byteData = readPdfFileToByteArray(new File("123"));
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteData);
            PDDocument document = PDDocument.load(inputStream);
            PDFTextStripper textStripper = new PDFTextStripper();
            String pdfContent = textStripper.getText(document);
            String[] lines = pdfContent.split("\\r?\\n");
            List<String> listData = new ArrayList<>();
            for (int i = 0; i < lines.length; i++) {
                listData.add(lines[i]);
            }
            document.close();
            return listData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
