package br.com.GreenfieldHealth.services;

import br.com.GreenfieldHealth.repositories.MedicamentosRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicamentosService {
    private final MedicamentosRepository medicamentosRepository;

    public MedicamentosService(MedicamentosRepository medicamentosRepository) {
        this.medicamentosRepository = medicamentosRepository;
    }
}
