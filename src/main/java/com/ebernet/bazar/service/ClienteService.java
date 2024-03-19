package com.ebernet.bazar.service;

import com.ebernet.bazar.model.Cliente;
import com.ebernet.bazar.repository.IClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements IClienteService{
    @Autowired
    private IClienteRepository clienterep;

    @Override
    public void crearCliente(Cliente cliente) {
        clienterep.save(cliente);
    }

    @Override
    public List<Cliente> listaClientes() {
        return clienterep.findAll();
    }

    @Override
    public Cliente traerCliente(Long idCliente) {
        return clienterep.findById(idCliente).orElse(null);
    }

    @Override
    public void eliminarCliente(Long idCliente) {
        clienterep.deleteById(idCliente);
    }

    @Override
    public Cliente editarCliente(Long idCliente, Long nuevoIdCliente, String nuevoNombre, String nuevoApellido, Long nuevoDni) {
        Cliente c = clienterep.findById(idCliente).orElse(null);
        if(nuevoIdCliente!=null){
            c.setId_cliente(idCliente);
        }
        if(nuevoNombre!=null){
            c.setNombre(nuevoNombre);
        }
        if(nuevoApellido!=null){
            c.setApellido(nuevoApellido);
        }
        if(nuevoDni!=null){
            c.setDni(nuevoDni);
        }
        clienterep.save(c);
        return c;
    }
    
    
}
