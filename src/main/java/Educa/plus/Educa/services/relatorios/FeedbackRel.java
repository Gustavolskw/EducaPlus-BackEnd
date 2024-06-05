package Educa.plus.Educa.services.relatorios;

import Educa.plus.Educa.domain.feedbacks.RelatorioFeedBackDTO;
import Educa.plus.Educa.repositories.FeedbackRepository;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class FeedbackRel {

    @Autowired
    FeedbackRepository feedbackRepository;



    public void writeTableDataForUsersRel(PdfPTable table) {
        List<Object[]> relatorioList = feedbackRepository.buscaRelatorioDeAtividades();

        for (Object[] row : relatorioList) {
            String materia = (String) row[0];
            Object feedbacks = row[1];
            Object neutras =  row[2];
            Object positivas = row[3];
            Object negativas =row[4];
            Object porcentPos = row[5];

            RelatorioFeedBackDTO relatorio = new RelatorioFeedBackDTO(materia, feedbacks, neutras, positivas, negativas, porcentPos);

            table.addCell(relatorio.getMateria()).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(relatorio.getFeedbacks().toString()).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(relatorio.getPositivas().toString()).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(relatorio.getNeutras().toString()).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(relatorio.getNegativas().toString()).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(relatorio.getPorcentPos().toString()+"%").setHorizontalAlignment(Element.ALIGN_CENTER);
        }
    }
    public void writeTableHeaderForUsersRel(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("MATERIA", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("FEEDBACKS", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("POSITIVOS", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("NEUTROS", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("NEGATIVOS", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("PORCENT_POS", font));
        table.addCell(cell);

    }


}
