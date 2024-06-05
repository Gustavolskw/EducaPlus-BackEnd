package Educa.plus.Educa.controllers;

import Educa.plus.Educa.services.PDFGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("relatorios")
public class PDFController {

    @Autowired
    private PDFGenerator pdfGenerator;

    @GetMapping("/users")
    public void generatePDFDeUsers(HttpServletResponse response){
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + "Relatorio de Usuarios_"+currentDateTime+ ".pdf";
        response.setHeader(headerKey, headerValue);
        this.pdfGenerator.relatorioDeUsuarios(response);
    }

    @GetMapping("/feedbacks")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void generatePDFFeedBacks(HttpServletResponse response){
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + "Relatorio de FeedBacks_" +currentDateTime+ ".pdf";
        response.setHeader(headerKey, headerValue);
        this.pdfGenerator.relatorioDeFeedbacks(response);
    }

    @GetMapping("/alunos/parti")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void generatePDFParticipacao(HttpServletResponse response){
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + "Relatorio de Participacao_" +currentDateTime+ ".pdf";
        response.setHeader(headerKey, headerValue);
        this.pdfGenerator.relatorioDeParticipacaoDosAlunos(response);
    }

}
