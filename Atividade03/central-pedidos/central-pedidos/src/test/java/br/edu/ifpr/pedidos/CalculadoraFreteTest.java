package br.edu.ifpr.pedidos;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CalculadoraFreteTest {

    private final CalculadoraFrete frete = new CalculadoraFrete();

    @Test
    void rejeitaLiquidoNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> frete.calcular(pedido("PR", false, false, 1_000), cliente(false), -1));
    }

    @Test
    void calculaParana() {
        assertEquals(1_200,
                frete.calcular(pedido("PR", false, false, 1_000), cliente(false), 10_000));
    }

    @Test
    void calculaSpERj() {
        assertEquals(2_000,
                frete.calcular(pedido("SP", false, false, 1_000), cliente(false), 10_000));

        assertEquals(2_000,
                frete.calcular(pedido("RJ", false, false, 1_000), cliente(false), 10_000));
    }

    @Test
    void calculaOutrosEstados() {
        assertEquals(3_000,
                frete.calcular(pedido("MG", false, false, 1_000), cliente(false), 10_000));
    }

    @Test
    void naoCobraAdicionalAteDoisKg() {
        assertEquals(1_200,
                frete.calcular(pedido("PR", false, false, 2_000), cliente(false), 10_000));
    }

    @Test
    void cobraAdicionalPorPeso() {
        assertEquals(1_500,
                frete.calcular(pedido("PR", false, false, 2_001), cliente(false), 10_000));
    }

    @Test
    void cobraMaisDeUmaFaixaDePeso() {
        assertEquals(1_800,
                frete.calcular(pedido("PR", false, false, 3_001), cliente(false), 10_000));
    }

    @Test
    void freteGratisAcimaDeTrintaMil() {
        assertEquals(0,
                frete.calcular(pedido("PR", false, false, 3_001), cliente(false), 30_000));
    }

    @Test
    void abaixoDeTrintaMilCobraFrete() {
        assertEquals(1_200,
                frete.calcular(pedido("PR", false, false, 1_000), cliente(false), 29_999));
    }

    @Test
    void expressoCobraTaxaExtra() {
        assertEquals(2_700,
                frete.calcular(pedido("PR", true, false, 1_000), cliente(false), 30_000));
    }

    @Test
    void vipPagaMetade() {
        assertEquals(600,
                frete.calcular(pedido("PR", false, false, 1_000), cliente(true), 10_000));
    }

    @Test
    void vipComPesoExtra() {
        assertEquals(750,
                frete.calcular(pedido("PR", false, false, 2_001), cliente(true), 10_000));
    }

    @Test
    void produtoFragilTemTaxaExtra() {
        assertEquals(1_700,
                frete.calcular(pedido("PR", false, true, 1_000), cliente(false), 10_000));
    }

    @Test
    void vipExpressoEFragil() {
        assertEquals(2_600,
                frete.calcular(pedido("PR", true, true, 1_000), cliente(true), 10_000));
    }

    @Test
    void freteGratisComExpresso() {
        assertEquals(3_200,
                frete.calcular(pedido("PR", true, true, 1_000), cliente(false), 30_000));
    }

    private static Pedido pedido(String uf, boolean expresso, boolean fragil, int peso) {
        return new Pedido(
                List.of(new ItemPedido("1423", 1_000, 1, 5, peso, fragil)),
                uf,
                expresso,
                null
        );
    }

    private static Cliente cliente(boolean vip) {
        return new Cliente(vip, false, 1);
    }
}