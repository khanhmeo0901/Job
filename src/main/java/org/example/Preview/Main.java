package org.example.Preview;

import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.options.HtmlViewOptions;

public class Main {
    public static void main(String[] args) {
        // Set up input DOCX file
        String filePath = "C:\\Users\\ADMIN\\Desktop\\Công Việc\\Data test\\BA Nguyễn Thị Hương .docx";

        // Instantiate Viewer
        try (Viewer viewer = new Viewer(filePath))
        {
            // Set view options
            HtmlViewOptions viewOptions = HtmlViewOptions.forEmbeddedResources();

            // Render DOCX file to HTML with embedded resources
            viewer.view(viewOptions);
        }
    }
}
