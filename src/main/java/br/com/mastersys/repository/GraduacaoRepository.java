package br.com.mastersys.repository;

import br.com.mastersys.domain.Graduacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraduacaoRepository extends JpaRepository<Graduacao, Long> {
}
