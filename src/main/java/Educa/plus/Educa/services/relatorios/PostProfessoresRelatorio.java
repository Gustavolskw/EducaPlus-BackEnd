package Educa.plus.Educa.services.relatorios;

import Educa.plus.Educa.domain.atividades.DatasRecebeDTO;
import Educa.plus.Educa.domain.usuario.RelatorioDeNotasDTO;
import Educa.plus.Educa.domain.usuario.RelatorioPostProfessores;
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
public class PostProfessoresRelatorio {

    @Autowired
    UserRepository userRepository;

    public void writeTableDataForPostsRelatorio(PdfPTable table, DatasRecebeDTO datas) {

        List<Object[]> relatorioList = userRepository.relatorioDePostagens(datas.diaInit(), datas.diaFim(), datas.mesInicial(), datas.mesFinal(), datas.anoBusca());


        for (Object[] row : relatorioList) {
            String login = (String) row[0];
            Object qtdAtividadesPostadas =  row[1];
            String materia = (String) row[2];
            Object porcentualPostado = row[3];
            Object totalAtividades = row[4];

            RelatorioPostProfessores relopt = new RelatorioPostProfessores(login, qtdAtividadesPostadas, materia, porcentualPostado, totalAtividades);

            table.addCell(relopt.getProfessor());
            table.addCell(relopt.getQtdPosts().toString());
            table.addCell(relopt.getMateria());
            table.addCell(relopt.getPorcentagemPost().toString()+"%");
            table.addCell(relopt.getTotalAtividades().toString());
        }
    }
    public void writeTableHeaderForPostsRelatorio(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("PROFESSOR", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("POSTS", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("MATERIA", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("PRCT_POST", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("TOTAL_ATV", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }
}
