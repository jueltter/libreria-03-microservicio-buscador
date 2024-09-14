package ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Disponibilidad {
    private String codigo;
    private boolean disponibilidad;
}
