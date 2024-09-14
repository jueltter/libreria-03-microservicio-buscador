package ec.com.samagua.libreria03microserviciobuscador.act3_repositorios;

import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Categoria;
import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.CategoriaLibro;
import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Libro;
import ec.com.samagua.libreria03microserviciobuscador.act2_jpa_repositorios.CategoriaLibroJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoriaLibroRepository {

    private final CategoriaLibroJpaRepository repository;

    public List<CategoriaLibro> findByLibro(Libro libro) {
        return repository.findByLibro(libro);
    }

    public List<CategoriaLibro> findByCategoria(Categoria categoria) {
        return repository.findByCategoria(categoria);
    }
    public CategoriaLibro save(CategoriaLibro entity) {
        return repository.save(entity);
    }

    public void delete(CategoriaLibro entity) {
        repository.delete(entity);
    }

    public CategoriaLibro save(Libro libro, Categoria categoria) {
        return save(CategoriaLibro.builder()
                .libro(libro)
                .categoria(categoria)
                .build());
    }
    public List<CategoriaLibro> saveAll(Libro libro, List<Categoria> categorias) {
        return categorias.stream().map(obj -> save(libro, obj)).toList();
    }
}
