package com.ebernet.bazar.service;

import com.ebernet.bazar.model.Cliente;
import java.util.List;

public interface IClienteService {
    public void crearCliente(Cliente cliente);
    public List<Cliente> listaClientes();
    public Cliente traerCliente(Long idCliente);
    public void eliminarCliente(Long idCliente);
    public Cliente editarCliente(Long idCliente, Long nuevoIdCliente, String nuevoNombre, String nuevoApellido, Long nuevoDni);
}
