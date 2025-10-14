package com.upiiz.ejercicio_05.repositories;

import com.upiiz.ejercicio_05.entities.Pedido;
import org.springframework.data.repository.CrudRepository;

public interface PedidoRepository extends CrudRepository<Pedido, Integer> {
}
