package ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibroDto {
    private Long id;

    private String titulo;

    private Integer anioPublicacion;

    private String isbn10;

    private String isbn13;

    private String portada;

    private Long idioma;

    private String sinopsis;

    private Boolean disponibilidad;

    private List<Long> autores;

    private List<Long> categorias;
}
