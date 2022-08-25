package br.com.GreenfieldHealth.services;

import br.com.GreenfieldHealth.repositories.MedicosRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicosService {
    private final MedicosRepository medicosRepository;

    public MedicosService(MedicosRepository medicosRepository) {
        this.medicosRepository = medicosRepository;
    }
}
