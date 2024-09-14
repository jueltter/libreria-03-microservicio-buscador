package ec.com.samagua.libreria03microserviciobuscador.act3_repositorios;

import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Autor;
import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Categoria;
import ec.com.samagua.libreria03microserviciobuscador.act2_jpa_repositorios.AutorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AutorRepository {

    private final AutorJpaRepository repository;

    public Optional<Autor> findById(Long id) {
        return repository.findById(id);
    }
}
