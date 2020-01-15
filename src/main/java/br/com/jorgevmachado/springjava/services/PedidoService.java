package br.com.jorgevmachado.springjava.services;

import br.com.jorgevmachado.springjava.domain.ItemPedido;
import br.com.jorgevmachado.springjava.domain.PagamentoComBoleto;
import br.com.jorgevmachado.springjava.domain.Pedido;
import br.com.jorgevmachado.springjava.domain.enumerations.EstadoPagamento;
import br.com.jorgevmachado.springjava.repositories.ItemPedidoRepository;
import br.com.jorgevmachado.springjava.repositories.PagamentoRepository;
import br.com.jorgevmachado.springjava.repositories.PedidoRepository;
import br.com.jorgevmachado.springjava.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido find(Integer id) {
        Optional<Pedido> obj = repository.findById(id);
        return obj.orElseThrow(
                () -> new ObjectNotFoundException(
                        "Objeto não encontrado! id:" +
                                id +
                                ", Tipo: " +
                                Pedido.class.getName()
                )
        );
    }

    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = repository.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        return obj;
    }

//    {
//        "cliente": {
//        "id": 1
//    },
//        "enderecoDeEntrega": {
//        "id": 1
//    },
//        "pagamento": {
//        "numeroDeParcelas": 10,
//                "@type": "pagamentoComCartao"
//    },
//        "itens": [
//        {
//            "quantidade": 2,
//                "produto": {
//            "id": 3
//        }
//        },
//        {
//            "quantidade": 1,
//                "produto" : {
//            "id": 1
//        }
//        }
//	]
//    }

}
