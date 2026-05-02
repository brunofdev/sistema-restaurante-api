package com.restaurante01.api_restaurante.infraestrutura.inicializador;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.PeriodoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.RegraRecorrencia;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Email;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.EnderecoCliente;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
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
import java.util.List;

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
    public void run(String... args) {
        Long count = entityManager.createQuery("SELECT COUNT(o) FROM Operador o", Long.class).getSingleResult();
        if (count > 0) return;

        String senha = passwordEncoder.encode("123456");

        // -------------------------------------------------------------------------
        // OPERADOR
        // -------------------------------------------------------------------------
        Operador admin = Operador.criar("Admin", senha, new Email("admin@restaurante.com"), new Cpf("00000000191"));
        entityManager.persist(admin);

        // -------------------------------------------------------------------------
        // CLIENTES
        // -------------------------------------------------------------------------
        Cliente maria = Cliente.criar(
                "Maria Silva", senha,
                new Email("maria@email.com"), new Cpf("00000000272"),
                new EnderecoCliente("Rua das Flores", 100, "Centro", "Porto Alegre", "RS", "90010010", "Apto 12", ""),
                "51999990001");
        entityManager.persist(maria);

        Cliente joao = Cliente.criar(
                "João Pereira", senha,
                new Email("joao@email.com"), new Cpf("00000000353"),
                new EnderecoCliente("Av. Ipiranga", 500, "Partenon", "Porto Alegre", "RS", "90160093", "Casa", ""),
                "51999990002");
        entityManager.persist(joao);

        // -------------------------------------------------------------------------
        // PRODUTOS (10)
        // -------------------------------------------------------------------------
        Produto hamburguer   = prod("X-Tudo Artesanal",            "Pão brioche, blend 180g, queijo prato, bacon e salada",    "35.00", 150);
        Produto refrigerante = prod("Refrigerante Cola 350ml",     "Lata bem gelada",                                          "8.00",  300);
        Produto batataFrita  = prod("Batata Frita Crocante",        "Porção 250g com molho cheddar",                            "18.00", 100);
        Produto milkShake    = prod("Milk Shake Chocolate 400ml",  "Sorvete de chocolate com leite gelado",                    "22.00",  80);
        Produto frango       = prod("Frango Grelhado com Arroz",   "Filé temperado, arroz e salada",                           "32.00", 120);
        Produto salada       = prod("Salada Caesar",                "Alface, croutons, parmesão e molho caesar",                "25.00",  60);
        Produto suco         = prod("Suco Natural de Laranja 500ml","Laranja espremida na hora",                               "12.00", 200);
        Produto brownie      = prod("Brownie com Sorvete",          "Brownie quente, sorvete de creme e calda de chocolate",   "19.00",  50);
        Produto pizza        = prod("Pizza Margherita (fatia)",     "Molho de tomate, mussarela e manjericão",                 "15.00",  80);
        Produto wrap         = prod("Wrap de Frango Crispy",        "Tortilla, frango empanado e molho chipotle",              "28.00",  90);

        List.of(hamburguer, refrigerante, batataFrita, milkShake, frango, salada, suco, brownie, pizza, wrap)
                .forEach(entityManager::persist);

        // -------------------------------------------------------------------------
        // CARDÁPIOS (2)
        // -------------------------------------------------------------------------
        Cardapio principal = card("Cardápio Principal",       "Os clássicos da casa",
                LocalDate.now().minusDays(30), LocalDate.now().plusMonths(6));
        Cardapio fds       = card("Cardápio Fim de Semana",   "Especiais exclusivos de sábado e domingo",
                LocalDate.now().minusDays(7),  LocalDate.now().plusMonths(3));
        entityManager.persist(principal);
        entityManager.persist(fds);

        entityManager.flush(); // garante IDs gerados antes de criar associações e pedidos

        // -------------------------------------------------------------------------
        // ASSOCIAÇÕES — 10 produtos × 2 cardápios
        // -------------------------------------------------------------------------

        // Cardápio Principal — preços promocionais
        Associacao aHamburguerP   = assoc(principal, hamburguer,   "30.00", 50);
        Associacao aRefrigeranteP = assoc(principal, refrigerante, "6.50",  100);
        Associacao aBatataP       = assoc(principal, batataFrita,  "15.00", 50);
        Associacao aMilkShakeP    = assoc(principal, milkShake,    "19.00", 40);
        Associacao aFrangoP       = assoc(principal, frango,       "28.00", 60);
        Associacao aSaladaP       = assoc(principal, salada,       "22.00", 30);
        Associacao aSucoP         = assoc(principal, suco,         "10.00", 80);
        Associacao aBrownieP      = assoc(principal, brownie,      "17.00", 25);
        Associacao aPizzaP        = assoc(principal, pizza,        "13.00", 40);
        Associacao aWrapP         = assoc(principal, wrap,         "25.00", 45);

        // Cardápio Fim de Semana — preços do cardápio especial
        Associacao aHamburguerF   = assoc(fds, hamburguer,   "38.00", 40);
        Associacao aRefrigeranteF = assoc(fds, refrigerante, "9.00",  80);
        Associacao aBatataF       = assoc(fds, batataFrita,  "20.00", 40);
        Associacao aMilkShakeF    = assoc(fds, milkShake,    "24.00", 30);
        Associacao aFrangoF       = assoc(fds, frango,       "35.00", 50);
        Associacao aSaladaF       = assoc(fds, salada,       "27.00", 25);
        Associacao aSucoF         = assoc(fds, suco,         "13.00", 60);
        Associacao aBrownieF      = assoc(fds, brownie,      "21.00", 20);
        Associacao aPizzaF        = assoc(fds, pizza,        "17.00", 35);
        Associacao aWrapF         = assoc(fds, wrap,         "30.00", 35);

        List.of(aHamburguerP, aRefrigeranteP, aBatataP, aMilkShakeP, aFrangoP,
                aSaladaP, aSucoP, aBrownieP, aPizzaP, aWrapP,
                aHamburguerF, aRefrigeranteF, aBatataF, aMilkShakeF, aFrangoF,
                aSaladaF, aSucoF, aBrownieF, aPizzaF, aWrapF)
                .forEach(entityManager::persist);

        entityManager.flush();

        // -------------------------------------------------------------------------
        // DADOS DE APOIO PARA OS PEDIDOS
        // -------------------------------------------------------------------------
        EnderecoPedido endMaria = new EnderecoPedido("Rua das Flores",  100, "Centro",   "Porto Alegre", "RS", "90010010", "Apto 12");
        EnderecoPedido endJoao  = new EnderecoPedido("Av. Ipiranga",    500, "Partenon", "Porto Alegre", "RS", "90160093", "");

        InformacoesClienteParaPedido infoMaria = new InformacoesClienteParaPedido(
                maria.getId(), maria.getNome(), maria.getCpf().cpf(), maria.getTelefone());
        InformacoesClienteParaPedido infoJoao  = new InformacoesClienteParaPedido(
                joao.getId(), joao.getNome(), joao.getCpf().cpf(), joao.getTelefone());

        // -------------------------------------------------------------------------
        // 20 PEDIDOS  (6 ENTREGUE | 3 SAIU_PARA_ENTREGA | 4 EM_PREPARACAO | 5 PENDENTE | 2 CANCELADO)
        // -------------------------------------------------------------------------

        // --- ENTREGUES ---

        Pedido p1 = Pedido.criar(principal.getId(), infoMaria, endMaria);
        p1.adicionarItem(item(p1, 1, hamburguer,   aHamburguerP,   "Sem cebola"));
        p1.adicionarItem(item(p1, 1, refrigerante, aRefrigeranteP, ""));
        entityManager.persist(p1);
        avancarAte(p1, StatusPedido.ENTREGUE);

        Pedido p2 = Pedido.criar(principal.getId(), infoMaria, endMaria);
        p2.adicionarItem(item(p2, 2, frango, aFrangoP, ""));
        p2.adicionarItem(item(p2, 1, suco,   aSucoP,   "Sem açúcar"));
        entityManager.persist(p2);
        avancarAte(p2, StatusPedido.ENTREGUE);

        Pedido p3 = Pedido.criar(principal.getId(), infoJoao, endJoao);
        p3.adicionarItem(item(p3, 1, pizza,        aPizzaP,        ""));
        p3.adicionarItem(item(p3, 1, batataFrita,  aBatataP,       "Bem crocante"));
        p3.adicionarItem(item(p3, 2, refrigerante, aRefrigeranteP, ""));
        entityManager.persist(p3);
        avancarAte(p3, StatusPedido.ENTREGUE);

        Pedido p4 = Pedido.criar(fds.getId(), infoJoao, endJoao);
        p4.adicionarItem(item(p4, 1, hamburguer, aHamburguerF, "Ponto médio"));
        p4.adicionarItem(item(p4, 1, milkShake,  aMilkShakeF,  ""));
        entityManager.persist(p4);
        avancarAte(p4, StatusPedido.ENTREGUE);

        Pedido p5 = Pedido.criar(fds.getId(), infoMaria, endMaria);
        p5.adicionarItem(item(p5, 1, wrap,   aWrapF,   ""));
        p5.adicionarItem(item(p5, 1, suco,   aSucoF,   ""));
        p5.adicionarItem(item(p5, 1, brownie, aBrownieF, ""));
        entityManager.persist(p5);
        avancarAte(p5, StatusPedido.ENTREGUE);

        Pedido p6 = Pedido.criar(principal.getId(), infoJoao, endJoao);
        p6.adicionarItem(item(p6, 1, salada, aSaladaP, ""));
        p6.adicionarItem(item(p6, 1, suco,   aSucoP,   ""));
        entityManager.persist(p6);
        avancarAte(p6, StatusPedido.ENTREGUE);

        // --- SAIU PARA ENTREGA ---

        Pedido p7 = Pedido.criar(principal.getId(), infoMaria, endMaria);
        p7.adicionarItem(item(p7, 2, hamburguer,   aHamburguerP,   ""));
        p7.adicionarItem(item(p7, 2, refrigerante, aRefrigeranteP, ""));
        entityManager.persist(p7);
        avancarAte(p7, StatusPedido.SAIU_PARA_ENTREGA);

        Pedido p8 = Pedido.criar(fds.getId(), infoJoao, endJoao);
        p8.adicionarItem(item(p8, 1, pizza,        aPizzaF,        "Extra queijo"));
        p8.adicionarItem(item(p8, 1, refrigerante, aRefrigeranteF, ""));
        entityManager.persist(p8);
        avancarAte(p8, StatusPedido.SAIU_PARA_ENTREGA);

        Pedido p9 = Pedido.criar(fds.getId(), infoMaria, endMaria);
        p9.adicionarItem(item(p9, 1, frango, aFrangoF, ""));
        p9.adicionarItem(item(p9, 1, salada, aSaladaF, ""));
        entityManager.persist(p9);
        avancarAte(p9, StatusPedido.SAIU_PARA_ENTREGA);

        // --- EM PREPARAÇÃO ---

        Pedido p10 = Pedido.criar(principal.getId(), infoJoao, endJoao);
        p10.adicionarItem(item(p10, 1, wrap,      aWrapP,      "Sem pimenta"));
        p10.adicionarItem(item(p10, 1, milkShake, aMilkShakeP, ""));
        entityManager.persist(p10);
        avancarAte(p10, StatusPedido.EM_PREPARACAO);

        Pedido p11 = Pedido.criar(principal.getId(), infoMaria, endMaria);
        p11.adicionarItem(item(p11, 1, brownie, aBrownieP, ""));
        p11.adicionarItem(item(p11, 2, suco,    aSucoP,    ""));
        entityManager.persist(p11);
        avancarAte(p11, StatusPedido.EM_PREPARACAO);

        Pedido p12 = Pedido.criar(fds.getId(), infoJoao, endJoao);
        p12.adicionarItem(item(p12, 1, hamburguer,   aHamburguerF,   "Bem passado"));
        p12.adicionarItem(item(p12, 1, batataFrita,  aBatataF,       ""));
        p12.adicionarItem(item(p12, 1, refrigerante, aRefrigeranteF, ""));
        entityManager.persist(p12);
        avancarAte(p12, StatusPedido.EM_PREPARACAO);

        Pedido p13 = Pedido.criar(fds.getId(), infoMaria, endMaria);
        p13.adicionarItem(item(p13, 1, pizza, aPizzaF, "Bordas recheadas"));
        p13.adicionarItem(item(p13, 1, suco,  aSucoF,  ""));
        entityManager.persist(p13);
        avancarAte(p13, StatusPedido.EM_PREPARACAO);

        // --- PENDENTES ---

        Pedido p14 = Pedido.criar(principal.getId(), infoJoao, endJoao);
        p14.adicionarItem(item(p14, 3, pizza,        aPizzaP,        ""));
        p14.adicionarItem(item(p14, 3, refrigerante, aRefrigeranteP, ""));
        entityManager.persist(p14);

        Pedido p15 = Pedido.criar(principal.getId(), infoMaria, endMaria);
        p15.adicionarItem(item(p15, 1, frango,     aFrangoP,    "Sem sal"));
        p15.adicionarItem(item(p15, 1, batataFrita, aBatataP,   ""));
        entityManager.persist(p15);

        Pedido p16 = Pedido.criar(fds.getId(), infoJoao, endJoao);
        p16.adicionarItem(item(p16, 2, wrap, aWrapF, ""));
        p16.adicionarItem(item(p16, 2, suco, aSucoF, ""));
        entityManager.persist(p16);

        Pedido p17 = Pedido.criar(fds.getId(), infoMaria, endMaria);
        p17.adicionarItem(item(p17, 1, milkShake, aMilkShakeF, ""));
        p17.adicionarItem(item(p17, 1, brownie,   aBrownieF,   "Quente"));
        entityManager.persist(p17);

        Pedido p18 = Pedido.criar(principal.getId(), infoJoao, endJoao);
        p18.adicionarItem(item(p18, 2, salada,     aSaladaP,    ""));
        p18.adicionarItem(item(p18, 1, refrigerante, aRefrigeranteP, ""));
        entityManager.persist(p18);

        // --- CANCELADOS ---

        Pedido p19 = Pedido.criar(principal.getId(), infoMaria, endMaria);
        p19.adicionarItem(item(p19, 2, hamburguer,   aHamburguerP,   ""));
        p19.adicionarItem(item(p19, 2, refrigerante, aRefrigeranteP, ""));
        entityManager.persist(p19);
        p19.mudarStatus(StatusPedido.CANCELADO);

        Pedido p20 = Pedido.criar(fds.getId(), infoJoao, endJoao);
        p20.adicionarItem(item(p20, 1, frango,    aFrangoF,    ""));
        p20.adicionarItem(item(p20, 1, milkShake, aMilkShakeF, ""));
        entityManager.persist(p20);
        p20.mudarStatus(StatusPedido.CANCELADO);

        // -------------------------------------------------------------------------
        // CUPONS
        // -------------------------------------------------------------------------
        PeriodoCupom periodoValido   = new PeriodoCupom("01/01/2026", "08:00", "31/12/2026", "23:59");
        PeriodoCupom periodoExpirado = new PeriodoCupom("01/01/2024", "08:00", "31/03/2024", "18:00");

        entityManager.persist(Cupom.criar("BEMVINDO10", periodoValido, true, 50,
                TipoDesconto.PORCENTAGEM, RegraRecorrencia.TRINTA_DIAS,
                new BigDecimal("10"), new BigDecimal("30"), new BigDecimal("200")));

        entityManager.persist(Cupom.criar("DESC15REAIS", periodoValido, true, 30,
                TipoDesconto.VALOR, RegraRecorrencia.QUINZE_DIAS,
                new BigDecimal("15"), new BigDecimal("60"), new BigDecimal("300")));

        entityManager.persist(Cupom.criar("EXPIRADO1", periodoExpirado, true, 100,
                TipoDesconto.PORCENTAGEM, RegraRecorrencia.VINTE_DIAS,
                new BigDecimal("10"), new BigDecimal("50"), new BigDecimal("299")));

        entityManager.persist(Cupom.criar("DESATIVADO1", periodoValido, false, 50,
                TipoDesconto.VALOR, RegraRecorrencia.TRINTA_DIAS,
                new BigDecimal("5"), new BigDecimal("45"), new BigDecimal("100")));

        entityManager.flush();
    }

    // -------------------------------------------------------------------------
    // HELPERS PRIVADOS
    // -------------------------------------------------------------------------

    private Produto prod(String nome, String descricao, String preco, int quantidade) {
        Produto p = new Produto();
        p.setNome(nome);
        p.setDescricao(descricao);
        p.setPreco(new BigDecimal(preco));
        p.setQuantidadeAtual(quantidade);
        p.setDisponibilidade(true);
        return p;
    }

    private Cardapio card(String nome, String descricao, LocalDate inicio, LocalDate fim) {
        Cardapio c = new Cardapio();
        c.setNome(nome);
        c.setDescricao(descricao);
        c.setDisponibilidade(true);
        c.setDataInicio(inicio);
        c.setDataFim(fim);
        return c;
    }

    private Associacao assoc(Cardapio cardapio, Produto produto, String preco, int quantidade) {
        Associacao a = new Associacao();
        a.setCardapio(cardapio);
        a.setProduto(produto);
        a.setPrecoCustomizado(new BigDecimal(preco));
        a.setQuantidadeCustomizada(quantidade);
        a.setDisponibilidadeCustomizada(true);
        return a;
    }

    private RepresentacaoProdutoItemPedido rep(Produto produto) {
        return new RepresentacaoProdutoItemPedido(produto.getId(), produto.getNome());
    }

    private ItemPedido item(Pedido pedido, int quantidade, Produto produto, Associacao associacao, String observacao) {
        return ItemPedido.criar(pedido, quantidade, rep(produto), associacao.resolverPrecoDeVenda(), observacao);
    }

    private void avancarAte(Pedido pedido, StatusPedido destino) {
        StatusPedido[] fluxo = {
                StatusPedido.EM_PREPARACAO,
                StatusPedido.SAIU_PARA_ENTREGA,
                StatusPedido.ENTREGUE
        };
        for (StatusPedido status : fluxo) {
            pedido.mudarStatus(status);
            if (status == destino) break;
        }
    }
}
