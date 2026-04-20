package com.restaurante01.api_restaurante.infraestrutura.inicializador;

import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Email;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.*;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.*;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.EnderecoPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.InformacoesClienteParaPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.RepresentacaoProdutoItemPedido;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import jakarta.persistence.EntityManager;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(EntityManager entityManager, PasswordEncoder passwordEncoder) {
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Verifica se já existem dados para não duplicar caso o sistema reinicie
        Long count = entityManager.createQuery("SELECT COUNT(o) FROM Operador o", Long.class).getSingleResult();
        if (count > 0) {
            return; // A "Mise en place" já foi feita.
        }

        String senhaPadrao = passwordEncoder.encode("123456");

        // --- 1. CRIANDO OPERADOR ---
        Operador admin = Operador.criar(
        "Bruno",
                senhaPadrao,
                new Email("joao@pessoa.com"),
                new Cpf("00000000000")
                );
        entityManager.persist(admin);

        // --- 2. CRIANDO CLIENTE ---
        Cliente cliente = Cliente.criar(
                "Maria Cliente",
                senhaPadrao,
                new Email("maria@email.com"),
                new Cpf("00000000000"),
                new EnderecoCliente("Tres pinheiros um", 27, "Mato grande", "Canoas", "RS", "88058208","Casa", "Perto do thomé"),
                "11999999999"
        );
        entityManager.persist(cliente);

        // --- 3. CRIANDO PRODUTOS ---
        Produto hamburguer = new Produto();
        hamburguer.setNome("X-Tudo Artesanal");
        hamburguer.setDescricao("Pão brioche, blend 180g, queijo prato, bacon e salada.");
        hamburguer.setPreco(new BigDecimal("35.00"));
        hamburguer.setQuantidadeAtual(50L);
        hamburguer.setDisponibilidade(true);
        entityManager.persist(hamburguer);

        Produto refrigerante = new Produto();
        refrigerante.setNome("Refrigerante Cola 350ml");
        refrigerante.setDescricao("Lata bem gelada.");
        refrigerante.setPreco(new BigDecimal("8.00"));
        refrigerante.setQuantidadeAtual(200L);
        refrigerante.setDisponibilidade(true);
        entityManager.persist(refrigerante);

        // --- 4. CRIANDO CARDÁPIO ---
        Cardapio cardapioInverno = new Cardapio();
        cardapioInverno.setNome("Cardápio Principal");
        cardapioInverno.setDescricao("Os clássicos da casa.");
        cardapioInverno.setDisponibilidade(true);
        cardapioInverno.setDataInicio(LocalDate.now().minusDays(1)); // Começou ontem
        cardapioInverno.setDataFim(LocalDate.now().plusMonths(6)); // Vai até daqui 6 meses
        entityManager.persist(cardapioInverno);

        // --- 5. VINCULANDO PRODUTO AO CARDÁPIO (CardapioProduto) ---
        Associacao associacao1 = new Associacao();
        associacao1.setCardapio(cardapioInverno);
        associacao1.setProduto(hamburguer);
        associacao1.setPrecoCustomizado(new BigDecimal("30.00")); // Preço promocional no cardápio
        associacao1.setQuantidadeCustomizada(10);
        associacao1.setDisponibilidadeCustomizada(true);
        entityManager.persist(associacao1);

        Associacao associacao2 = new Associacao();
        associacao2.setCardapio(cardapioInverno);
        associacao2.setProduto(refrigerante);
        associacao2.setPrecoCustomizado(new BigDecimal("6.00")); // Preço promocional no cardápio
        associacao2.setQuantidadeCustomizada(10);
        associacao2.setDisponibilidadeCustomizada(true);
        entityManager.persist(associacao2);

        // --- 6. CRIANDO PEDIDO ---
        Pedido pedido = Pedido.criar(1L, new InformacoesClienteParaPedido(cliente.getId(),cliente.getNome(), cliente.getCpf().cpf(), cliente.getTelefone()), new EnderecoPedido("Tres pinheiros um", 27, "Mato grande", "Canoas", "RS", "88058028", ""));
        ItemPedido item1 =  ItemPedido.criar(pedido, 2, new RepresentacaoProdutoItemPedido(hamburguer.getId(), hamburguer.getNome()),associacao1.resolverPrecoDeVenda(), "");
        ItemPedido item2 = ItemPedido.criar(pedido, 2, new RepresentacaoProdutoItemPedido(refrigerante.getId(), refrigerante.getNome()),associacao2.resolverPrecoDeVenda(),"");

        pedido.adicionarItem(item1);
        pedido.adicionarItem(item2);

        entityManager.persist(pedido);

        // Salva tudo no banco H2
        entityManager.flush();
    }
}