package org.prueba.manejoclientes.controller;
import org.prueba.manejoclientes.models.Cliente;
import org.prueba.manejoclientes.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
   public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteService.createCliente(cliente);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente getClienteById(@PathVariable Long id) {
        return clienteService.getClienteById(id);
    }

    @GetMapping("/todos")
    public List<Cliente> getAllClientes() {
        return clienteService.getAllClientes();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Cliente updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        return clienteService.updateCliente(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
    }
}
