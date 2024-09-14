package ec.com.samagua.libreria03microserviciobuscador.act1_entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categoria_libro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CategoriaLibro {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "categoria", referencedColumnName = "id")
    @ManyToOne
    private Categoria categoria;

    @JoinColumn(name = "libro", referencedColumnName = "id")
    @ManyToOne
    private Libro libro;

}
