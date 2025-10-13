package com.upiiz.practica_05.controllers;

import com.upiiz.practica_05.entities.Platillo;
import com.upiiz.practica_05.services.PlatilloService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlatilloController {
    private final PlatilloService platilloService;

    public PlatilloController(PlatilloService platilloService) {
        this.platilloService = platilloService;
    }

    @GetMapping("/garufa/public/v1/platillos")
    public ResponseEntity<List<Platillo>> getHeroes() {
        return ResponseEntity.ok(platilloService.getAllPlatillos());
    }

    @GetMapping("/garufa/public/v1/platillos/{id}")
    public ResponseEntity<Platillo> getHeroe(@PathVariable int id) {
        return ResponseEntity.ok(platilloService.getPlatilloById(id));
    }

    @PostMapping("/garufa/public/v1/platillos")
    public ResponseEntity<Platillo> createHeroe(@RequestBody Platillo platillo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(platilloService.addPlatillo(platillo));
        //return ResponseEntity.ok(heroeService.addHeroe(heroe));
    }

    @PutMapping("/garufa/public/v1/platillos/{id}")
    public ResponseEntity<Platillo> updateHeroe(@PathVariable int id, @RequestBody Platillo platillo) {
        platillo.setId((long) id);
        return ResponseEntity.ok(platilloService.updatePlatillo(platillo));
    }

    @DeleteMapping("/garufa/public/v1/platillos/{id}")
    public ResponseEntity<Void> deleteHeroe(@PathVariable int id) {
        platilloService.deletePlatillo(id);
        return ResponseEntity.noContent().build();
    }
}
