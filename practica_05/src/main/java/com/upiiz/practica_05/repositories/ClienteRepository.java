package com.upiiz.practica_05.repositories;

import com.upiiz.practica_05.entities.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
}
