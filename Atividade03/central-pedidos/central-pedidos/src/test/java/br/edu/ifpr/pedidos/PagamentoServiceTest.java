package br.edu.ifpr.pedidos;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class PagamentoServiceTest {

    @Test
    void processadorNulo() {
        assertThrows(NullPointerException.class,
                () -> new PagamentoService(null));
    }

    @Test
    void totalInvalido() {
        var p = new PagamentoService(total -> true);

        assertThrows(IllegalArgumentException.class, () -> p.pagar(0, 1));
        assertThrows(IllegalArgumentException.class, () -> p.pagar(-10, 1));
    }

    @Test
    void tentativasInvalidas() {
        var p = new PagamentoService(total -> true);

        assertThrows(IllegalArgumentException.class, () -> p.pagar(100, 0));
        assertThrows(IllegalArgumentException.class, () -> p.pagar(100, 4));
    }

    @Test
    void pagamentoAprovado() {
        var p = new PagamentoService(total -> true);
        assertTrue(p.pagar(100, 1));
    }

    @Test
    void pagamentoRecusado() {
        var p = new PagamentoService(total -> false);
        assertFalse(p.pagar(100, 3));
    }

    @Test
    void tentaDeNovo() {
        AtomicInteger chamadas = new AtomicInteger();

        var p = new PagamentoService(total -> {
            chamadas.incrementAndGet();

            if (chamadas.get() == 1) {
                throw new IllegalStateException();
            }

            return true;
        });
        assertTrue(p.pagar(100, 3));
        assertEquals(2, chamadas.get());
    }

    @Test
    void acabaAsTentativas() {
        AtomicInteger chamadas = new AtomicInteger();

        var p = new PagamentoService(total -> {
            chamadas.incrementAndGet();
            throw new IllegalStateException();
        });
        assertFalse(p.pagar(100, 3));
        assertEquals(3, chamadas.get());
    }

    @Test
    void outraExcecao() {
        var p = new PagamentoService(total -> {
            throw new IllegalArgumentException();
        });
        assertThrows(IllegalArgumentException.class,
                () -> p.pagar(100, 3));
    }
}