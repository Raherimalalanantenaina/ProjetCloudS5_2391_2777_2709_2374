package web.projet.fournisseurIdentite.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import web.projet.fournisseurIdentite.dtos.sexe.SexeDTO;
import web.projet.fournisseurIdentite.models.Sexe;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-20T14:24:18+0300",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class SexeMapperImpl implements SexeMapper {

    @Override
    public Sexe toSexe(SexeDTO sexeDTO) {
        if ( sexeDTO == null ) {
            return null;
        }

        Sexe sexe = new Sexe();

        sexe.setId( sexeDTO.getId() );
        sexe.setSexe( sexeDTO.getSexe() );

        return sexe;
    }

    @Override
    public SexeDTO toSexeDTO(Sexe sexe) {
        if ( sexe == null ) {
            return null;
        }

        SexeDTO sexeDTO = new SexeDTO();

        sexeDTO.setId( sexe.getId() );
        sexeDTO.setSexe( sexe.getSexe() );

        return sexeDTO;
    }
}
