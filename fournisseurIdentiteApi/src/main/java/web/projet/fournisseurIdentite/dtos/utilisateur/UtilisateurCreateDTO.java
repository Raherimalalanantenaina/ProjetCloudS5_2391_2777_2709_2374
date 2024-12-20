package web.projet.fournisseurIdentite.dtos.utilisateur;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UtilisateurCreateDTO {
    private String email;
    private String nom;
    private String prenom;
    private LocalDate date_naissance;
    private String mot_de_passe;
    private Long id_sexe;
}
