package br.com.jorgevmachado.springjava.services;

import br.com.jorgevmachado.springjava.domain.Categoria;
import br.com.jorgevmachado.springjava.domain.Produto;
import br.com.jorgevmachado.springjava.repositories.CategoriaRepository;
import br.com.jorgevmachado.springjava.repositories.ProdutoRepository;
import br.com.jorgevmachado.springjava.services.exceptions.DataIntegrityException;
import br.com.jorgevmachado.springjava.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Produto> findAll() {
        return repository.findAll();
    }

    public Page<Produto> findPage(
            Integer page,
            Integer linesPerPage,
            String orderBy,
            String direction
    ) {
        PageRequest pageRequest = PageRequest.of(
                page,
                linesPerPage,
                Sort.Direction.valueOf(direction)
                , orderBy
        );
        return repository.findAll(pageRequest);
    }

    public Produto find(Integer id) {
        Optional<Produto> obj = repository.findById(id);
        return obj.orElseThrow(
                () -> new ObjectNotFoundException(
                        "Objeto não encontrado! id:" +
                        id +
                        ", Tipo: " +
                        Produto.class.getName()
                )
        );
    }

    public Produto insert (Produto obj){
        obj.setId(null);
        return repository.save(obj);
    }

    public Produto update(Produto obj) {
        Produto newOBJ = find(obj.getId());
        updateData(newOBJ, obj);
        return  repository.save(newOBJ);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categuria que possui produtos");
        }
    }

    public Page<Produto> search(
            String nome,
            List<Integer> ids,
            Integer page,
            Integer linesPerPage,
            String orderBy,
            String direction
    ) {
        PageRequest pageRequest = PageRequest.of(
                page,
                linesPerPage,
                Direction.valueOf(direction),
                orderBy
        );
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }

    private void updateData(Produto newOBJ, Produto obj) {
        newOBJ.setNome(obj.getNome());
        newOBJ.setCategorias(obj.getCategorias());
        newOBJ.setItens(obj.getItens());
        newOBJ.setPreco(obj.getPreco());
    }

}
