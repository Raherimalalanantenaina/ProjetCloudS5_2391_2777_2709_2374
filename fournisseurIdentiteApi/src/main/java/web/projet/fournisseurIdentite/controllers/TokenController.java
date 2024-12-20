package web.projet.fournisseurIdentite.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.projet.fournisseurIdentite.services.TokenService;

@RestController
@RequestMapping("/api/tokens")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/valider-token")
    public ResponseEntity<String> validerToken(@RequestParam String token) {
        if (tokenService.isTokenValid(token)) {
            return ResponseEntity.ok("Token valide.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expiré. Veuillez vous reconnecter.");
        }
    }
}

