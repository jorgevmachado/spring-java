package br.com.jorgevmachado.springjava.domain;

import br.com.jorgevmachado.springjava.domain.enumerations.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@Entity
public class PagamentoComBoleto extends Pagamento {

    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataVencimento;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataPagamento;

    public PagamentoComBoleto() {}

    public PagamentoComBoleto(
            Integer id,
            EstadoPagamento estado,
            Pedido pedido,
            Date dataVencimento,
            Date dataPagamento
    ) {
        super(id, estado, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }


}
