package br.com.mastersys.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "alunos")
@Getter
@Setter
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    @Column(length = 1)
    private String sexo;
    private String telefone;
    private String celular;
    private String email;
    private String observacao;

    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    @Column(length = 2)
    private String estado;
    private String cep;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    // Define automaticamente a data de criação antes de salvar no banco.
    @PrePersist
    public void prePersist() {
        criadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        atualizadoEm = LocalDateTime.now();
    }

}