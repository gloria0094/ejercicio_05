package com.upiiz.ejercicio_05.services;

import com.upiiz.ejercicio_05.entities.Platillo;
import com.upiiz.ejercicio_05.repositories.PlatilloRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatilloService {
    private PlatilloRepository platilloRepository;

    public PlatilloService(PlatilloRepository platilloRepository) {
        this.platilloRepository = platilloRepository;
    }

    //CRUD Platillos
    public List<Platillo> getAllPlatillos() {
        return  (List<Platillo>) platilloRepository.findAll(); }
    public Platillo getPlatilloById(int id) { return platilloRepository.findById((long) id).orElse(null); }
    public Platillo addPlatillo(Platillo platillo) { return platilloRepository.save(platillo); }
    public Platillo updatePlatillo(Platillo platillo) { return platilloRepository.save(platillo); }
    public void deletePlatillo(int id) { platilloRepository.deleteById((long) id); }
}
