package br.edu.ifpr.pedidos;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private static ItemPedido item(String sku, int qtd, int estoque, int peso, boolean fragil) {
        return new ItemPedido(sku, 1_000, qtd, estoque, peso, fragil);
    }

    @Test
    void calculaSubtotal() {
        var pedido = new Pedido(List.of(
                item("A", 2, 5, 100, false),
                item("B", 0, 5, 200, true)
        ), "PR", false, null);

        assertEquals(2_000, pedido.subtotalCentavos());
    }

    @Test
    void copiaALista() {
        var lista = new ArrayList<>(List.of(
                item("A", 1, 5, 100, false)
        ));

        var pedido = new Pedido(lista, "PR", false, null);
        lista.clear();

        assertEquals(1_000, pedido.subtotalCentavos());
    }

    @Test
    void naoAceitaItemNulo() {
        assertThrows(NullPointerException.class,
                () -> new Pedido(List.of(
                        item("A", 1, 1, 100, false),
                        null
                ), "PR", false, null));
    }

    @Test
    void calculaPeso() {
        var pedido = new Pedido(List.of(
                item("A", 2, 5, 100, false),
                item("B", 3, 5, 200, false)
        ), "SP", false, null);

        assertEquals(800, pedido.pesoGramas());
    }

    @Test
    void verificaFragil() {
        var pedido = new Pedido(List.of(
                item("A", 1, 5, 100, true)
        ), "PR", false, null);

        assertTrue(pedido.temFragil());
    }

    @Test
    void ignoraFragilComQuantidadeZero() {
        var pedido = new Pedido(List.of(
                item("A", 0, 5, 100, true)
        ), "PR", false, null);

        assertFalse(pedido.temFragil());
    }

    @Test
    void verificaEstoque() {
        var pedido = new Pedido(List.of(
                item("A", 1, 1, 100, false),
                item("B", 2, 1, 100, false)
        ), "PR", false, null);

        assertFalse(pedido.estoqueSuficiente());
    }

    @Test
    void estoqueSuficiente() {
        var pedido = new Pedido(List.of(
                item("A", 1, 2, 100, false),
                item("B", 1, 1, 100, false)
        ), "PR", false, null);

        assertTrue(pedido.estoqueSuficiente());
    }

    @Test
    void aceitaListaVazia() {
        var pedido = new Pedido(List.of(), "PR", false, null);

        assertEquals(0, pedido.subtotalCentavos());
        assertEquals(0, pedido.pesoGramas());
        assertFalse(pedido.temFragil());
        assertTrue(pedido.estoqueSuficiente());
    }

    @Test
    void rejeitaListaInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pedido(null, "PR", false, null));

        var itens = new ArrayList<ItemPedido>();

        for (int i = 0; i < 101; i++) {
            itens.add(item("X" + i, 1, 1, 1, false));
        }

        assertThrows(IllegalArgumentException.class,
                () -> new Pedido(itens, "PR", false, null));
    }

    @Test
    void aceitaCemItens() {
        var itens = new ArrayList<ItemPedido>();

        for (int i = 0; i < 100; i++) {
            itens.add(item("X" + i, 1, 1, 1, false));
        }

        var pedido = new Pedido(itens, "PR", false, null);

        assertEquals(100_000, pedido.subtotalCentavos());
    }

    @Test
    void rejeitaUfInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pedido(List.of(), null, false, null));

        assertThrows(IllegalArgumentException.class,
                () -> new Pedido(List.of(), "pr", false, null));

        assertThrows(IllegalArgumentException.class,
                () -> new Pedido(List.of(), "PR1", false, null));
    }

    @Test
    void aceitaUfValida() {
        var pedido = new Pedido(
                List.of(),
                "PR",
                true,
                "BEMVINDO"
        );

        assertEquals("PR", pedido.uf());
        assertTrue(pedido.expresso());
        assertEquals("BEMVINDO", pedido.cupom());
    }
}