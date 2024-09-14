package ec.com.samagua.libreria03microserviciobuscador.act5_controladores;

import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Libro;
import ec.com.samagua.libreria03microserviciobuscador.act4_servicios.LibroService;
import ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos.CriteriosBusqueda;
import ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos.LibroDto;
import ec.com.samagua.libreria03microserviciobuscador.act5_controladores_dtos.Resultado;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LibrosController {

    private final LibroService service;

    @GetMapping("/libros/{id}")
    public ResponseEntity<Libro> getById(@PathVariable Long id) {
        Resultado<Libro> resultado = service.search(id);
        if(!resultado.getErrores().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado.getValor());
    }

    @DeleteMapping ("/libros/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        Resultado<Boolean> resultado = service.delete(id);
        return ResponseEntity.ok(resultado.getValor());
    }

    @PostMapping ("/libros")
    public ResponseEntity<Libro> create(@RequestBody LibroDto dto) {
        Resultado<Libro> resultado = service.create(dto);
        if(!resultado.getErrores().isEmpty()) {
            resultado.getErrores().forEach(error -> log.info(error));
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(resultado.getValor());
    }

    @PutMapping ("/libros/{id}")
    public ResponseEntity<Libro> update(@PathVariable Long id, @RequestBody LibroDto dto) {
        Resultado<Libro> entity = service.search(id);
        if (!entity.getErrores().isEmpty()) {
            entity.getErrores().forEach(error -> log.info(error));
            return ResponseEntity.badRequest().build();
        }

        Resultado<Libro> resultado = service.update(id, dto);
        if(!resultado.getErrores().isEmpty()) {
            resultado.getErrores().forEach(error -> log.info(error));
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(resultado.getValor());
    }

    @PatchMapping ("/libros/{id}")
    public ResponseEntity<Libro> updatePatch(@PathVariable Long id, @RequestBody String patch) {
        Resultado<Libro> entity = service.search(id);
        if (!entity.getErrores().isEmpty()) {
            entity.getErrores().forEach(error -> log.info(error));
            return ResponseEntity.badRequest().build();
        }

        Resultado<Libro> resultado = service.updatePatch(id, patch);
        if(!resultado.getErrores().isEmpty()) {
            resultado.getErrores().forEach(error -> log.info(error));
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(resultado.getValor());
    }

    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> search(@QueryParam("titulo") String titulo, @QueryParam("anioPublicacion") Integer anioPublicacion, @QueryParam("isbn10") String isbn10, @QueryParam("isbn13") String isbn13, @QueryParam("idioma") Long idioma, @QueryParam("disponibilidad") Boolean disponibilidad, @QueryParam("autor") Long autor, @QueryParam("categoria") Long categoria) {
        Resultado<List<Libro>> resultado = service.search(CriteriosBusqueda.builder()
                .titulo(titulo)
                .anioPublicacion(anioPublicacion)
                .isbn10(isbn10)
                .isbn13(isbn13)
                .idioma(idioma)
                .disponibilidad(disponibilidad)
                .autor(autor)
                .categoria(categoria)
                .build());
        return ResponseEntity.ok(resultado.getValor());
    }
}