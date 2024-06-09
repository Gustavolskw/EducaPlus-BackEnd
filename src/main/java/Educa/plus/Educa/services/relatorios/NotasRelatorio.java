package Educa.plus.Educa.services.relatorios;

import Educa.plus.Educa.domain.atividades.DatasRecebeDTO;
import Educa.plus.Educa.domain.usuario.RelatorioDeNotasDTO;
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
public class NotasRelatorio {

    @Autowired
    UserRepository userRepository;

    public void writeTableDataForNotasRelatorio(PdfPTable table, DatasRecebeDTO datas) {

        List<Object[]> relatorioList = userRepository.relatorioNotasDosAlunos(datas.diaInit(), datas.diaFim(), datas.mesInicial(), datas.mesFinal(), datas.anoBusca());


        for (Object[] row : relatorioList) {
            String login = (String) row[0];
            String materia = (String) row[1];
            Object med =  row[2];
            Object max = row[3];
            Object min = row[4];
            Object prcnt = row[5];

            RelatorioDeNotasDTO relatorio = new RelatorioDeNotasDTO(login, materia, med, max, min, prcnt);

            table.addCell(relatorio.getLogin()).setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(relatorio.getMateria()).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(relatorio.getMedia().toString()).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(relatorio.getMaiorNota().toString()).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(relatorio.getMenorNota().toString()).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(relatorio.getPorcentAcima().toString()+"%").setHorizontalAlignment(Element.ALIGN_CENTER);
        }
    }
    public void writeTableHeaderForNotasRelatorio(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("LOGIN", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("MATERIA", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("MEDIA", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("MAIOR", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("MENOR", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell.setPhrase(new Phrase("PRCT_ACIMA_7", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

    }

}
