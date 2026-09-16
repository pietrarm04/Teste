package br.edu.ifpr.boletim;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParticipacaoTest {
    // TODO: criar o objeto, chamar calcularPontos e verificar o resultado.

    @Test
    void deveCalcularTresPontos() {
        Participacao participacao = new Participacao();
        int resultado = participacao.calcularPontos(true, true);
        assertEquals(3, resultado);
    }

    @Test
    void deveCalcularZeroPontos(){
        Participacao participacao = new Participacao();
        int resultado = participacao.calcularPontos(false,false);
        assertEquals(0,resultado);
    }

}
