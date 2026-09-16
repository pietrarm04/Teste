package br.edu.ifpr.pedidos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void aceitaDadosValidos() {
        var c = new Cliente(true, false, 3);
        assertTrue(c.vip());
        assertFalse(c.bloqueado());
        assertEquals(3, c.comprasAnteriores());
    }

    @Test
    void aceitaHistoricoZero() {
        var c = new Cliente(false, false, 0);
        assertEquals(0, c.comprasAnteriores());
    }

    @Test
    void rejeitaHistoricoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente(false, false, -1));
    }
}