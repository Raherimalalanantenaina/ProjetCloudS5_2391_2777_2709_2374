package web.projet.fournisseurIdentite.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import web.projet.fournisseurIdentite.mail.EmailConfig;
import web.projet.fournisseurIdentite.mail.EmailService;
import web.projet.fournisseurIdentite.models.CodePin;
import web.projet.fournisseurIdentite.models.Utilisateur;
import web.projet.fournisseurIdentite.repositories.CodePinRepository;

@Service
public class CodePinService {
    @Autowired
    private CodePinRepository codePinRepository;

    public int envoyerCodePin(Utilisateur utilisateur) throws Exception {
        int code = (int) (Math.random() * 900000) + 100000;
        LocalDateTime expiration = LocalDateTime.now().plusSeconds(90);
        while(codePinRepository.findByCodepin(code).isPresent()){
            // codePinRepository.delete(codePinRepository.findByCodepin(code).get());
            code = (int) (Math.random() * 900000) + 100000;
        }
        CodePin codePin = new CodePin();
        codePin.setCodepin(code);
        codePin.setDateExpiration(expiration);
        codePin.setUtilisateur(utilisateur);

        
        codePinRepository.save(codePin);

       
        EmailConfig config = new EmailConfig("smtp.gmail.com", 587, "rarianamiadana@gmail.com", "mgxypljhfsktzlbk");
        String destinataire = utilisateur.getEmail();
        String message = "Code Pin de Confirmation";
        String htmlContent = """
            <p>Bonjour %s %s,</p>
            <p>Votre code PIN de confirmation est :</p>
            <h2>%d</h2>
            <p>Ce code est valable pour les 90 prochaines secondes.</p>
            <p>Merci,</p>
            <p>L'équipe de Fournisseur Identité.</p>
        """.formatted(utilisateur.getPrenom(), utilisateur.getNom(), code);
        EmailService emailService = new EmailService(config);
        try {
            emailService.sendEmail(destinataire, message, htmlContent);
            return code;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean verifierCodePin(Integer codepin, Long utilisateurId) {
        Optional<CodePin> code = codePinRepository.findByCodepin(codepin);
    
        if (code.isPresent() && code.get().getUtilisateur().getId().equals(utilisateurId)) {
            if (code.get().getDateExpiration().isAfter(LocalDateTime.now())) {
                codePinRepository.delete(code.get());
                return true;
            }
        }
        return false;
    }
    
}
