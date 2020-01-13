package br.com.jorgevmachado.springjava.resources;

import br.com.jorgevmachado.springjava.domain.Pedido;
import br.com.jorgevmachado.springjava.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "REST está funcionando!";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Pedido> show(@PathVariable Integer id) {
        Pedido obj =  service.find(id);
        return ResponseEntity.ok().body(obj);
    }
}
