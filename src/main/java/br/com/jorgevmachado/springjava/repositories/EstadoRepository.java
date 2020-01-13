package br.com.jorgevmachado.springjava.repositories;

import br.com.jorgevmachado.springjava.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
}
