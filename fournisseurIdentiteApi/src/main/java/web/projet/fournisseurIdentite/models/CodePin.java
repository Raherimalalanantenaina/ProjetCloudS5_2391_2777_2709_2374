package web.projet.fournisseurIdentite.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "code_pin")
public class CodePin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCodePin;

    @Column(nullable = false, unique = true)
    private int codepin;

    @Column(nullable = false)
    private LocalDateTime dateExpiration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateurs", nullable = false)
    private Utilisateur utilisateur;

    // Getters et Setters
    public int getIdCodePin() {
        return idCodePin;
    }

    public void setIdCodePin(int idCodePin) {
        this.idCodePin = idCodePin;
    }

    public int getCodepin() {
        return codepin;
    }

    public void setCodepin(int codepin) {
        this.codepin = codepin;
    }

    public LocalDateTime getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDateTime dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
