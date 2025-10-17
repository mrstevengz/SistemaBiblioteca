package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "categorias")
@Getter @Setter
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    @ManyToMany //Se establece la relacion muchos a muchos entre categorias y libros
    private Set<Libro> libros;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Categoria{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", libros=").append(libros);
        sb.append('}');
        return sb.toString();
    }
}