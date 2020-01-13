package br.com.jorgevmachado.springjava.domain.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoPagamento {
    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");


    private Integer codigo;
    private String descricao;

    public static EstadoPagamento toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (EstadoPagamento tipo : EstadoPagamento.values()) {
            if (codigo.equals(tipo.getCodigo())) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + codigo);
    }
}
