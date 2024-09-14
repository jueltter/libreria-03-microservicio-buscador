package ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos;

import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Libro;
import ec.com.samagua.libreria03microserviciobuscador.act3_repositorios.AutorRepository;
import ec.com.samagua.libreria03microserviciobuscador.act3_repositorios.CategoriaRepository;
import ec.com.samagua.libreria03microserviciobuscador.act3_repositorios.IdiomaRepository;
import ec.com.samagua.libreria03microserviciobuscador.act3_repositorios.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class LibroValidation {
    @Autowired
    private LibroRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private IdiomaRepository idiomaRepository;

    private List<String> erroresGenerales(LibroDto dto) {
        List<String> errores = new ArrayList<>();

        boolean categoriasValidas = validarCategorias(dto.getCategorias());
        if (!categoriasValidas) {
            errores.add("Categoría es incorrecto");
        }

        boolean autoresValidos = validarAutores(dto.getAutores());
        if (!autoresValidos) {
            errores.add("Autor es incorrecto");
        }

        boolean idiomaValido = validarIdioma(dto.getIdioma());
        if (!idiomaValido) {
            errores.add("Idioma es incorrecto");
        }

        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            errores.add("Título es incorrecto");
        }

        if (dto.getAnioPublicacion() == null) {
            errores.add("Año de publicación es incorrecto");

        }

        if (dto.getPortada() == null || dto.getPortada().isBlank()) {
            errores.add("Portada es incorrecto");
        }

        if (dto.getSinopsis() == null || dto.getSinopsis().isBlank()) {
            errores.add("Sinopsis es incorrecto");
        }

        if (dto.getDisponibilidad() == null) {
            errores.add("Disponibilidad es incorrecto");
        }

        return errores;
    }

    public List<String> erroresUpdate(Long id, LibroDto dto) {
        if (!id.equals(dto.getId())) {
            return Collections.singletonList("libro es incorrecto");
        }

        List<String> errores = new ArrayList<>();

        Libro entity = repository.findById(id).get();

        boolean validarIsbn10 = validarIsbn10(entity.getIsbn10(), dto.getIsbn10());
        if (!validarIsbn10) {
            errores.add("isbn10 es incorrecto");
        }

        boolean validarIsbn13 = validarIsbn13(entity.getIsbn13(), dto.getIsbn13());
        if (!validarIsbn13) {
            errores.add("isbn13 is incorrecto");
        }

        errores.addAll(erroresGenerales(dto));
        return errores;
    }

    private boolean validarIsbn10(String actual, String nuevo) {
        if (nuevo == null) {
            return Boolean.FALSE;
        }

        if (actual != null  && actual.equals(nuevo)) {
            return Boolean.TRUE;
        }

        return repository.findByIsbn10(nuevo).isEmpty();
    }

    private boolean validarIsbn13(String actual, String nuevo) {
        if (nuevo == null) {
            return Boolean.FALSE;
        }

        if (actual != null  && actual.equals(nuevo)) {
            return Boolean.TRUE;
        }

        return repository.findByIsbn13(nuevo).isEmpty();
    }

    public List<String> erroresNuevo(LibroDto dto) {

        if (dto == null || dto.getId() != null) {
            return Collections.singletonList("libro es incorrecto");
        }

        List<String> errores = new ArrayList<>();

        boolean validarIsbn10 = validarIsbn10(null, dto.getIsbn10());
        if (!validarIsbn10) {
            errores.add("isbn10 es incorrecto");
        }

        boolean validarIsbn13 = validarIsbn13(null, dto.getIsbn13());
        if (!validarIsbn13) {
            errores.add("isbn13 is incorrecto");
        }

        errores.addAll(erroresGenerales(dto));
        return errores;

    }

    private boolean validarCategorias(List<Long> categorias) {
        if (categorias == null) {
            return false;
        }

        if (categorias.isEmpty()) {
            return false;
        }

        if (categorias.stream().anyMatch(obj -> obj == null)) {
            return false;
        }

        if (categorias.stream().map(obj -> categoriaRepository.findById(obj)).anyMatch(obj -> !obj.isPresent())) {
            return false;
        }

        return true;
    }

    private boolean validarAutores(List<Long> autores) {
        if (autores == null) {
            return false;
        }

        if (autores.isEmpty()) {
            return false;
        }

        if (autores.stream().anyMatch(obj -> obj == null)) {
            return false;
        }

        if (autores.stream().map(obj -> autorRepository.findById(obj)).anyMatch(obj -> obj.isEmpty())) {
            return false;
        }

        return true;
    }

    private boolean validarIdioma(Long idioma) {
        if (idioma == null) {
            return false;
        }
        return idiomaRepository.findById(idioma).isPresent();
    }
}
