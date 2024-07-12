package org.prueba.manejoclientes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prueba.manejoclientes.models.Cliente;
import  org.prueba.manejoclientes.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup() {
        clienteRepository.deleteAll();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAgregarCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setEmail("juan@example.com");

        MvcResult result = mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Cliente clienteResponse = objectMapper.readValue(responseContent, Cliente.class);

        assertThat(clienteResponse.getId()).isNotNull();
        assertThat(clienteResponse.getNombre()).isEqualTo("Juan");
        assertThat(clienteResponse.getEmail()).isEqualTo("juan@example.com");
    }

    @Test
    public void testObtenerClientes() throws Exception {
        Cliente cliente1 = new Cliente();
        cliente1.setNombre("Juan");
        cliente1.setEmail("juan@example.com");

        Cliente cliente2 = new Cliente();
        cliente2.setNombre("Maria");
        cliente2.setEmail("maria@example.com");

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        mockMvc.perform(get("/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[1].nombre").value("Maria"));
    }

    @Test
    public void testObtenerClientePorId() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setEmail("juan@example.com");

        cliente = clienteRepository.save(cliente);

        mockMvc.perform(get("/clientes/" + cliente.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@example.com"));
    }

    @Test
    public void testActualizarCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setEmail("juan@example.com");

        cliente = clienteRepository.save(cliente);

        Cliente detallesCliente = new Cliente();
        detallesCliente.setNombre("Juan Actualizado");
        detallesCliente.setEmail("juan.actualizado@example.com");

        mockMvc.perform(put("/clientes/" + cliente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detallesCliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Actualizado"))
                .andExpect(jsonPath("$.email").value("juan.actualizado@example.com"));
    }

    @Test
    public void testEliminarCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setEmail("juan@example.com");

        cliente = clienteRepository.save(cliente);

        mockMvc.perform(delete("/clientes/" + cliente.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Cambiado de isNoContent() a isOk()

        assertThat(clienteRepository.existsById(cliente.getId())).isFalse();
    }

}
