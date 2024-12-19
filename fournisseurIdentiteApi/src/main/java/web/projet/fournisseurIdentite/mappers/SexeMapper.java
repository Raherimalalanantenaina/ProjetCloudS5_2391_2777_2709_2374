package web.projet.fournisseurIdentite.mappers;

import org.mapstruct.Mapper;

import web.projet.fournisseurIdentite.dtos.sexe.SexeDTO;
import web.projet.fournisseurIdentite.models.Sexe;

@Mapper(componentModel = "spring")
public interface SexeMapper {
    
    Sexe toSexe(SexeDTO sexeDTO);

    SexeDTO toSexeDTO(Sexe sexe);

}
