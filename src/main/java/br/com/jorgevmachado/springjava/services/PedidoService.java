package br.com.jorgevmachado.springjava.services;

import br.com.jorgevmachado.springjava.domain.Pedido;
import br.com.jorgevmachado.springjava.repositories.PedidoRepository;
import br.com.jorgevmachado.springjava.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

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
}
