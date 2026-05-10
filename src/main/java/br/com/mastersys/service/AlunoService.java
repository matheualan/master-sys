package br.com.mastersys.service;

import br.com.mastersys.domain.Aluno;
import br.com.mastersys.dto.AlunoFiltroRequest;
import br.com.mastersys.dto.AlunoRequest;
import br.com.mastersys.dto.AlunoResponse;
import br.com.mastersys.exception.RegraNegocioException;
import br.com.mastersys.repository.AlunoRepository;
import br.com.mastersys.specification.AlunoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public AlunoResponse cadastrar(AlunoRequest request) {
        if (request.email() != null && alunoRepository.existsByEmail(request.email()))  {
            throw new RegraNegocioException("Email já cadastrado.");
        }

        Aluno aluno = request.toEntity();
        Aluno alunoSalvo = alunoRepository.save(aluno);
        return AlunoResponse.fromEntity(alunoSalvo);
    }

    public Page<AlunoResponse> listar(AlunoFiltroRequest filtro, Pageable pageable) {
        return alunoRepository.findAll(AlunoSpecification.comFiltros(filtro),
                pageable).map(AlunoResponse::fromEntity);
    }

    public Slice<AlunoResponse> listarSlice(Pageable pageable) {
        return alunoRepository.findAllBy(pageable).map(AlunoResponse::fromEntity);
    }

    public AlunoResponse buscarPorId(Long id) {
        Aluno aluno = buscarEntidadePorId(id);
        return AlunoResponse.fromEntity(aluno);
    }

    public AlunoResponse atualizarPorId(Long id, AlunoRequest request) {
        Aluno aluno = buscarEntidadePorId(id);

        if (request.email() != null && alunoRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new RegraNegocioException("Email já cadastrado para outro aluno.");
        }

        request.preencher(aluno);
        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return AlunoResponse.fromEntity(alunoAtualizado);
    }

    public void excluir(Long id) {
        Aluno aluno = buscarEntidadePorId(id);
        alunoRepository.delete(aluno);
    }

    private Aluno buscarEntidadePorId(Long id) {
        return alunoRepository.findById(id).orElseThrow(() -> new RegraNegocioException("Aluno não encontrado."));
    }

}