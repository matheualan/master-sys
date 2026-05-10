package br.com.mastersys.specification;

import br.com.mastersys.domain.Aluno;
import br.com.mastersys.dto.AlunoFiltroRequest;
import org.springframework.data.jpa.domain.Specification;

public class AlunoSpecification {

    public static Specification<Aluno> comFiltros(AlunoFiltroRequest filtro) {
        return Specification.allOf(nomeContem(filtro.nome()))
                .and(emailContem(filtro.email()))
                .and(celularContem(filtro.celular()))
                .and(cidadeContem(filtro.cidade()))
                .and(estadoContem(filtro.estado()));
    }

    private static Specification<Aluno> nomeContem(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()) {
                return null;
            }

            return cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    private static Specification<Aluno> emailContem(String email) {
        return (root, query, cb) -> {
            if (email == null || email.isBlank()) {
                return null;
            }

            return  cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }

    private static Specification<Aluno> celularContem(String celular) {
        return (root, query, cb) -> {
            if (celular == null || celular.isBlank()) {
                return null;
            }

            return cb.like(root.get("celular"), "%" + celular + "%");
        };
    }

    private static Specification<Aluno> cidadeContem(String cidade) {
        return (root, query, cb) -> {
            if (cidade == null || cidade.isBlank()) {
                return null;
            }

            return cb.like(cb.lower(root.get("cidade")), "%" + cidade.toLowerCase() + "%");
        };
    }

    private static Specification<Aluno> estadoContem(String estado) {
        return (root, query, cb) -> {
            if (estado == null || estado.isBlank()) {
                return null;
            }

            return cb.equal(cb.upper(root.get("estado")), estado.toUpperCase());
        };
    }

}