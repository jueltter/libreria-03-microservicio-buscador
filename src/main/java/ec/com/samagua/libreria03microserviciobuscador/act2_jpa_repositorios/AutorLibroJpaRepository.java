package ec.com.samagua.libreria03microserviciobuscador.act2_jpa_repositorios;

import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Autor;
import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.AutorLibro;
import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AutorLibroJpaRepository extends JpaRepository<AutorLibro, Long>, JpaSpecificationExecutor<AutorLibro> {
    List<AutorLibro> findByLibro(Libro libro);
    List<AutorLibro> findByAutor(Autor autor);
}


