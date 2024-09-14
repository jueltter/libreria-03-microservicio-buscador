package ec.com.samagua.libreria03microserviciobuscador.act1_entidades;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "critica")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Critica {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha")
    private Timestamp fecha;

    @Column(name = "valoracion")
    private int valoracion;

    @Column(name = "descripcion")
    private String descripcion;

    @JoinColumn(name = "Libro", referencedColumnName = "id")
    @ManyToOne
    private Libro libro;
}
