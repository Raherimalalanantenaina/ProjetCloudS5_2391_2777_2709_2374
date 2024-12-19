package web.projet.fournisseurIdentite.models;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "configurations")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConfiguration;

    @Column(nullable = false, unique = true, length = 50)
    private String cle;

    @Column(nullable = false, length = 50)
    private String valeur;

    // Constructeurs

    public Configuration() {
    }

    public Configuration(String cle, String valeur) {
        this.cle = cle;
        this.valeur = valeur;
    }

    public Configuration(int idConfiguration, String cle, String valeur) {
        this.idConfiguration = idConfiguration;
        this.cle = cle;
        this.valeur = valeur;
    }

    // Getters et Setters

    public int getIdConfiguration() {
        return idConfiguration;
    }

    public void setIdConfiguration(int idConfiguration) {
        this.idConfiguration = idConfiguration;
    }

    public String getCle() {
        return cle;
    }

    public void setCle(String cle) {
        this.cle = cle;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    // Méthodes supplémentaires

    @Override
    public String toString() {
        return "Configuration{" +
                "idConfiguration=" + idConfiguration +
                ", cle='" + cle + '\'' +
                ", valeur='" + valeur + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return idConfiguration == that.idConfiguration &&
                Objects.equals(cle, that.cle) &&
                Objects.equals(valeur, that.valeur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConfiguration, cle, valeur);
    }
}
