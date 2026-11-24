package com.personalizeja.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.personalizeja.exception.RecursoNaoEncontradoException;
import com.personalizeja.model.Arte;
import com.personalizeja.model.Pedido;
import com.personalizeja.model.Produto;
import com.personalizeja.repository.ArteRepository;
import com.personalizeja.repository.PedidoRepository;
import com.personalizeja.repository.ProdutoRepository;

@Service
public class ArteService {

    private final ArteRepository arteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    public ArteService(ArteRepository arteRepository, 
                       ProdutoRepository produtoRepository, 
                       PedidoRepository pedidoRepository) {
        this.arteRepository = arteRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public Arte enviarArtePadrao(Long produtoId, String arquivoUrl) {
        Produto produto = buscarProduto(produtoId);
        
        int proximaVersao = calcularProximaVersao(produtoId);
        
        Arte arte = new Arte(produto, proximaVersao, arquivoUrl);
        return arteRepository.save(arte);
    }

    @Transactional
    public Arte enviarArtePersonalizada(Long produtoId, Long pedidoId, String arquivoUrl) {
        Produto produto = buscarProduto(produtoId);
        Pedido pedido = buscarPedido(pedidoId);
        
        int proximaVersao = calcularProximaVersao(produtoId);
        
        Arte arte = new Arte(produto, pedido, proximaVersao, arquivoUrl);
        return arteRepository.save(arte);
    }


    @Transactional(readOnly = true)
    public Arte buscarPorId(Long id) {
        return arteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Arte não encontrada: " + id));
    }

    @Transactional(readOnly = true)
    public List<Arte> listarPorProduto(Long produtoId) {
        buscarProduto(produtoId); 
        return arteRepository.findByProdutoIdOrderByVersaoDesc(produtoId);
    }

    
    @Transactional
    public Arte iniciarAnalise(Long arteId) {
        Arte arte = buscarPorId(arteId);
        arte.iniciarAnalise();
        return arte;
    }

    @Transactional
    public Arte aprovar(Long arteId) {
        Arte arte = buscarPorId(arteId);
        arte.aprovar();
        return arte;
    }

    @Transactional
    public Arte reprovar(Long arteId, String comentario) {
        Arte arte = buscarPorId(arteId);
        arte.reprovar(comentario);
        return arte;
    }

    @Transactional
    public Arte solicitarRevisao(Long arteId, String comentario) {
        Arte arte = buscarPorId(arteId);
        arte.solicitarRevisao(comentario);
        return arte;
    }


    private Produto buscarProduto(Long produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + produtoId));
    }

    private Pedido buscarPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado: " + pedidoId));
    }

    private int calcularProximaVersao(Long produtoId) {
        return arteRepository.findFirstByProdutoIdOrderByVersaoDesc(produtoId)
                .map(a -> a.getVersao() + 1)
                .orElse(1);
    }
}