package com.ebernet.bazar.controller;

import com.ebernet.bazar.model.Cliente;
import com.ebernet.bazar.service.IClienteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    @Autowired
    private IClienteService clienteser;
    
    @PostMapping("/clientes/crear")
    public String crearCliente(@RequestBody Cliente cliente){
        clienteser.crearCliente(cliente);
        return "Cliente creado correctamente";
    }
    @GetMapping("/clientes")
    public List<Cliente> listaClientes(){
        return clienteser.listaClientes();
    }
    @GetMapping("/clientes/{idCliente}")
    public Cliente traerCliente(@PathVariable Long idCliente){
        return clienteser.traerCliente(idCliente);
    }
    @DeleteMapping("/clientes/eliminar/{idCliente}")
    public String eliminarCliente(@PathVariable Long idCliente){
        clienteser.eliminarCliente(idCliente);
        return "Cliente eliminado correctamente";
    }
    @PutMapping("/clientes/editar/{idCliente}")
    public Cliente editarCliente(@PathVariable Long idCliente,
                                 @RequestParam(required = false, name="nuevoIdCliente")Long nuevoIdCliente,
                                 @RequestParam(required = false, name="nuevoNombre")String nuevoNombre,
                                 @RequestParam(required = false, name="nuevoApellido")String nuevoApellido,
                                 @RequestParam(required = false, name="nuevoDni")Long nuevoDni){
        
        return clienteser.editarCliente(idCliente, nuevoIdCliente, nuevoNombre, nuevoApellido, nuevoDni);    
    }
}
