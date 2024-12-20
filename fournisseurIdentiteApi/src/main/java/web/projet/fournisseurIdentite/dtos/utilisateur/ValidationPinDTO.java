package web.projet.fournisseurIdentite.dtos.utilisateur;

public class ValidationPinDTO {

    private String email;
    private int codepin;

    // Constructeur par défaut
    public ValidationPinDTO() {
    }

    // Constructeur avec arguments
    public ValidationPinDTO(String email, int codepin) {
        this.email = email;
        this.codepin = codepin;
    }

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCodepin() {
        return codepin;
    }

    public void setCodepin(int codepin) {
        this.codepin = codepin;
    }

    // Méthode toString (facultative)
    @Override
    public String toString() {
        return "ValidationPinDTO{" +
                "email='" + email + '\'' +
                ", codepin=" + codepin +
                '}';
    }
}

