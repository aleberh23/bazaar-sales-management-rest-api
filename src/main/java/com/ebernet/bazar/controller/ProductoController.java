package com.ebernet.bazar.controller;

import com.ebernet.bazar.model.Producto;
import com.ebernet.bazar.service.IProductoService;
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
public class ProductoController {
    @Autowired
    private IProductoService produser;
    
    @PostMapping("/productos/crear")
    public String crearProducto(@RequestBody Producto produ){
        produser.crearProducto(produ);
        
        return "Producto registrado correctamente";
    }
    
    @GetMapping("/productos")
    public List<Producto> listaProductos(){
        return produser.listaProductos();
    }
    
    @GetMapping("/productos/falta_stock")
    public List<Producto> listaBajoStock(){
        return produser.productosConStockBajo();
    }
    
    @GetMapping("/productos/{idProd}")
    public Producto traerProducto(@PathVariable Long idProd){
        return produser.traerProducto(idProd);
    }
    
    @DeleteMapping("/productos/eliminar/{idProd}")
    public String eliminarProducto(@PathVariable Long idProd){
        produser.eliminarProducto(idProd);
        return "Producto eliminado correctamente";
    }
    
    @PutMapping("/productos/editar/{idProd}")
    public Producto editarProducto(@PathVariable Long idProd,
                                   @RequestParam(required = false, name="nuevoIdProd")Long nuevoIdProd,
                                   @RequestParam(required = false, name="nuevoNombre")String nuevoNombre,
                                   @RequestParam(required = false, name="nuevaMarca")String nuevaMarca,
                                   @RequestParam(required = false, name="nuevoCosto")Double nuevoCosto,
                                   @RequestParam(required = false, name="nuevaCantidad")Double nuevaCantidad){
        return produser.editarProducto(idProd, idProd, nuevoNombre, nuevaMarca, nuevoCosto, nuevaCantidad);
    }
}
