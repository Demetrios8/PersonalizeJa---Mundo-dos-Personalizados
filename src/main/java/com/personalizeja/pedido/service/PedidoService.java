
package com.personalizeja.pedido.service;

import com.personalizeja.pedido.dto.PedidoRequestDTO;
import com.personalizeja.pedido.dto.ItemPedidoRequestDTO;
import com.personalizeja.pedido.dto.PedidoResponseDTO;

import com.personalizeja.pedido.mapper.PedidoMapper;

import com.personalizeja.cliente.entity.Cliente;
import com.personalizeja.pedido.entity.ItemPedido;
import com.personalizeja.pedido.entity.Pedido;
import com.personalizeja.produto.entity.Produto;

import com.personalizeja.pedido.repository.PedidoRepository;
import com.personalizeja.cliente.repository.ClienteRepository;
import com.personalizeja.produto.repository.ProdutoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository,
            PedidoMapper pedidoMapper) {

        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    // Cadastrar pedido

    @Transactional
    public PedidoResponseDTO cadastrar(PedidoRequestDTO dto) {

        if (dto.itens() == null || dto.itens().isEmpty()) {
            throw new IllegalArgumentException(
                    "O pedido precisa possuir pelo menos um item."
            );
        }

        Cliente cliente = clienteRepository
                .findById(dto.clienteId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cliente não encontrado."
                ));

        Pedido pedido = new Pedido(
                cliente,
                dto.canalOrigem()
        );

        for (ItemPedidoRequestDTO itemDTO : dto.itens()) {

            Produto produto = produtoRepository
                    .findById(itemDTO.produtoId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Produto não encontrado: "
                                    + itemDTO.produtoId()
                    ));

            if (!produto.isAtivo()) {
                throw new IllegalArgumentException(
                        "O produto está inativo."
                );
            }

            ItemPedido item = new ItemPedido(
                    produto,
                    itemDTO.quantidade(),
                    produto.getPreco(),
                    itemDTO.descricaoPersonalizacao()
            );

            pedido.adicionarItem(item);
        }

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return pedidoMapper.toResponseDTO(pedidoSalvo);
    }

    // Listar todos os pedidos

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarTodos() {

        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toResponseDTO)
                .toList();
    }

    // Buscar pedido por ID

    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPorId(Long id) {

        Pedido pedido = buscarEntidade(id);

        return pedidoMapper.toResponseDTO(pedido);
    }

    // Iniciar produção

    @Transactional
    public PedidoResponseDTO iniciarProducao(Long id) {

        Pedido pedido = buscarEntidade(id);

        pedido.iniciarProducao();

        return pedidoMapper.toResponseDTO(
                pedidoRepository.save(pedido)
        );
    }

    // Concluir pedido

    @Transactional
    public PedidoResponseDTO concluir(Long id) {

        Pedido pedido = buscarEntidade(id);

        pedido.concluir();

        return pedidoMapper.toResponseDTO(
                pedidoRepository.save(pedido)
        );
    }

    // Entregar pedido

    @Transactional
    public PedidoResponseDTO entregar(Long id) {

        Pedido pedido = buscarEntidade(id);

        pedido.entregar();

        return pedidoMapper.toResponseDTO(
                pedidoRepository.save(pedido)
        );
    }

    // Cancelar pedido

    @Transactional
    public PedidoResponseDTO cancelar(Long id) {

        Pedido pedido = buscarEntidade(id);

        pedido.cancelar();

        return pedidoMapper.toResponseDTO(
                pedidoRepository.save(pedido)
        );
    }

    // Método auxiliar

    private Pedido buscarEntidade(Long id) {

        return pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Pedido não encontrado."
                ));
    }
}
