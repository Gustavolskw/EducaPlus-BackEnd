package Educa.plus.Educa.services.relatorios;

import Educa.plus.Educa.domain.feedbacks.RelatorioFeedBackDTO;
import Educa.plus.Educa.domain.usuario.AlunosParticipationRelDTO;
import Educa.plus.Educa.repositories.UserRepository;
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
public class ParticipacaoAlunosRel {

    @Autowired
    UserRepository userRepository;

    public void writeTableDataForUsersRel(PdfPTable table) {
        List<Object[]> relatorioList = userRepository.listaDeParticipacao();

        for (Object[] row : relatorioList) {
            String login = (String) row[0];
            Object atividadesFeitas =  row[1];
            String materia = (String) row[2];
            Object totalAtividades = row[3];
            Object porcentAtividadeFeita = row[4];

            AlunosParticipationRelDTO relatorio = new AlunosParticipationRelDTO(login, atividadesFeitas, materia, totalAtividades, porcentAtividadeFeita);

            table.addCell(relatorio.getLogin()).setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(relatorio.getAtividadesFeitas().toString()).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(relatorio.getMateria()).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(relatorio.getTotalAtividades().toString()).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(relatorio.getPorcentAtividadeFeitas().toString()+"%").setHorizontalAlignment(Element.ALIGN_CENTER);
        }
    }
    public void writeTableHeaderForUsersRel(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("LOGIN", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("ATVs_FEITAS", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("MATERIA", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("TOTAL_ATVs", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("PORCENT_FEITO", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

    }

}
