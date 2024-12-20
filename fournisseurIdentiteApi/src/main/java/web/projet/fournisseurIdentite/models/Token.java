package web.projet.fournisseurIdentite.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
@Getter
@Setter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_token;

    private String token;

    private LocalDateTime date_expiration;

    @ManyToOne
    @JoinColumn(name = "id_utilisateurs")
    private Utilisateur utilisateur;
}