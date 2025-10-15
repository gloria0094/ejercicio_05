package com.upiiz.ejercicio_05.services;

import com.upiiz.ejercicio_05.entities.Cliente;
import com.upiiz.ejercicio_05.entities.DetallePedido;
import com.upiiz.ejercicio_05.entities.Pedido;
import com.upiiz.ejercicio_05.entities.Platillo;
import com.upiiz.ejercicio_05.repositories.ClienteRepository;
import com.upiiz.ejercicio_05.repositories.PedidoRepository;
import com.upiiz.ejercicio_05.repositories.PlatilloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final PlatilloRepository platilloRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteRepository clienteRepository,
                         PlatilloRepository platilloRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.platilloRepository = platilloRepository;
    }

    public List<Pedido> getAllPedidos() {
        return (List<Pedido>) pedidoRepository.findAll();
    }

    public Pedido getPedidoById(int id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Pedido addPedido(Pedido pedido) {
        // Cliente embebido con { "id": X }
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            throw new IllegalArgumentException("El pedido debe tener un cliente válido con ID existente.");
        }
        Cliente cliente = clienteRepository.findById(Math.toIntExact(pedido.getCliente().getId()))
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + pedido.getCliente().getId()));
        pedido.setCliente(cliente);

        // Detalles
        List<DetallePedido> detallesList = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        if (pedido.getDetalles() != null) {
            for (DetallePedido det : pedido.getDetalles()) {
                if (det.getPlatillo() == null || det.getPlatillo().getId() == null) {
                    throw new IllegalArgumentException("Cada detalle debe tener un platillo con ID válido.");
                }

                Platillo platillo = platilloRepository.findById(det.getPlatillo().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Platillo no encontrado con ID: " + det.getPlatillo().getId()));

                det.setPedido(pedido);      // back-reference
                det.setPlatillo(platillo);  // entidad administrada

                // Si no mandan precioActual, se congela con el precio vigente del platillo
                if (det.getPrecioActual() == null) {
                    det.setPrecioActual(platillo.getPrecio());
                }

                total = total.add(det.getPrecioActual());
                detallesList.add(det);
            }
        }

        pedido.setDetalles(detallesList);
        pedido.setTotal(total);

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido updatePedido(Pedido pedido) {
        BigDecimal total = BigDecimal.ZERO;
        if (pedido.getDetalles() != null) {
            for (DetallePedido det : pedido.getDetalles()) {
                det.setPedido(pedido);
                if (det.getPrecioActual() != null) {
                    total = total.add(det.getPrecioActual());
                }
            }
        }
        pedido.setTotal(total);
        return pedidoRepository.save(pedido);
    }

    public void deletePedido(int id) {
        pedidoRepository.deleteById(id);
    }
}
