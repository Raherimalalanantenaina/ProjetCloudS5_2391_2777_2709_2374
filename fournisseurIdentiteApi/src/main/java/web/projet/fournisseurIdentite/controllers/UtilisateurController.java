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
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Utilisateur API", description = "API pour gérer les utilisateurs")
@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @Autowired
    private CodePinService codePinService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Operation(summary = "Créer un utilisateur", description = "Ajoute un nouvel utilisateur dans le système.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Utilisateur créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides fournies")
    })
    @PostMapping
    public UtilisateurDTO create(@RequestBody UtilisateurDTO data) {
        return utilisateurService.save(data);
    }

    @Operation(summary = "Mettre à jour un utilisateur", description = "Met à jour les informations d'un utilisateur existant.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès"),
        @ApiResponse(responseCode = "403", description = "Session expirée ou non valide"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id, 
            @RequestBody UtilisateurUpdateDTO data,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (tokenService.isTokenValid(token)) {
            return ResponseEntity.ok(utilisateurService.update(id, data));
        } else {
            return ResponseEntity.status(403).body("Session expirée");
        }
    }

    @Operation(summary = "Inscrire un utilisateur", description = "Inscription d'un nouvel utilisateur avec une URL de confirmation.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Utilisateur inscrit avec succès"),
        @ApiResponse(responseCode = "400", description = "Erreur lors de l'inscription")
    })
    @PostMapping("/inscrire")
    public ResponseEntity<?> inscrireUtilisateur(@RequestBody UtilisateurDTO dto) {
        try {
            String url = utilisateurService.inscrireUtilisateur(dto);
            return ResponseEntity.ok(url);
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

    @Operation(summary = "Valider un compte utilisateur", description = "Valide un compte utilisateur à l'aide d'un token.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Compte validé avec succès"),
        @ApiResponse(responseCode = "400", description = "Token invalide")
    })
    @GetMapping("/valider-compte")
    public ResponseEntity<String> validerCompte(@RequestParam String token) {
        try {
            utilisateurService.validerCompte(token);
            return ResponseEntity.ok("Compte validé avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Réinitialiser les tentatives", description = "Réinitialise les tentatives de connexion d'un utilisateur.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tentatives réinitialisées avec succès"),
        @ApiResponse(responseCode = "400", description = "Token invalide")
    })
    @GetMapping("/reinitialiser-tentative")
    public ResponseEntity<String> reinitialiserTentative(@RequestParam String token) {
        try {
            utilisateurService.reinitialiserTentative(token);
            return ResponseEntity.ok("Tentatives réinitialisées avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Valider un code PIN", description = "Valide le code PIN saisi par l'utilisateur.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Code PIN validé et token généré"),
        @ApiResponse(responseCode = "400", description = "Code PIN non valide ou expiré")
    })
    @PostMapping("/valider-pin")
    public ResponseEntity<String> validationPin(@RequestBody ValidationPinDTO validationPinDTO) {
        try {
            String tokens = utilisateurService.validationPin(validationPinDTO);
            if (tokens != null && tokens.startsWith("0x0:")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tokens.replace("0x0:", ""));
            }
            if (tokens == null) {
                throw new RuntimeException();
            }
            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Code PIN non valide ou expiré");
        }
    }
}
