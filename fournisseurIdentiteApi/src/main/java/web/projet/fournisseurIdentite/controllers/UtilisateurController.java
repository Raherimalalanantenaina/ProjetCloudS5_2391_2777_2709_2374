package web.projet.fournisseurIdentite.controllers;

import web.projet.fournisseurIdentite.dtos.utilisateur.ConnexionDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurUpdateDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.ValidationPinDTO;
import web.projet.fournisseurIdentite.models.Utilisateur;
import web.projet.fournisseurIdentite.repositories.UtilisateurRepository;
import web.projet.fournisseurIdentite.services.CodePinService;
import web.projet.fournisseurIdentite.services.TokenService;
import web.projet.fournisseurIdentite.services.UtilisateurService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CodePinService codePinService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping
    public UtilisateurDTO create(@RequestBody UtilisateurDTO data) {
        return utilisateurService.save(data);
    }

    @PostMapping("/inscrire")
    public ResponseEntity<?> inscrireUtilisateur(@RequestBody UtilisateurDTO dto) {
        try {
            String url = utilisateurService.inscrireUtilisateur(dto);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/valider-compte")
    public ResponseEntity<String> validerCompte(@RequestParam String token) {
        try {
            utilisateurService.validerCompte(token);
            return ResponseEntity.ok("Compte validé avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
       @Operation(summary = "Connexion utilisateur", description = "Permet à un utilisateur de se connecter et génère un code PIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Connexion réussie et code PIN envoyé"),
        @ApiResponse(responseCode = "404", description = "Utilisateur introuvable")
    })
    @PostMapping("/connexion")
    public ResponseEntity<?> connexion(@RequestBody ConnexionDTO dto) throws Exception {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(dto.getEmail());
        if (utilisateurOpt.isPresent()) {
            String email = utilisateurService.connexion(dto.getEmail(), dto.getMotDePasse());
            if (email.startsWith("0x0:")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(email.replace("0x0:", ""));
            }
            int code = codePinService.envoyerCodePin(utilisateurOpt.get());
            return ResponseEntity.ok(code);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable.");
    }
}
