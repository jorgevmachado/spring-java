package br.com.jorgevmachado.springjava.repositories;

import br.com.jorgevmachado.springjava.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {}
