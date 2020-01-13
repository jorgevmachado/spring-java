package br.com.jorgevmachado.springjava.services;

import br.com.jorgevmachado.springjava.domain.Cidade;
import br.com.jorgevmachado.springjava.domain.Cliente;
import br.com.jorgevmachado.springjava.domain.Endereco;
import br.com.jorgevmachado.springjava.domain.enumerations.TipoCliente;
import br.com.jorgevmachado.springjava.dto.ClienteDTO;
import br.com.jorgevmachado.springjava.dto.ClienteInsertDTO;
import br.com.jorgevmachado.springjava.repositories.ClienteRepository;
import br.com.jorgevmachado.springjava.repositories.EnderecoRepository;
import br.com.jorgevmachado.springjava.services.exceptions.DataIntegrityException;
import br.com.jorgevmachado.springjava.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Page<Cliente> findPage(
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

    public Cliente find(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(
                () -> new ObjectNotFoundException(
                        "Objeto não encontrado! id:" +
                                id +
                                ", Tipo: " +
                                Cliente.class.getName()
                )
        );
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj ;
    }

    public Cliente update(Cliente obj) {
        Cliente newOBJ = find(obj.getId());
        updateData(newOBJ, obj);
        return repository.save(newOBJ);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir há pedidos relacionados");
        }
    }

    public Cliente fromDTO(ClienteDTO objDTO) {
        return new Cliente(
                objDTO.getId(),
                objDTO.getNome(),
                objDTO.getEmail(),
                null,
                null
        );
    }

    public Cliente fromDTO(ClienteInsertDTO objDTO) {
        Cliente cliente = new Cliente(
                null,
                objDTO.getNome(),
                objDTO.getEmail(),
                objDTO.getCpfOuCnpj(),
                TipoCliente.toEnum(objDTO.getTipo())
        );
        Cidade cidade = new Cidade(
                objDTO.getCidadeId(),
                null,
                null
        );
        Endereco endereco = new Endereco(
                null,
                objDTO.getLogradouro(),
                objDTO.getNumero(),
                objDTO.getComplemento(),
                objDTO.getBairro(),
                objDTO.getCep(),
                cliente,
                cidade
        );
        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(objDTO.getTelefone1());
        if(objDTO.getTelefone2()!=null){
            cliente.getTelefones().add((objDTO.getTelefone2()));
        }
        if(objDTO.getTelefone3()!=null){
            cliente.getTelefones().add((objDTO.getTelefone3()));
        }
        return cliente;
    }

    private  void updateData(Cliente newOBJ, Cliente obj) {
        newOBJ.setNome(obj.getNome());
        newOBJ.setEmail(obj.getEmail());
    }
}
