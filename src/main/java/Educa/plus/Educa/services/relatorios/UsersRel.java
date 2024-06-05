package Educa.plus.Educa.services.relatorios;

import Educa.plus.Educa.domain.usuario.UserRole;
import Educa.plus.Educa.domain.usuario.Usuario;
import Educa.plus.Educa.repositories.UserRepository;
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
public class UsersRel {

    @Autowired
    private UserRepository userRepository;

    public void writeTableDataForUsersRel(PdfPTable table) {
        List<Usuario> listUsers = userRepository.findAll();
        for (Usuario user : listUsers) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getLogin());
            if(user.getRole() == UserRole.ADMIN){
                table.addCell("Admin");
            }else if(user.getRole() == UserRole.STAFF){
                table.addCell("Professor");
            }else {
                table.addCell("Aluno");
            }
            table.addCell(user.getMateria() != null ? user.getMateria().getMateriaNome() : "");
            if(user.getAtivo()){
                table.addCell("ATIVO");
            }else{
                table.addCell("INATIVO");
            }

        }
    }

    public void writeTableHeaderForUsersRel(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("USER_ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("LOGIN", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("CARGO", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("MATERIA", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("ACESSO", font));
        table.addCell(cell);
    }
}
