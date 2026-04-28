package com.restaurante01.api_restaurante.infraestrutura.inicializador;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.PeriodoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.RegraRecorrencia;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;
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
        hamburguer.setQuantidadeAtual(50);
        hamburguer.setDisponibilidade(true);
        entityManager.persist(hamburguer);

        Produto refrigerante = new Produto();
        refrigerante.setNome("Refrigerante Cola 350ml");
        refrigerante.setDescricao("Lata bem gelada.");
        refrigerante.setPreco(new BigDecimal("8.00"));
        refrigerante.setQuantidadeAtual(200);
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

        // --- 7. CRIANDO CUPOM ---
        PeriodoCupom periodoCupom = new PeriodoCupom("14/04/2026", "12:00", "14/06/2026", "15:00");
        BigDecimal valorParaDesconto = new BigDecimal(15); //valor em porcentagem
        BigDecimal valorMinPedido = new BigDecimal(60);
        BigDecimal valorMaxPedido = new BigDecimal(200);
        Cupom cupomValido = Cupom.criar(
                "VALIDO1",
                periodoCupom,
                true,
                3,
                TipoDesconto.PORCENTAGEM,
                RegraRecorrencia.QUINZE_DIAS,
                valorParaDesconto,
                valorMinPedido,
                valorMaxPedido);
        entityManager.persist(cupomValido);

        // Cupom de 2024 - Já expirou (estamos em 2026)
        PeriodoCupom periodoPassado = new PeriodoCupom("01/01/2024", "08:00", "01/03/2024", "18:00");
        Cupom cupomExpirado = Cupom.criar(
                "EXPIRADO1",
                periodoPassado,
                true,
                100,
                TipoDesconto.PORCENTAGEM,
                RegraRecorrencia.VINTE_DIAS,
                new BigDecimal(10),
                new BigDecimal(50),
                new BigDecimal(299)); //limite 300 para regra de porcentagem
        entityManager.persist(cupomExpirado);

        // Ativo = false
        Cupom cupomDesativado = Cupom.criar(
                "DESATIVADO1",
                periodoCupom, // Usando o período válido do seu exemplo
                false,        // <-- O erro proposital está aqui
                50,
                TipoDesconto.VALOR,
                RegraRecorrencia.TRINTA_DIAS,
                new BigDecimal(5),
                new BigDecimal(45),
                new BigDecimal(100));
        entityManager.persist(cupomDesativado);

        // Erro: Min (100) é maior que Max (50)
        Cupom cupomErroLogica = Cupom.criar(
                "VALORES1",
                periodoCupom,
                true,
                10,
                TipoDesconto.PORCENTAGEM,
                RegraRecorrencia.QUINZE_DIAS,
                new BigDecimal(10),
                new BigDecimal(30), // Min
                new BigDecimal(100));  // Max
        entityManager.persist(cupomErroLogica);

        // Salva tudo no banco H2
        entityManager.flush();
    }
}