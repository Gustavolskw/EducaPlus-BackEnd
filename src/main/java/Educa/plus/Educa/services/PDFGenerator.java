package Educa.plus.Educa.services;

import Educa.plus.Educa.services.relatorios.FeedbackRel;
import Educa.plus.Educa.services.relatorios.ParticipacaoAlunosRel;
import Educa.plus.Educa.services.relatorios.UsersRel;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class PDFGenerator {

    @Autowired
    private UsersRel usersRel;

    @Autowired
    private FeedbackRel feedbackRel;

    @Autowired
    private ParticipacaoAlunosRel participacaoAlunosRel;

    private void mainHeader(Document document, PdfWriter writer){


        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_BOLD);
        fontTitle.setSize(26);
        Paragraph headertext = new Paragraph("EducaPlus", fontTitle);
        headertext.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(headertext);


        PdfContentByte canvas = writer.getDirectContent();
        float yPosition = document.getPageSize().getHeight() - 80;
        canvas.setLineWidth(1);
        canvas.moveTo(document.left(), yPosition);
        canvas.lineTo(document.right(), yPosition);
        canvas.stroke();


        document.add(Chunk.NEWLINE);
    }

    public void relatorioDeUsuarios(HttpServletResponse response) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            mainHeader(document, writer);

            // Adicionando o subtítulo
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(18);
            font.setColor(Color.BLUE);
            Paragraph p = new Paragraph("Lista de Usuarios", font);
            p.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p);

            // Adicionando a tabela
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100f);
            table.setWidths(new float[]{1.5f, 2f, 1.8f, 2f, 2f});
            table.setSpacingBefore(10);

            usersRel.writeTableHeaderForUsersRel(table);
            usersRel.writeTableDataForUsersRel(table);
            document.add(table);

            document.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    public void relatorioDeFeedbacks(HttpServletResponse response) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            mainHeader(document, writer);

            // Adicionando o subtítulo
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(18);
            font.setColor(Color.BLUE);
            Paragraph p = new Paragraph("Relatorio de Feedbacks para cada materia", font);
            p.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p);

            // Adicionando a tabela
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100f);
            table.setWidths(new float[]{2.2f, 2f, 1.8f, 1.8f, 1.9f, 2.5f});
            table.setSpacingBefore(10);

            feedbackRel.writeTableHeaderForUsersRel(table);
            feedbackRel.writeTableDataForUsersRel(table);
            document.add(table);

            document.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void relatorioDeParticipacaoDosAlunos(HttpServletResponse response) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            mainHeader(document, writer);

            // Adicionando o subtítulo
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(18);
            font.setColor(Color.BLUE);
            Paragraph p = new Paragraph("Relatorio de Participacao dos Alunos", font);
            p.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p);

            // Adicionando a tabela
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100f);
            table.setWidths(new float[]{1.4f, 1.8f, 2f, 2f, 2.2f});
            table.setSpacingBefore(10);

            participacaoAlunosRel.writeTableHeaderForUsersRel(table);
            participacaoAlunosRel.writeTableDataForUsersRel(table);
            document.add(table);

            document.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }






}
