package br.com.mastersys.projection;

import java.math.BigDecimal;

public interface FaturamentoMensalProjection {

    String getMes(); //projecao mes do faturamento
    BigDecimal getTotal(); //valor faturamento do mes

}