package ec.com.samagua.libreria03microserviciobuscador.act3_repositorios;

import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Autor;
import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.AutorLibro;
import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Libro;
import ec.com.samagua.libreria03microserviciobuscador.act2_jpa_repositorios.AutorLibroJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AutorLibroRepository {

    private final AutorLibroJpaRepository repository;

    public AutorLibro save(AutorLibro entity) {
        return repository.save(entity);
    }

    public List<AutorLibro> findByLibro(Libro libro) {
        return repository.findByLibro(libro);

    };

    public List<AutorLibro> findByAutor(Autor autor) {
        return repository.findByAutor(autor);

    };

    public void delete(AutorLibro entity) {
        repository.delete(entity);
    }

    public AutorLibro save(Libro libro, Autor autor) {
        return save(AutorLibro.builder()
                .libro(libro)
                .autor(autor)
                .build());
    }

    public List<AutorLibro> saveAll(Libro libro, List<Autor> autores) {
        return autores.stream().map(obj -> save(libro, obj)).toList();
    }
}
