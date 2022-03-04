package br.com.revistainfoco.revista.repository;

import br.com.revistainfoco.revista.domain.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatoRepository  extends JpaRepository<Contato, Long> {
    Contato findByCelular(String celular);
}
