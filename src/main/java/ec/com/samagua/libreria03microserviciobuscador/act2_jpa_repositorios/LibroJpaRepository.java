package ec.com.samagua.libreria03microserviciobuscador.act2_jpa_repositorios;

import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LibroJpaRepository extends JpaRepository<Libro, Long>, JpaSpecificationExecutor<Libro> {

    public List<Libro> findByIsbn10(String isbn10);
    public List<Libro> findByIsbn13(String isbn13);
}


