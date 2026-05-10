package br.com.mastersys.dto;

import br.com.mastersys.domain.Aluno;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AlunoRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 150, message = "O nome não pode exceder 150 caracteres")
        String nome,

        @Past(message = "A data de nascimento deve ser uma data no passado")
        LocalDate dataNascimento,

        @Pattern(regexp = "[MF]", message = "O sexo deve ser 'M' ou 'F'")
        String sexo,

        @Size(max = 20, message = "O telefone não pode exceder 20 caracteres")
        String telefone,

        @NotBlank(message = "O celular é obrigatório")
        @Size(max = 20, message = "O celular não pode exceder 20 caracteres")
        String celular,

        @Email(message = "E-mail inválido")
        @Size(max = 150, message = "O e-mail não pode exceder 150 caracteres")
        String email,

        String observacao,

        @Size(max = 200, message = "O endereço não pode exceder 200 caracteres")
        String endereco,

        @Size(max = 20, message = "O número não pode exceder 20 caracteres")
        String numero,

        @Size(max = 100, message = "O complemento não pode exceder 100 caracteres")
        String complemento,

        @Size(max = 100, message = "O bairro não pode exceder 100 caracteres")
        String bairro,

        @Size(max = 100, message = "A cidade não pode exceder 100 caracteres")
        String cidade,

        @Size(min = 2, max = 2, message = "O estado deve ter 2 caracteres")
        String estado,

        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido. Use o formato 00000-000 ou apenas números")
        String cep
) {

    public Aluno toEntity() {
        Aluno aluno = new Aluno();
        preencher(aluno);
        return aluno;
    }

    public void preencher(Aluno aluno) {
        aluno.setNome(this.nome);
        aluno.setDataNascimento(dataNascimento);
        aluno.setSexo(sexo);
        aluno.setTelefone(telefone);
        aluno.setCelular(celular);
        aluno.setEmail(email);
        aluno.setObservacao(observacao);
        aluno.setEndereco(endereco);
        aluno.setNumero(numero);
        aluno.setComplemento(complemento);
        aluno.setBairro(bairro);
        aluno.setCidade(cidade);
        aluno.setEstado(estado);
        aluno.setCep(cep);
    }

}
