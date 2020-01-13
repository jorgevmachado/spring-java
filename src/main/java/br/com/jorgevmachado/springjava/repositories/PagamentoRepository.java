package br.com.jorgevmachado.springjava.repositories;

import br.com.jorgevmachado.springjava.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {}
