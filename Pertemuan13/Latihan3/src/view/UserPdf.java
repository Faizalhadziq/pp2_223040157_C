package Pertemuan13.Latihan3.src.view;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import Pertemuan13.Latihan3.src.model.User;

import java.io.FileOutputStream;
import java.util.List;

public class UserPdf {
    public static void exportPdf(List<User> users) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("users.pdf"));
            document.open();
            for (User user : users) {
                document.add(new Paragraph(user.getName() + " - " + user.getEmail()));
            }
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage());
        }
    }
}


