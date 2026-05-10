package br.com.mastersys.service;

import br.com.mastersys.domain.Aluno;
import br.com.mastersys.dto.AlunoRequest;
import br.com.mastersys.dto.AlunoResponse;
import br.com.mastersys.exception.RegraNegocioException;
import br.com.mastersys.repository.AlunoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    private Aluno aluno;
    private AlunoRequest request;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("João Silva");
        aluno.setEmail("joao@email.com");
        aluno.setCelular("11988888888");

        request = new AlunoRequest(
                "João Silva Atualizado",
                LocalDate.of(1990, 1, 1),
                "M",
                "11999999999",
                "11988888888",
                "joao.atualizado@email.com",
                "Observação",
                "Rua A",
                "123",
                "Apto 1",
                "Bairro X",
                "Cidade Y",
                "SP",
                "01234567"
        );
    }

    @Nested
    @DisplayName("Testes de Cadastro")
    class CadastroTests {
        @Test
        @DisplayName("Deve cadastrar aluno com sucesso")
        void deveCadastrarAlunoComSucesso() {
            when(alunoRepository.existsByEmail(request.email())).thenReturn(false);
            when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> {
                Aluno a = invocation.getArgument(0);
                a.setId(1L);
                return a;
            });

            AlunoResponse response = alunoService.cadastrar(request);

            assertNotNull(response);
            assertEquals(request.nome(), response.nome());
            verify(alunoRepository).save(any(Aluno.class));
        }

        @Test
        @DisplayName("Deve lançar exceção ao cadastrar com email já existente")
        void deveLancarExcecaoAoCadastrarComEmailExistente() {
            when(alunoRepository.existsByEmail(request.email())).thenReturn(true);

            RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> alunoService.cadastrar(request));
            assertEquals("Email já cadastrado.", exception.getMessage());
            verify(alunoRepository, never()).save(any(Aluno.class));
        }
    }

    @Nested
    @DisplayName("Testes de Listagem")
    class ListagemTests {
//        @Test
//        @DisplayName("Deve listar alunos com paginação (Page)")
//        void deveListarAlunosPaginados() {
//            Page<Aluno> page = new PageImpl<>(List.of(aluno));
//            when(alunoRepository.findAll(any(Pageable.class))).thenReturn(page);
//
//            Page<AlunoResponse> response = alunoService.listar(Pageable.unpaged());
//
//            assertNotNull(response);
//            assertEquals(1, response.getContent().size());
//            assertEquals(aluno.getNome(), response.getContent().get(0).nome());
//        }

        @Test
        @DisplayName("Deve listar alunos com slice")
        void deveListarAlunosSlice() {
            Slice<Aluno> slice = new SliceImpl<>(List.of(aluno));
            when(alunoRepository.findAllBy(any(Pageable.class))).thenReturn(slice);

            Slice<AlunoResponse> response = alunoService.listarSlice(Pageable.unpaged());

            assertNotNull(response);
            assertEquals(1, response.getContent().size());
            assertEquals(aluno.getNome(), response.getContent().get(0).nome());
        }
    }

    @Nested
    @DisplayName("Testes de Busca por ID")
    class BuscaTests {
        @Test
        @DisplayName("Deve buscar aluno por ID com sucesso")
        void deveBuscarPorIdComSucesso() {
            when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

            AlunoResponse response = alunoService.buscarPorId(1L);

            assertNotNull(response);
            assertEquals(aluno.getId(), response.id());
        }

        @Test
        @DisplayName("Deve lançar exceção ao buscar ID inexistente")
        void deveLancarExcecaoAoBuscarIdInexistente() {
            when(alunoRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(RegraNegocioException.class, () -> alunoService.buscarPorId(1L));
        }
    }

    @Nested
    @DisplayName("Testes de Atualização")
    class AtualizacaoTests {
        @Test
        @DisplayName("Deve atualizar aluno com sucesso")
        void deveAtualizarAlunoComSucesso() {
            when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
            when(alunoRepository.existsByEmailAndIdNot(request.email(), 1L)).thenReturn(false);
            when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);

            AlunoResponse response = alunoService.atualizarPorId(1L, request);

            assertNotNull(response);
            assertEquals(request.nome(), response.nome());
            verify(alunoRepository).save(any(Aluno.class));
        }

        @Test
        @DisplayName("Deve lançar exceção ao atualizar com email de outro aluno")
        void deveLancarExcecaoAoAtualizarEmailExistente() {
            when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
            when(alunoRepository.existsByEmailAndIdNot(request.email(), 1L)).thenReturn(true);

            assertThrows(RegraNegocioException.class, () -> alunoService.atualizarPorId(1L, request));
        }

        @Test
        @DisplayName("Deve atualizar com sucesso quando novo email for nulo")
        void deveAtualizarQuandoEmailForNulo() {
            AlunoRequest requestSemEmail = new AlunoRequest("João", LocalDate.now().minusYears(20), "M", null, "119", null, null, null, null, null, null, null, "SP", null);
            when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
            when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);

            AlunoResponse response = alunoService.atualizarPorId(1L, requestSemEmail);

            assertNotNull(response);
            verify(alunoRepository, never()).existsByEmailAndIdNot(any(), anyLong());
        }
    }

    @Nested
    @DisplayName("Testes de Exclusão")
    class ExclusaoTests {
        @Test
        @DisplayName("Deve excluir aluno com sucesso")
        void deveExcluirComSucesso() {
            when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

            alunoService.excluir(1L);

            verify(alunoRepository).delete(aluno);
        }

        @Test
        @DisplayName("Deve lançar exceção ao excluir aluno inexistente")
        void deveLancarExcecaoAoExcluirInexistente() {
            when(alunoRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(RegraNegocioException.class, () -> alunoService.excluir(1L));
            verify(alunoRepository, never()).delete(any(Aluno.class));
        }
    }
}
