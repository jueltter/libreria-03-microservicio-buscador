package ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriteriosBusqueda {
    private String titulo;

    private Integer anioPublicacion;

    private String isbn10;

    private String isbn13;

    private Long idioma;

    private Boolean disponibilidad;

    private Long autor;

    private Long categoria;
}
