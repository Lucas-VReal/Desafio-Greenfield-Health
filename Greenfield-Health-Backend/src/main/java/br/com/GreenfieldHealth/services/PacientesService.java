package br.com.GreenfieldHealth.services;

import br.com.GreenfieldHealth.repositories.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
public class PacientesService {
    private final PacienteRepository pacienteRepository;

    public PacientesService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }
}
