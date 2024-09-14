package ec.com.samagua.libreria03microserviciobuscador.act2_jpa_repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Idioma;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IdiomaJpaRepository extends JpaRepository<Idioma, Long>, JpaSpecificationExecutor<Idioma> {
}


