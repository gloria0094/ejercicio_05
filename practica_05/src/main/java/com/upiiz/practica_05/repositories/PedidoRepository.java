package com.upiiz.practica_05.repositories;

import com.upiiz.practica_05.entities.Pedido;
import org.springframework.data.repository.CrudRepository;

public interface PedidoRepository extends CrudRepository<Pedido, Integer> {
}
