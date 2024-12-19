package web.projet.fournisseurIdentite.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.mappers.UtilisateurMapper;
import web.projet.fournisseurIdentite.models.Token;
import web.projet.fournisseurIdentite.models.Utilisateur;
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
                    // String newUrl = creationUrlValidation(newToken);
                    // emailValidation(dto, newUrl);
                    return "newUrl";
                }
    
                // String url = creationUrlValidation(token);
                // emailValidation(dto, url);
                return "url";
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
        // String url = creationUrlValidation(token);
        // emailValidation(dto, url);
    
        return "url";
    }

}
