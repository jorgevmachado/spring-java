package br.com.jorgevmachado.springjava.resources;

import br.com.jorgevmachado.springjava.domain.Produto;
import br.com.jorgevmachado.springjava.dto.ProdutoDTO;
import br.com.jorgevmachado.springjava.resources.utils.URL;
import br.com.jorgevmachado.springjava.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Produto> find(@PathVariable Integer id) {
        Produto obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

//    http://localhost:9000/produtos/?nome=or&categorias=1,4
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value="nome", defaultValue="") String nome,
            @RequestParam(value="categorias", defaultValue="") String categorias,
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction) {
        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> list = service.search(
                nomeDecoded,
                ids,
                page,
                linesPerPage,
                orderBy,
                direction
        );
        Page<ProdutoDTO> listDto = list.map(ProdutoDTO::new);
        return ResponseEntity.ok().body(listDto);
    }
}
