package br.edu.ifpr.pedidos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemPedidoTest {

    @Test
    void calculaTotalEDisponibilidade() {
        var i = new ItemPedido("1478", 2_500, 3, 3, 100, false);
        assertEquals(7_500, i.totalCentavos());
        assertTrue(i.disponivel());
    }

    @Test
    void quantidadeMaiorQueEstoqueIndisponivel() {
        var i = new ItemPedido("1586", 2_500, 4, 3, 100, false);
        assertFalse(i.disponivel());
    }

    @Test
    void quantidadeZeroTemTotalZeroEContinuaDisponivel() {
        var i = new ItemPedido("1590", 2_500, 0, 0, 100, false);
        assertEquals(0, i.totalCentavos());
        assertTrue(i.disponivel());
    }

    @Test
    void aceitaLimitesValidos() {
        var i = new ItemPedido("895", 1_000_000, 100, 100, 100_000, true);
        assertEquals(100_000_000L, i.totalCentavos());
        assertTrue(i.disponivel());
        assertTrue(i.fragil());
    }

    @Test
    void rejeitaSkuNuloOuVazio() {
        assertThrows(IllegalArgumentException.class,
                () -> new ItemPedido(null, 1_000, 1, 1, 100, false));
        assertThrows(IllegalArgumentException.class,
                () -> new ItemPedido("", 1_000, 1, 1, 100, false));
    }

    @Test
    void rejeitaPrecoForaDoIntervalo() {
        assertThrows(IllegalArgumentException.class,
                () -> new ItemPedido("1863", 0, 1, 1, 100, false));
        assertThrows(IllegalArgumentException.class,
                () -> new ItemPedido("1264", 1_000_001, 1, 1, 100, false));
    }

    @Test
    void rejeitaQuantidadeForaDoIntervalo() {
        assertThrows(IllegalArgumentException.class,
                () -> new ItemPedido("1032", 1_000, -1, 1, 100, false));
        assertThrows(IllegalArgumentException.class,
                () -> new ItemPedido("654", 1_000, 101, 1, 100, false));
    }

    @Test
    void rejeitaEstoqueNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new ItemPedido("362", 1_000, 1, -1, 100, false));
    }

    @Test
    void rejeitaPesoForaDoIntervalo() {
        assertThrows(IllegalArgumentException.class,() -> new ItemPedido("12", 1_000, 1, 1, 0, false));
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido("65", 1_000, 1, 1, 100_001, false));
    }
}