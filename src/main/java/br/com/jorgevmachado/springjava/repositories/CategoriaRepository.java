package br.com.jorgevmachado.springjava.repositories;

import br.com.jorgevmachado.springjava.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
