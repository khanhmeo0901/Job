package org.example.ConvertDocxToPdf;
import com.aspose.words.*;
public class Main {
    public static void main(String[] args) {
        try {
                Document document = new Document("C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Nguyễn Thị Hương .docx");
                document.save("C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Nguyễn Thị Hương.pdf");
            System.out.println("Convert Success !");
        }catch (Exception e) {
            e.getMessage();
        }
    }

}
