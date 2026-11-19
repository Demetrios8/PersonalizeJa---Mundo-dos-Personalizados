package com.personalizeja.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.personalizeja.dto.ClienteRequestDTO;
import com.personalizeja.dto.ClienteResponseDTO;
import com.personalizeja.exception.RecursoNaoEncontradoException;
import com.personalizeja.exception.RegraNegocioException;
import com.personalizeja.mapper.ClienteMapper;
import com.personalizeja.model.Cliente;
import com.personalizeja.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public ClienteService(ClienteRepository repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        ClienteRequestDTO dados = normalizar(dto);

        if (dados.email() != null && repository.existsByEmail(dados.email())) {
            throw new RegraNegocioException("Já existe um cliente com este e-mail.");
        }
        if (dados.documento() != null && repository.existsByDocumento(dados.documento())) {
            throw new RegraNegocioException("Já existe um cliente com este documento.");
        }

        Cliente cliente = repository.save(mapper.toEntity(dados));
        return mapper.toResponse(cliente);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listar(String nome) {
        List<Cliente> clientes = (nome == null || nome.isBlank())
                ? repository.findAll()
                : repository.findByNomeContainingIgnoreCase(nome.trim());

        return clientes.stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = buscarEntidade(id);
        ClienteRequestDTO dados = normalizar(dto);

        if (dados.email() != null && repository.existsByEmailAndIdNot(dados.email(), id)) {
            throw new RegraNegocioException("Já existe um cliente com este e-mail.");
        }
        if (dados.documento() != null && repository.existsByDocumentoAndIdNot(dados.documento(), id)) {
            throw new RegraNegocioException("Já existe um cliente com este documento.");
        }

        cliente.atualizarDados(dados.nome(), dados.telefone(), dados.email(), dados.documento());
        return mapper.toResponse(cliente);
    }

    // Metodo auxiliar usado por outros services (ex.: PedidoService)
    @Transactional(readOnly = true)
    public Cliente buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Cliente não encontrado: " + id));
    }

    // Campos opcionais em branco viram null (evita conflito na restrição unique)
    private ClienteRequestDTO normalizar(ClienteRequestDTO dto) {
        return new ClienteRequestDTO(
                dto.nome().trim(),
                dto.telefone().trim(),
                vazioParaNulo(dto.email()),
                vazioParaNulo(dto.documento())
        );
    }

    private String vazioParaNulo(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor.trim();
    }
}