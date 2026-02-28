package TravelAgency.util.file_strategy;

import TravelAgency.TravelAgency.dtos.VacantaDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PdfStrategy implements FileStrategy {
    @Override
    public void generateVacanteFile(List<VacantaDTO> vacante) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, 700);
                for (VacantaDTO vacanta : vacante) {
                    contentStream.setLeading(14.5f);
                    contentStream.showText(vacanta.toString());
                    contentStream.newLine();
                }
                contentStream.endText();
            }

            document.save("vacante.pdf");
        } catch (IOException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
        }
    }
}
