package web.projet.fournisseurIdentite.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import web.projet.fournisseurIdentite.dtos.sexe.SexeDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurCreateDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurUpdateDTO;
import web.projet.fournisseurIdentite.models.Sexe;
import web.projet.fournisseurIdentite.models.Utilisateur;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-19T21:49:32+0300",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20241215-0944, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class UtilisateurMapperImpl implements UtilisateurMapper {

    @Override
    public Utilisateur toUtilisateur(UtilisateurDTO utilisateurDTO) {
        if ( utilisateurDTO == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setDate_naissance( utilisateurDTO.getDate_naissance() );
        utilisateur.setEmail( utilisateurDTO.getEmail() );
        utilisateur.setEtat( utilisateurDTO.getEtat() );
        utilisateur.setId( utilisateurDTO.getId() );
        utilisateur.setMot_de_passe( utilisateurDTO.getMot_de_passe() );
        utilisateur.setNb_tentative( utilisateurDTO.getNb_tentative() );
        utilisateur.setNom( utilisateurDTO.getNom() );
        utilisateur.setPrenom( utilisateurDTO.getPrenom() );
        utilisateur.setSexe( sexeDTOToSexe( utilisateurDTO.getSexe() ) );

        return utilisateur;
    }

    @Override
    public Utilisateur toUtilisateur(UtilisateurCreateDTO utilisateurCreateDTO) {
        if ( utilisateurCreateDTO == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setDate_naissance( utilisateurCreateDTO.getDate_naissance() );
        utilisateur.setEmail( utilisateurCreateDTO.getEmail() );
        utilisateur.setMot_de_passe( utilisateurCreateDTO.getMot_de_passe() );
        utilisateur.setNom( utilisateurCreateDTO.getNom() );
        utilisateur.setPrenom( utilisateurCreateDTO.getPrenom() );

        return utilisateur;
    }

    @Override
    public Utilisateur toUtilisateur(UtilisateurUpdateDTO utilisateurCreateDTO) {
        if ( utilisateurCreateDTO == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setDate_naissance( utilisateurCreateDTO.getDate_naissance() );
        utilisateur.setMot_de_passe( utilisateurCreateDTO.getMot_de_passe() );
        utilisateur.setNom( utilisateurCreateDTO.getNom() );
        utilisateur.setPrenom( utilisateurCreateDTO.getPrenom() );

        return utilisateur;
    }

    @Override
    public UtilisateurDTO toUtilisateurDTO(Utilisateur utilisateur) {
        if ( utilisateur == null ) {
            return null;
        }

        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();

        utilisateurDTO.setDate_naissance( utilisateur.getDate_naissance() );
        utilisateurDTO.setEmail( utilisateur.getEmail() );
        utilisateurDTO.setEtat( utilisateur.getEtat() );
        utilisateurDTO.setId( utilisateur.getId() );
        utilisateurDTO.setMot_de_passe( utilisateur.getMot_de_passe() );
        utilisateurDTO.setNb_tentative( utilisateur.getNb_tentative() );
        utilisateurDTO.setNom( utilisateur.getNom() );
        utilisateurDTO.setPrenom( utilisateur.getPrenom() );
        utilisateurDTO.setSexe( sexeToSexeDTO( utilisateur.getSexe() ) );

        return utilisateurDTO;
    }

    @Override
    public void updateUtilisateurFromDTO(UtilisateurUpdateDTO utilisateurUpdateDTO, Utilisateur utilisateur) {
        if ( utilisateurUpdateDTO == null ) {
            return;
        }

        utilisateur.setDate_naissance( utilisateurUpdateDTO.getDate_naissance() );
        utilisateur.setMot_de_passe( utilisateurUpdateDTO.getMot_de_passe() );
        utilisateur.setNom( utilisateurUpdateDTO.getNom() );
        utilisateur.setPrenom( utilisateurUpdateDTO.getPrenom() );
    }

    protected Sexe sexeDTOToSexe(SexeDTO sexeDTO) {
        if ( sexeDTO == null ) {
            return null;
        }

        Sexe sexe = new Sexe();

        sexe.setId( sexeDTO.getId() );
        sexe.setSexe( sexeDTO.getSexe() );

        return sexe;
    }

    protected SexeDTO sexeToSexeDTO(Sexe sexe) {
        if ( sexe == null ) {
            return null;
        }

        SexeDTO sexeDTO = new SexeDTO();

        sexeDTO.setId( sexe.getId() );
        sexeDTO.setSexe( sexe.getSexe() );

        return sexeDTO;
    }
}
