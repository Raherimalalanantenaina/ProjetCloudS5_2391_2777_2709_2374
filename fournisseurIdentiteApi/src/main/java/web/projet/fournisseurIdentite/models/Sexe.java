package web.projet.fournisseurIdentite.models;

import java.time.*;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@SuppressWarnings("unused")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sexes")
public class Sexe {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_sexe")
   private Integer id;

   @Column(name = "sexe")
   private String sexe;

}