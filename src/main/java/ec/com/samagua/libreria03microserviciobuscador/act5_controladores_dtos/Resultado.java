package ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resultado<E> {
    private E valor;
    private List<String> errores;
}
