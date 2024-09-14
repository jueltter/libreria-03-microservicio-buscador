package ec.com.samagua.libreria03microserviciobuscador.act2_jpa_repositorios;

import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Categoria;
import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.CategoriaLibro;
import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CategoriaLibroJpaRepository extends JpaRepository<CategoriaLibro, Long>, JpaSpecificationExecutor<CategoriaLibro> {

    List<CategoriaLibro> findByLibro(Libro libro);

    List<CategoriaLibro> findByCategoria(Categoria categoria);
}


