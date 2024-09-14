package ec.com.samagua.libreria03microserviciobuscador.act2_jpa_repositorios;

import ec.com.samagua.libreria03microserviciobuscador.act1_entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoriaJpaRepository extends JpaRepository<Categoria, Long>, JpaSpecificationExecutor<Categoria> {
}


