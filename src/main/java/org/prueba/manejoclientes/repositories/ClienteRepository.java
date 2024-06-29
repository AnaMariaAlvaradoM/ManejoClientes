package org.prueba.manejoclientes.repositories;

import org.prueba.manejoclientes.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
