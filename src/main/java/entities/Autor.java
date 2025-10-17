package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name = "autores")
@Getter @Setter

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    @Column(name = "nacionalidad", length = 100, nullable = false)
    private String nacionalidad;
    @Column(name = "fechanacimiento", nullable = false)
    private Date fechanacimiento;
}
