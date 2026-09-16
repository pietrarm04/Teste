package br.edu.ifpr.pedidos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PoliticaDescontoTest {

    private final PoliticaDesconto politica = new PoliticaDesconto();

    private static Cliente cliente(boolean vip, int compras) {
        return new Cliente(vip, false, compras);
    }

    @Test
    void subtotalNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> politica.calcular(cliente(false, 1), -1, null));
    }

    @Test
    void semDesconto() {
        assertEquals(0,
                politica.calcular(cliente(false, 1), 10_000, null));
    }

    @Test
    void clienteVip() {
        assertEquals(1_000,
                politica.calcular(cliente(true, 1), 10_000, null));
    }

    @Test
    void clienteComum() {
        assertEquals(2_500,
                politica.calcular(cliente(false, 1), 50_000, null));

        assertEquals(0,
                politica.calcular(cliente(false, 1), 49_999, null));
    }

    @Test
    void cupomVazio() {
        assertEquals(2_500,
                politica.calcular(cliente(false, 1), 50_000, "  "));
    }

    @Test
    void bemVindo() {
        assertEquals(2_000,
                politica.calcular(cliente(false, 0), 10_000, "BEMVINDO"));
    }

    @Test
    void bemVindoComEspacos() {
        assertEquals(2_000,
                politica.calcular(cliente(false, 0), 20_000, " bemvindo "));
    }

    @Test
    void bemVindoNaoAplica() {
        assertEquals(2_500,
                politica.calcular(cliente(false, 1), 50_000, "BEMVINDO"));

        assertEquals(0,
                politica.calcular(cliente(false, 0), 9_999, "BEMVINDO"));
    }

    @Test
    void extra10() {
        assertEquals(2_000,
                politica.calcular(cliente(false, 1), 20_000, "EXTRA10"));
    }

    @Test
    void extra10NaoAplica() {
        assertEquals(0,
                politica.calcular(cliente(false, 1), 19_999, "EXTRA10"));
    }

    @Test
    void cupomDesconhecido() {
        assertThrows(IllegalArgumentException.class,
                () -> politica.calcular(cliente(false, 1), 20_000, "OUTRO"));
    }

    @Test
    void descontoNaoPassaDoLimite() {
        assertEquals(2_000,
                politica.calcular(cliente(false, 0), 20_000, "EXTRA10"));

        assertEquals(4_000,
                politica.calcular(cliente(true, 1), 20_000, "EXTRA10"));
    }
}