
package com.personalizeja.service;

import com.personalizeja.enums.FormaPagamento;
import com.personalizeja.model.Pagamento;
import com.personalizeja.model.Pedido;
import com.personalizeja.repository.PagamentoRepository;
import com.personalizeja.repository.PedidoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;

    public PagamentoService(
            PagamentoRepository pagamentoRepository,
            PedidoRepository pedidoRepository) {

        this.pagamentoRepository = pagamentoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    // Cadastrar pagamento
    @Transactional
    public Pagamento criar(
            BigDecimal valor,
            FormaPagamento formaPagamento,
            Long pedidoId) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Pedido não encontrado."));

        Pagamento pagamento = new Pagamento(
                valor,
                formaPagamento,
                pedido
        );

        return pagamentoRepository.save(pagamento);
    }

    // Listar todos os pagamentos
    @Transactional(readOnly = true)
    public List<Pagamento> listarTodos() {
        return pagamentoRepository.findAll();
    }

    // Buscar pagamento por ID
    @Transactional(readOnly = true)
    public Pagamento buscarPorId(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Pagamento não encontrado."));
    }

    // Atualizar pagamento
    @Transactional
    public Pagamento atualizar(
            Long id,
            BigDecimal valor,
            FormaPagamento formaPagamento) {

        Pagamento pagamento = buscarPorId(id);

        pagamento.atualizarDados(valor, formaPagamento);

        return pagamentoRepository.save(pagamento);
    }

    // Aprovar pagamento
    @Transactional
    public Pagamento aprovar(Long id) {

        Pagamento pagamento = buscarPorId(id);

        pagamento.aprovar();

        return pagamentoRepository.save(pagamento);
    }

    // Recusar pagamento
    @Transactional
    public Pagamento recusar(Long id) {

        Pagamento pagamento = buscarPorId(id);

        pagamento.recusar();

        return pagamentoRepository.save(pagamento);
    }

    // Cancelar pagamento
    @Transactional
    public Pagamento cancelar(Long id) {

        Pagamento pagamento = buscarPorId(id);

        pagamento.cancelar();

        return pagamentoRepository.save(pagamento);
    }

    // Estornar pagamento
    @Transactional
    public Pagamento estornar(Long id) {

        Pagamento pagamento = buscarPorId(id);

        pagamento.estornar();

        return pagamentoRepository.save(pagamento);
    }
}
