package org.example.Code.service.Impl;

import org.elasticsearch.client.RestHighLevelClient;
import org.example.Code.base.BaseAbstract;
import org.example.Code.service.ELKService;
import org.example.Code.service.ReadFileService;
import org.springframework.stereotype.Service;

import java.io.File;
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

    @Override
    public void pushDataFromDB() {

    }
}
