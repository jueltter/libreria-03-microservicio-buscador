package ec.com.samagua.libreria03microserviciobuscador.act3_repositorios;

import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.*;
import ec.com.samagua.libreria03microserviciobuscador.act2_jpa_repositorios.LibroJpaRepository;
import ec.com.samagua.libreria03microserviciobuscador.act3_repositorios_utils.SearchCriteria;
import ec.com.samagua.libreria03microserviciobuscador.act3_repositorios_utils.SearchOperation;
import ec.com.samagua.libreria03microserviciobuscador.act3_repositorios_utils.SearchStatement;
import ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos.CriteriosBusqueda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LibroRepository {

    private final LibroJpaRepository repository;
    private final IdiomaRepository idiomaRepository;
    private final CategoriaRepository categoriaRepository;
    private final CategoriaLibroRepository categoriaLibroRepository;
    private final AutorRepository autorRepository;
    private final AutorLibroRepository autorLibroRepository;

    public Libro save(Libro entity) {
        return repository.save(entity);
    }

    public Optional<Libro> findById(Long id) {
        return repository.findById(id);

    }

    public List<Libro> findByIsbn10(String isbn10) {
        return repository.findByIsbn10(isbn10);

    }

    public List<Libro> findByIsbn13(String isbn13) {
        return repository.findByIsbn13(isbn13);

    }

    public void delete(Libro entity) {
        repository.delete(entity);
    }

    public List<Libro> search(CriteriosBusqueda criterios) {
        SearchCriteria spec = new SearchCriteria();
        if (criterios.getIdioma() != null) {
            Optional<Idioma> idioma = idiomaRepository.findById(criterios.getIdioma());
            idioma.ifPresent(value -> spec.add(new SearchStatement("idioma", value, SearchOperation.EQUAL)));

        }
        if (criterios.getCategoria() != null) {
            Optional<Categoria> categoria = categoriaRepository.findById(criterios.getCategoria());
            if (categoria.isPresent()) {
                List<Long> libros = categoriaLibroRepository.findByCategoria(categoria.get()).stream().map(CategoriaLibro::getLibro).map(Libro::getId).toList();
                if (!libros.isEmpty()) {
                    spec.add(new SearchStatement("id", libros, SearchOperation.IS_MEMBER));
                }
            }
        }
        if (criterios.getAutor() != null) {
            Optional<Autor> autor = autorRepository.findById(criterios.getAutor());
            if (autor.isPresent()) {
                List<Long> libros = autorLibroRepository.findByAutor(autor.get()).stream().map(AutorLibro::getLibro).map(Libro::getId).toList();
                if (!libros.isEmpty()) {
                    spec.add(new SearchStatement("id", libros, SearchOperation.IS_MEMBER));
                }
            }
        }
        if (criterios.getTitulo() != null && !criterios.getTitulo().isBlank()) {
            spec.add(new SearchStatement("titulo", criterios.getTitulo(), SearchOperation.MATCH));
        }
        if (criterios.getIsbn10() != null && !criterios.getIsbn10().isBlank()) {
            spec.add(new SearchStatement("isbn10", criterios.getIsbn10(), SearchOperation.EQUAL));
        }
        if (criterios.getIsbn13() != null && !criterios.getIsbn13().isBlank()) {
            spec.add(new SearchStatement("isbn13", criterios.getIsbn13(), SearchOperation.EQUAL));
        }
        if (criterios.getAnioPublicacion() != null) {
            spec.add(new SearchStatement("anioPublicacion", criterios.getAnioPublicacion(), SearchOperation.EQUAL));
        }
        if (criterios.getDisponibilidad() != null) {
            spec.add(new SearchStatement("disponibilidad", criterios.getDisponibilidad(), SearchOperation.EQUAL));
        }
        return repository.findAll(spec);
    }
}
