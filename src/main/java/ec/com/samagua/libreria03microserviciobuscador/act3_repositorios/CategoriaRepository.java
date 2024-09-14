package ec.com.samagua.libreria03microserviciobuscador.act3_repositorios;

import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Categoria;
import ec.com.samagua.libreria03microserviciobuscador.act2_jpa_repositorios.CategoriaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoriaRepository {

    private final CategoriaJpaRepository repository;

    public Optional<Categoria> findById(Long id) {
        return repository.findById(id);
    }
}
