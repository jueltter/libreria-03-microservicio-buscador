package ec.com.samagua.libreria03microserviciobuscador.act1_entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "autor_libro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AutorLibro {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "libro", referencedColumnName = "id")
    @ManyToOne
    private Libro libro;

    @JoinColumn(name = "autor", referencedColumnName = "id")
    @ManyToOne
    private Autor autor;


}
