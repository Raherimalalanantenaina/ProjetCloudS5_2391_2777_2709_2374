package web.projet.fournisseurIdentite.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.projet.fournisseurIdentite.models.Configuration;
@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {
    Optional<Configuration> findByCle(String cle);
}

