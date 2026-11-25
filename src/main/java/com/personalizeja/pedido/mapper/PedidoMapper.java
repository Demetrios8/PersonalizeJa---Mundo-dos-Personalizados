
package com.personalizeja.pedido.mapper;

import com.personalizeja.pedido.dto.ItemPedidoResponseDTO;
import com.personalizeja.pedido.dto.PedidoResponseDTO;
import com.personalizeja.pedido.entity.ItemPedido;
import com.personalizeja.pedido.entity.Pedido;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public PedidoResponseDTO toResponseDTO(Pedido pedido) {

        List<ItemPedidoResponseDTO> itens = pedido.getItens()
                .stream()
                .map(this::toItemResponseDTO)
                .toList();

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getStatus(),
                pedido.getCanalOrigem(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNome(),
                itens,
                pedido.calcularValorTotal()
        );
    }

    private ItemPedidoResponseDTO toItemResponseDTO(
            ItemPedido item) {

        return new ItemPedidoResponseDTO(
                item.getId(),
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getDescricaoPersonalizacao(),
                item.calcularSubtotal()
        );
    }
}
