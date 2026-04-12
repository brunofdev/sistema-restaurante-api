package com.restaurante01.api_restaurante.infraestrutura.inicializador;

import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.role.Role;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import jakarta.persistence.EntityManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
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
        Operador admin = new Operador();
        admin.setNome("Carlos Gerente");
        admin.setUserName("carlos_admin");
        admin.setSenha(senhaPadrao);
        admin.setEmail("gerencia@restaurante.com");
        admin.setCpf("00000000000");
        admin.setRole(Role.ADMIN3); // Acesso total
        admin.setContaAtiva(true);
        admin.setMatricula(1001L);
        entityManager.persist(admin);

        // --- 2. CRIANDO CLIENTE ---
        Cliente cliente = new Cliente();
        cliente.setNome("Maria Cliente");
        cliente.setUserName("maria_cli");
        cliente.setSenha(senhaPadrao);
        cliente.setEmail("maria@email.com");
        cliente.setCpf("11111111111");
        cliente.setRole(Role.USER);
        cliente.setContaAtiva(true);
        cliente.setPontuacaoFidelidade(0);
        cliente.setTelefone("11999999999");
        cliente.setEstado("SP");
        cliente.setCidade("São Paulo");
        cliente.setBairro("Centro");
        cliente.setCep("01001000");
        cliente.setRua("Avenida Paulista");
        cliente.setNumeroResidencia(1000);
        cliente.setComplemento("Apto 12");
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
        CardapioProduto comboBurger = new CardapioProduto();
        comboBurger.setCardapio(cardapioInverno);
        comboBurger.setProduto(hamburguer);
        comboBurger.setPrecoCustomizado(new BigDecimal("30.00")); // Preço promocional no cardápio
        comboBurger.setQuantidadeCustomizada(10);
        comboBurger.setDisponibilidadeCustomizada(true);
        entityManager.persist(comboBurger);

        // --- 6. CRIANDO PEDIDO ---
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatusPedido(StatusPedido.PENDENTE);
        pedido.setEnderecoEntrega(cliente.getRua() + ", " + cliente.getNumeroResidencia());

        // Adicionando Itens (a lógica de calcularTotal e linkar o Pedido já está no metodo adicionarItem)
        ItemPedido item1 = new ItemPedido(hamburguer, 2, new BigDecimal("35.00"), "Carne mal passada");
        ItemPedido item2 = new ItemPedido(refrigerante, 2, new BigDecimal("8.00"), null);

        pedido.adicionarItem(item1);
        pedido.adicionarItem(item2);

        entityManager.persist(pedido);

        // Salva tudo no banco H2
        entityManager.flush();
    }
}