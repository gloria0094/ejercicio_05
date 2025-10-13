package com.upiiz.practica_05.controllers;

import com.upiiz.practica_05.entities.DetallePedido;
import com.upiiz.practica_05.entities.Pedido;
import com.upiiz.practica_05.services.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/garufa/public/v1/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Evita 415 aceptando cualquier Content-Type (incluye application/json;charset=UTF-8)
    @PostMapping(consumes = "*/*", produces = "application/json")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Pedido pedido) {
        Pedido saved = pedidoService.addPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(toCompact(saved));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> list() {
        var compact = pedidoService.getAllPedidos()
                .stream()
                .map(this::toCompact)
                .toList();
        return ResponseEntity.ok(compact);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable int id) {
        Pedido p = pedidoService.getPedidoById(id);
        if (p == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toCompact(p));
    }

    // Evita 415 aceptando cualquier Content-Type
    @PutMapping(value = "/{id}", consumes = "*/*", produces = "application/json")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Pedido body) {
        body.setId(id);
        Pedido updated = pedidoService.updatePedido(body);
        return ResponseEntity.ok(toCompact(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Helpers ----------

    // Respuesta compacta para evitar JSON gigantes
    private Map<String, Object> toCompact(Pedido p) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", p.getId());
        // Nota: si cambiaste la fecha a String en la entidad, esto funciona tal cual.
        // Si la dejaste como LocalDateTime, asegúrate de tener jackson-datatype-jsr310 registrado.
        m.put("fecha", p.getFecha());
        m.put("total", p.getTotal());

        Map<String, Object> cliente = new LinkedHashMap<>();
        if (p.getCliente() != null) {
            cliente.put("id", p.getCliente().getId());
            cliente.put("nombre", p.getCliente().getNombre());
        }
        m.put("cliente", cliente);

        var detalles = (p.getDetalles() == null) ? List.<Map<String, Object>>of()
                : p.getDetalles().stream().map(this::toCompactDetalle).toList();
        m.put("detalles", detalles);

        return m;
    }

    private Map<String, Object> toCompactDetalle(DetallePedido d) {
        Map<String, Object> md = new LinkedHashMap<>();
        md.put("id", d.getId());
        if (d.getPlatillo() != null) {
            md.put("platilloId", d.getPlatillo().getId());
            md.put("platilloNombre", d.getPlatillo().getNombre());
        }
        md.put("precioActual", d.getPrecioActual());
        return md;
    }
}
