package web.projet.fournisseurIdentite.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.repositories.UtilisateurRepository;
import web.projet.fournisseurIdentite.services.TokenService;
import web.projet.fournisseurIdentite.services.UtilisateurService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;

    @Autowired
    private TokenService tokenService;

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
}
