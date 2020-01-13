package br.com.jorgevmachado.springjava.domain;

import br.com.jorgevmachado.springjava.domain.enumerations.EstadoPagamento;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class PagamentoComCartao extends Pagamento {

    private static final long serialVersionUID = 1L;

    private Integer numeroDeParcelas;

    public PagamentoComCartao() {}

    public PagamentoComCartao(
            Integer id,
            EstadoPagamento estado,
            Pedido pedido,
            Integer numeroDeParcelas
    ) {
        super(id, estado, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }
}
