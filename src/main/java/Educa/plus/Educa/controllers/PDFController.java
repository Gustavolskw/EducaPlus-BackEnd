package Educa.plus.Educa.controllers;

import Educa.plus.Educa.domain.atividades.DatasRecebeDTO;
import Educa.plus.Educa.services.PDFGenerator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/alunos/notas")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void generatePDFPNotas(
            HttpServletResponse response,
            @RequestParam(name = "diaInit") int diaInit,
            @RequestParam(name = "diaFim") int diaFim,
            @RequestParam(name = "mesInicial") int mesInicial,
            @RequestParam(name = "mesFinal") int mesFinal,
            @RequestParam(name = "anoBusca") int anoBusca
    ) {
        // Your logic to generate and return the PDF based on the parameters
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + "Relatorio de Notas_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        DatasRecebeDTO datasRecebeDTO = new DatasRecebeDTO(diaInit, diaFim, mesInicial, mesFinal, anoBusca);
        this.pdfGenerator.relatorioDeNotaDosAlunos(response, datasRecebeDTO);
    }

    @GetMapping("/posts/atividades")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void generatePDFPostProfessores(
            HttpServletResponse response,
            @RequestParam(name = "diaInit") int diaInit,
            @RequestParam(name = "diaFim") int diaFim,
            @RequestParam(name = "mesInicial") int mesInicial,
            @RequestParam(name = "mesFinal") int mesFinal,
            @RequestParam(name = "anoBusca") int anoBusca
    ) {
        // Your logic to generate and return the PDF based on the parameters
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + "Relatorio_de_Posts_Professores_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        DatasRecebeDTO datasRecebeDTO = new DatasRecebeDTO(diaInit, diaFim, mesInicial, mesFinal, anoBusca);
        this.pdfGenerator.relatorioDePostProfessores(response, datasRecebeDTO);
    }

}
