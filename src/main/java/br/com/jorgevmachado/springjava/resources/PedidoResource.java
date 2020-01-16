package br.com.jorgevmachado.springjava.resources;

import br.com.jorgevmachado.springjava.domain.Pedido;
import br.com.jorgevmachado.springjava.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @GetMapping
    public String index(){
        return "REST está funcionando!";
    }

    @GetMapping(value = "/{id}")
    public Pedido show(@PathVariable Integer id) {
        Pedido obj =  service.find(id);
        return obj;
    }

    @PostMapping
    public URI insert(@Valid @RequestBody Pedido obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return uri;
    }

}
