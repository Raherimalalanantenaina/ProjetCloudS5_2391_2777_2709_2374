package web.projet.fournisseurIdentite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import web.projet.fournisseurIdentite.dtos.sexe.SexeDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurCreateDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurDTO;
import web.projet.fournisseurIdentite.dtos.utilisateur.UtilisateurUpdateDTO;
import web.projet.fournisseurIdentite.models.Utilisateur;

@Mapper(componentModel = "spring" , uses = SexeDTO.class)
public interface UtilisateurMapper {
    
    Utilisateur toUtilisateur(UtilisateurDTO utilisateurDTO);

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "etat" , ignore = true)
    @Mapping(target = "sexe" , ignore = true)
    @Mapping(target = "nb_tentative" , ignore = true)
    Utilisateur toUtilisateur(UtilisateurCreateDTO utilisateurCreateDTO);

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "email" , ignore = true)
    @Mapping(target = "etat" , ignore = true)
    @Mapping(target = "sexe" , ignore = true)
    @Mapping(target = "nb_tentative" , ignore = true)
    Utilisateur toUtilisateur(UtilisateurUpdateDTO utilisateurCreateDTO);

    UtilisateurDTO toUtilisateurDTO(Utilisateur utilisateur);

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "email" , ignore = true)
    @Mapping(target = "etat" , ignore = true)
    @Mapping(target = "sexe" , ignore = true)
    @Mapping(target = "nb_tentative" , ignore = true)
    void updateUtilisateurFromDTO(UtilisateurUpdateDTO utilisateurUpdateDTO, @MappingTarget Utilisateur utilisateur);

}
