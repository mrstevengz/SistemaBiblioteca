package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "libros")
@Getter
@Setter
public class Libro  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name= "titulo", length = 100, nullable = false)
    private String titulo;
    @Column(name= "anno publicacion", nullable = false)
    private Date anno;
    @ManyToOne(fetch = FetchType.LAZY) //Se establece la relacion muchos a uno entre libros y autores
    private Autor autor;
    @ManyToMany(fetch = FetchType.LAZY) //Se establece la relacion muchos a muchos entre libros y categorias
    private Set<Categoria> categorias;

    @Override
    public String toString() {
        List<String> nombresCategorias = new ArrayList<>();
        for (Categoria categoria : categorias) {
            nombresCategorias.add(categoria.getNombre());
        }
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor.getNombre() + '\'' +
                ", Año de publicacion='" + anno + '\'' +
                ", categorias=" + nombresCategorias+
                '}';
    }
}
