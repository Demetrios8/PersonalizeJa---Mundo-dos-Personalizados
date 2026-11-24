package com.personalizeja.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.personalizeja.enums.StatusAprovacao;
import com.personalizeja.exception.RecursoNaoEncontradoException;
import com.personalizeja.model.Arte;
import com.personalizeja.model.Produto;
import com.personalizeja.repository.ArteRepository;
import com.personalizeja.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ArteRepository arteRepository;

    public ProdutoService(ProdutoRepository produtoRepository, ArteRepository arteRepository) {
        this.produtoRepository = produtoRepository;
        this.arteRepository = arteRepository;
    }

    // ---------- Produto ----------

    @Transactional
    public Produto cadastrar(Produto produto) {
        String codigo = produto.getCodigoBarras();
        if (codigo != null && produtoRepository.existsByCodigoBarras(codigo)) {
            throw new IllegalArgumentException("Já existe produto com esse código de barras");
        }
        return produtoRepository.save(produto);
    }

    @Transactional(readOnly = true)
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + id));
    }

    @Transactional
    public Produto atualizar(Long id, Produto dados) {
        Produto produto = buscarPorId(id);
        
        produto.atualizarDados(
            dados.getNome(), 
            dados.getDescricao(), 
            dados.getPreco(), 
            dados.getCodigoBarras()
        );
        
        return produtoRepository.save(produto);
    }

    @Transactional
    public void ativar(Long id) {
        buscarPorId(id).ativar();
    }

    @Transactional
    public void desativar(Long id) {
        buscarPorId(id).desativar();
    }

    @Transactional
    public Arte enviarArte(Long produtoId, String arquivoUrl) {
        Produto produto = buscarPorId(produtoId);
        int proximaVersao = arteRepository.findFirstByProdutoIdOrderByVersaoDesc(produtoId)
                .map(a -> a.getVersao() + 1)
                .orElse(1);
        return arteRepository.save(new Arte(produto, proximaVersao, arquivoUrl));
    }

    @Transactional(readOnly = true)
    public List<Arte> listarArtes(Long produtoId) {
        buscarPorId(produtoId);
        return arteRepository.findByProdutoIdOrderByVersaoDesc(produtoId);
    }

    @Transactional
    public Arte aprovarArte(Long arteId) {
        Arte arte = buscarArte(arteId);
        validarAindaNaoDecidida(arte);
        arte.aprovar();
        return arte;
    }

    @Transactional
    public Arte reprovarArte(Long arteId, String comentario) {
        Arte arte = buscarArte(arteId);
        validarAindaNaoDecidida(arte);
        arte.reprovar(comentario);
        return arte;
    }

    @Transactional
    public Arte solicitarRevisao(Long arteId, String comentario) {
        Arte arte = buscarArte(arteId);
        validarAindaNaoDecidida(arte);
        arte.solicitarRevisao(comentario);
        return arte;
    }

    private Arte buscarArte(Long id) {
        return arteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Arte não encontrada: " + id));
    }

    private void validarAindaNaoDecidida(Arte arte) {
        if (arte.getStatus() == StatusAprovacao.APROVADA) {
            throw new IllegalStateException("Esta arte já foi aprovada");
        }
    }
}
