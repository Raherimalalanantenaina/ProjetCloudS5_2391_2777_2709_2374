package web.projet.fournisseurIdentite.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.mail.EmailConfig;
import web.projet.fournisseurIdentite.mail.EmailService;
import web.projet.fournisseurIdentite.mappers.UtilisateurMapper;
import web.projet.fournisseurIdentite.models.Configuration;
import web.projet.fournisseurIdentite.models.Token;
import web.projet.fournisseurIdentite.models.Utilisateur;
import web.projet.fournisseurIdentite.repositories.ConfigurationRepository;
import web.projet.fournisseurIdentite.repositories.SexeRepository;
import web.projet.fournisseurIdentite.repositories.TokenRepository;
import web.projet.fournisseurIdentite.repositories.UtilisateurRepository;

@Service
public class UtilisateurService {
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private UtilisateurMapper utilisateurMapper;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ConfigurationRepository configurationRepository;
    
    public UtilisateurDTO save(UtilisateurDTO data) {
        Utilisateur utilisateur = utilisateurMapper.toUtilisateur(data);
        return utilisateurMapper.toUtilisateurDTO(utilisateur);
    }

    public String inscrireUtilisateur(UtilisateurDTO dto) throws Exception {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findByEmail(dto.getEmail());
    
        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
    
            if (utilisateur.getEtat() == false) {
                Token token = tokenService.recupererTokenUtiliateur(dto);
    
                if (token.getDate_expiration().isBefore(LocalDateTime.now())) {
                    Token newToken = tokenRepository.save(tokenService.creationToken(utilisateur));
                    String newUrl = creationUrlValidation(newToken);
                    emailValidation(dto, newUrl);
                    return newUrl;
                }
    
                String url = creationUrlValidation(token);
                emailValidation(dto, url);
                return url;
            } else if (utilisateur.getEtat() == true) {
                throw new RuntimeException("L'adresse email est deja utilise");
            }
        }
    
        // Si aucun utilisateur n'existe avec cet email, en créer un nouveau
        
        dto.setMot_de_passe(BCrypt.hashpw(dto.getMot_de_passe(), BCrypt.gensalt(10)));
        dto.setEtat(false);
        Utilisateur utilisateur=utilisateurMapper.toUtilisateur(save(dto));
        utilisateur.setId(null);
        // System.out.println(utilisateur.toString());
        // Sexe sexe=sexeMapper.toSexe(dto.getSexe());
        // if(!sexeRepository.findById(sexe.getId()).isEmpty()){
        //     sexeRepository.save(sexe);
        // }
        utilisateur=utilisateurRepository.save(utilisateur);
        Token token = tokenRepository.save(tokenService.creationToken(utilisateur));
        String url = creationUrlValidation(token);
        emailValidation(dto, url);
    
        return url;
    }

    public String creationUrlValidation(Token token){
    
        String validationUrl = "http://localhost:8080/utilisateurs/valider-compte?token=" + token.getToken();
        return validationUrl;
    }


    public void emailValidation(UtilisateurDTO dto, String validationUrl) throws Exception {
        EmailConfig config = new EmailConfig("smtp.gmail.com", 587, "rarianamiadana@gmail.com", "mgxypljhfsktzlbk");
        String destinataires = dto.getEmail();
    
        String sujet = "Confirmation Email";
    
        String contenuHTML = "<!DOCTYPE html>" +
                "<html lang=\"fr\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Validation Email</title>" +
                "<style>" +
                "body {" +
                "font-family: Arial, sans-serif;" +
                "background-color: #f4f4f9;" +
                "color: #333;" +
                "display: flex;" +
                "justify-content: center;" +
                "align-items: center;" +
                "height: 100vh;" +
                "margin: 0;" +
                "}" +
                ".container {" +
                "background-color: white;" +
                "padding: 2rem;" +
                "border-radius: 8px;" +
                "box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);" +
                "text-align: center;" +
                "max-width: 400px;" +
                "width: 100%;" +
                "}" +
                "h1 {" +
                "color: #007BFF;" +
                "}" +
                "p {" +
                "margin: 1rem 0;" +
                "color: #555;" +
                "}" +
                "a {" +
                "display: inline-block;" +
                "padding: 0.8rem 1.2rem;" +
                "background-color: #007BFF;" +
                "color: white;" +
                "text-decoration: none;" +
                "border-radius: 4px;" +
                "transition: background-color 0.3s ease;" +
                "}" +
                "a:hover {" +
                "background-color: #0056b3;" +
                "}" +
                ".footer {" +
                "margin-top: 2rem;" +
                "font-size: 0.9rem;" +
                "color: #888;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"container\">" +
                "<h1>Validation de votre Email</h1>" +
                "<p>Merci pour la création de votre compte. Veuillez cliquer sur le lien ci-dessous pour valider votre adresse email :</p>" +
                "<a href=\"" + validationUrl + "\">Valider votre compte</a>" +
                "<div class=\"footer\">" +
                "<p>Si vous n'avez pas demandé cette validation, veuillez ignorer cet email.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    
        EmailService emailService = new EmailService(config);
        try {
            emailService.sendEmail(destinataires, sujet, contenuHTML);
        } catch (Exception e) {
            throw new Exception("Erreur lors de l'envoi de l'email de validation : " + e.getMessage(), e);
        }
    }


    public void validerCompte(String tokenStr) {

        Token token = tokenRepository.findByToken(tokenStr)
                .orElseThrow(() -> new RuntimeException("Token invalide ou expiré"));

        if(token.getDate_expiration().isBefore(LocalDateTime.now())){
            throw  new RuntimeException("Token invalide ou expiré");
        }   
        Utilisateur utilisateur = token.getUtilisateur();
        utilisateur.setEtat(true);
        Configuration configuration=configurationRepository.findByCle("nbtentative").get();
        utilisateur.setNb_tentative(Integer.parseInt(configuration.getValeur()));
        utilisateurRepository.save(utilisateur);
        tokenRepository.delete(token);
    }

}
