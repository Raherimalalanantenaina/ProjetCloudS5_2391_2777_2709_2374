package web.projet.fournisseurIdentite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.projet.fournisseurIdentite.models.CodePin;
import web.projet.fournisseurIdentite.models.Utilisateur;

import java.util.Optional;

@Repository
public interface CodePinRepository extends JpaRepository<CodePin, Integer> {
    Optional<CodePin> findByCodepin(int codePin);
    Optional<CodePin> findByUtilisateur(Utilisateur utilisateur);

}

