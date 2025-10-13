package com.upiiz.practica_05.controllers;

import com.upiiz.practica_05.entities.Cliente;
import com.upiiz.practica_05.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/garufa/public/v1/clientes")
    public ResponseEntity<List<Cliente>> getHeroes() {
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    @GetMapping("/garufa/public/v1/clientes/{id}")
    public ResponseEntity<Cliente> getHeroe(@PathVariable int id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    @PostMapping("/garufa/public/v1/clientes")
    public ResponseEntity<Cliente> createHeroe(@RequestBody Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.addCliente(cliente));
        //return ResponseEntity.ok(heroeService.addHeroe(heroe));
    }

    @PutMapping("/garufa/public/v1/clientes/{id}")
    public ResponseEntity<Cliente> updateHeroe(@PathVariable int id, @RequestBody Cliente cliente) {
        cliente.setId((long) id);
        return ResponseEntity.ok(clienteService.updateCliente(cliente));
    }

    @DeleteMapping("/garufa/public/v1/clientes/{id}")
    public ResponseEntity<Void> deleteHeroe(@PathVariable int id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
