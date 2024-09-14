package ec.com.samagua.libreria03microserviciobuscador.act1_entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "libro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Libro {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    @Column(name = "isbn10")
    private String isbn10;

    @Column(name = "isbn13")
    private String isbn13;

    @Column(name = "portada")
    private String portada;

    @JoinColumn(name = "idioma", referencedColumnName = "id")
    @ManyToOne
    private Idioma idioma;

    @Column(name = "sinopsis")
    private String sinopsis;

    @Column(name = "disponibilidad")
    private Boolean disponibilidad;

    @Transient
    private List<Autor> autores;

    @Transient
    private List<Categoria> categorias;

}
