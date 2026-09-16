package br.edu.ifpr.pedidos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnaliseRiscoTest {

    private final AnaliseRisco risco = new AnaliseRisco();

    @Test
    void rejeitaTotalNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> risco.avaliar(cliente(false, false, 1), -1, false));
    }

    @Test
    void clienteBloqueadoERecusado() {
        assertEquals("RECUSADO",
                risco.avaliar(cliente(false, true, 0), 999_999, true));
    }

    @Test
    void primeiraCompraValorAlto() {
        assertEquals("REVISAO",
                risco.avaliar(cliente(false, false, 0), 200_000, false));
    }

    @Test
    void primeiraCompraNoLimite() {
        assertEquals("APROVADO",
                risco.avaliar(cliente(false, false, 0), 100_000, false));
    }

    @Test
    void primeiraCompraExpresso() {
        assertEquals("REVISAO",
                risco.avaliar(cliente(false, false, 0), 10_000, true));
    }

    @Test
    void clienteComHistoricoValorAlto() {
        assertEquals("REVISAO",
                risco.avaliar(cliente(false, false, 1), 600_000, false));
    }

    @Test
    void clienteNoLimite() {
        assertEquals("APROVADO",
                risco.avaliar(cliente(false, false, 2), 500_000, false));
    }

    @Test
    void clienteVip() {
        assertEquals("APROVADO",
                risco.avaliar(cliente(true, false, 1), 600_000, false));
    }

    @Test
    void vipExpresso() {
        assertEquals("APROVADO",
                risco.avaliar(cliente(true, false, 10), 900_000, true));
    }

    private static Cliente cliente(boolean vip, boolean bloqueado, int compras) {
        return new Cliente(vip, bloqueado, compras);
    }
}