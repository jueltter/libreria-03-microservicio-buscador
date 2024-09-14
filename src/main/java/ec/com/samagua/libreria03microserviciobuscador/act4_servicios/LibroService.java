package ec.com.samagua.libreria03microserviciobuscador.act4_servicios;

import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Libro;
import ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos.CriteriosBusqueda;
import ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos.LibroDto;
import ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos.Resultado;

import java.util.List;

public interface LibroService {

    Resultado<Libro> create(LibroDto dto);

    Resultado<Libro> update(Long id, LibroDto dto);

    Resultado<Libro> updatePatch(Long id, String patch);

    Resultado<Libro> search(Long id);

    Resultado<Boolean> delete(Long id) ;

    Resultado<List<Libro>> search(CriteriosBusqueda criterios);
}
