package ar.edu.utn.frc.tup.lciii.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Entity
@Table(name = "Countries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private long population;
    private double area;
    private String code;
}
//c. Guardar en la base de datos: Luego, se debe guardar en la base de datos los países seleccionados,
// incluyendo los campos de nombre, código, población (population) y área (Ademas de un id autoincremental).