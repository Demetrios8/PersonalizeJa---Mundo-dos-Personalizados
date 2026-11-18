
package com.personalizeja.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String documento;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    protected Cliente() {
    }

    public Cliente(String nome, String telefone, String email,
                   String documento) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.documento = documento;
        this.dataCadastro = LocalDateTime.now();
    }

    public void atualizarDados(String nome, String telefone,
                               String email, String documento) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.documento = documento;
    }
}
