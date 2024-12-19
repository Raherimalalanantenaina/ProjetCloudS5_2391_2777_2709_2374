package web.projet.fournisseurIdentite.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "utilisateurs")
@Getter
@Setter
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateurs")
    private Long id;

    private String email;
    private String nom;
    private String prenom;
    private LocalDate date_naissance;
    private String mot_de_passe;
    private Boolean etat = false;
    private Integer nb_tentative = 0;
    
    @ManyToOne
    @JoinColumn(name = "id_sexe")
    private Sexe sexe;

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", date_naissance=" + date_naissance +
                ", mot_de_passe='[PROTÉGÉ]'" + 
                ", etat=" + etat +
                ", nb_tentative=" + nb_tentative +
                ", sexe=" + (sexe != null ? sexe.getSexe() : "Non spécifié") +
                '}';
    }

}
