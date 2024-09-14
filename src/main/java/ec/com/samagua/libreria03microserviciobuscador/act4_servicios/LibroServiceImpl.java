package ec.com.samagua.libreria03microserviciobuscador.act4_servicios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.*;
import ec.com.samagua.libreria03microserviciobuscador.act3_repositorios.*;
import ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos.CriteriosBusqueda;
import ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos.LibroDto;
import ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos.LibroValidation;
import ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos.Resultado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private CategoriaLibroRepository categoriaLibroRepository;

    @Autowired
    private AutorLibroRepository autorLibroRepository;

    @Autowired
    private LibroValidation validation;

    @Autowired
    private ObjectMapper objectMapper;


    public Resultado<List<Libro>> search(CriteriosBusqueda criterios) {
        List<Libro> entities = repository.search(criterios);
        entities.forEach(entity -> {
            List<CategoriaLibro> categoriaLibroList = categoriaLibroRepository.findByLibro(entity);
            entity.setCategorias(categoriaLibroList.stream().map(obj -> obj.getCategoria()).toList());

            List<AutorLibro> autorLibroList = autorLibroRepository.findByLibro(entity);
            entity.setAutores(autorLibroList.stream().map(obj -> obj.getAutor()).toList());
        });
        return new Resultado<>(entities, Collections.emptyList());
    }

    public Resultado<Boolean> delete(Long id) {
        if (id == null) {
            return new Resultado<>(false, Collections.singletonList("id es obligatorio"));
        }

        Libro entity = repository.findById(id).orElse(null);

        if (entity == null) {
            return new Resultado<>(false, Collections.singletonList("id es incorrecto"));
        }

        categoriaLibroRepository.findByLibro(entity).forEach(obj -> categoriaLibroRepository.delete(obj));
        autorLibroRepository.findByLibro(entity).forEach(obj -> autorLibroRepository.delete(obj));

        repository.delete(entity);

        return new Resultado<>(true, Collections.emptyList());
    }

    public Resultado<Libro> search(Long id) {
        if (id == null) {
            return new Resultado<>(null, Collections.singletonList("id es obligatorio"));
        }

        Libro entity = repository.findById(id).orElse(null);

        if (entity == null) {
            return new Resultado<>(null, Collections.singletonList("id es incorrecto"));
        }

        List<CategoriaLibro> categoriaLibroList = categoriaLibroRepository.findByLibro(entity);
        entity.setCategorias(categoriaLibroList.stream().map(obj -> obj.getCategoria()).toList());

        List<AutorLibro> autorLibroList = autorLibroRepository.findByLibro(entity);
        entity.setAutores(autorLibroList.stream().map(obj -> obj.getAutor()).toList());

        return new Resultado<>(entity, Collections.emptyList());
    }

    public Resultado<Libro> updatePatch(Long id, String patch) {
        try {
            Libro entity = repository.findById(id).get();

            JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(patch));
            JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(entity)));
            Libro patched = objectMapper.treeToValue(target, Libro.class);
            repository.save(patched);
            return new Resultado<>(patched, Collections.emptyList());
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Resultado<Libro> update(Long id, LibroDto dto) {
        List<String> errores = validation.erroresUpdate(id, dto);

        if (!errores.isEmpty()) {
            return new Resultado<>(null, errores);
        }

        Idioma idioma = idiomaRepository.findById(dto.getIdioma()).get();

        Libro entity = repository.findById(dto.getId()).get();
        entity.setTitulo(dto.getTitulo());
        entity.setAnioPublicacion(dto.getAnioPublicacion());
        entity.setIsbn10(dto.getIsbn10());
        entity.setIsbn13(dto.getIsbn13());
        entity.setPortada(dto.getPortada());
        entity.setIdioma(idioma);
        entity.setSinopsis(dto.getSinopsis());

        entity = repository.save(entity);

        categoriaLibroRepository.findByLibro(entity).forEach(obj -> categoriaLibroRepository.delete(obj));
        autorLibroRepository.findByLibro(entity).forEach(obj -> autorLibroRepository.delete(obj));

        List<Categoria> categorias = dto.getCategorias().stream().map(obj -> categoriaRepository.findById(obj).get()).toList();
        List<CategoriaLibro> categoriaLibroList = categoriaLibroRepository.saveAll(entity, categorias);
        entity.setCategorias(categoriaLibroList.stream().map(obj -> obj.getCategoria()).toList());

        List<Autor> autores = dto.getAutores().stream().map(obj -> autorRepository.findById(obj).get()).toList();
        List<AutorLibro> autorLibroList = autorLibroRepository.saveAll(entity, autores);
        entity.setAutores(autorLibroList.stream().map(obj -> obj.getAutor()).toList());

        return new Resultado<>(entity, Collections.emptyList());
    }

    public Resultado<Libro> create(LibroDto dto) {
        List<String> errores = validation.erroresNuevo(dto);

        if (!errores.isEmpty()) {
            return new Resultado<>(null, errores);
        }

        Idioma idioma = idiomaRepository.findById(dto.getIdioma()).get();

        Libro entity = Libro.builder()
                .titulo(dto.getTitulo())
                .anioPublicacion(dto.getAnioPublicacion())
                .isbn10(dto.getIsbn10())
                .isbn13(dto.getIsbn13())
                .portada(dto.getPortada())
                .idioma(idioma)
                .sinopsis(dto.getSinopsis())
                .disponibilidad(dto.getDisponibilidad())
                .build();

        entity = repository.save(entity);

        List<Categoria> categorias = dto.getCategorias().stream().map(obj -> categoriaRepository.findById(obj).get()).toList();
        List<CategoriaLibro> categoriaLibroList = categoriaLibroRepository.saveAll(entity, categorias);
        entity.setCategorias(categoriaLibroList.stream().map(obj -> obj.getCategoria()).toList());

        List<Autor> autores = dto.getAutores().stream().map(obj -> autorRepository.findById(obj).get()).toList();
        List<AutorLibro> autorLibroList = autorLibroRepository.saveAll(entity, autores);
        entity.setAutores(autorLibroList.stream().map(obj -> obj.getAutor()).toList());

        return new Resultado<>(entity, Collections.emptyList());
    }


}
