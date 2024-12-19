package web.projet.fournisseurIdentite.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.mappers.UtilisateurMapper;
import web.projet.fournisseurIdentite.models.Token;
import web.projet.fournisseurIdentite.models.Utilisateur;
import web.projet.fournisseurIdentite.repositories.TokenRepository;

@Service
public class TokenService {
    @Autowired 
    private UtilisateurMapper utilisateurMapper;
    @Autowired
    private TokenRepository tokenRepository;

    public Token creationToken(Utilisateur utilisateur){
        String tokenStr=UUID.randomUUID().toString();
        Token token= new Token();
        token.setToken(tokenStr);
        token.setUtilisateur(utilisateur);
        token.setDate_expiration(LocalDateTime.now().plusMinutes(1));
        return token;
    }

    public Token recupererTokenUtiliateur(UtilisateurDTO utilisateurDTO ){
        Utilisateur utilisateur=utilisateurMapper.toUtilisateur(utilisateurDTO);
        Token token=tokenRepository.findByUtilisateur(utilisateur).get();
        return token;
    }
}
