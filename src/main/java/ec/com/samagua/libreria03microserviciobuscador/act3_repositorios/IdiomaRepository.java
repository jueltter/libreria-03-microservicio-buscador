package ec.com.samagua.libreria03microserviciobuscador.act3_repositorios;

import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Autor;
import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Idioma;
import ec.com.samagua.libreria03microserviciobuscador.act2_jpa_repositorios.IdiomaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IdiomaRepository {

    private final IdiomaJpaRepository repository;

    public Optional<Idioma> findById(Long id) {
        return repository.findById(id);
    }
}
