package br.com.jorgevmachado.springjava.domain.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoCliente {
    PESSSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Jurídica");

    private int codigo;
    private String descricao;

    public static TipoCliente toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for(TipoCliente tipo : TipoCliente.values()) {
            if (codigo.equals(tipo.getCodigo())){
                return tipo;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + codigo);
    }
}
