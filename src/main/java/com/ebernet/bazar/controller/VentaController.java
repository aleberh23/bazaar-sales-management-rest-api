package com.ebernet.bazar.controller;

import com.ebernet.bazar.dto.ResumenVentasDiaDTO;
import com.ebernet.bazar.dto.VentaDTO;
import com.ebernet.bazar.dto.VentaMayorDTO;
import com.ebernet.bazar.model.Producto;
import com.ebernet.bazar.model.Venta;
import com.ebernet.bazar.service.IVentaService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VentaController {
    @Autowired
    private IVentaService ventaser;
    
    @PostMapping("/ventas/crear")
    public String crearVenta(@RequestBody VentaDTO ventadto){
        ventaser.crearVenta(ventadto);
        return "Venta creada correctamente..";
    }
    
    @GetMapping("/ventas")
    public List<Venta> listaVentas(){
        return ventaser.listaVentas();
    }
    
    @GetMapping("/ventas/{idVenta}")
    public Venta traerVenta(@PathVariable Long idVenta){
        return ventaser.traerVenta(idVenta);
    }
    
    @DeleteMapping("/ventas/eliminar/{idVenta}")
    public String eliminarVenta(@PathVariable Long idVenta){
        ventaser.eliminarVenta(idVenta);
        return "Venta eliminada correctamente..";
    }
    
    @PutMapping("/ventas/editar/{idVenta}")
    public Venta editarVenta(@PathVariable Long idVenta, @RequestBody VentaDTO nuevaVenta){
        return ventaser.editarVenta(idVenta, nuevaVenta);
    }
    
    @GetMapping("/ventas/productos/{idVenta}")
    public List<Producto> obtenerListaProductosDeVenta(@PathVariable Long idVenta){
        Venta v = ventaser.traerVenta(idVenta);
        return v.getProductos();
    }
    
    @GetMapping("/ventas/resumen/{fecha}")
    public ResumenVentasDiaDTO resumenDiario(@PathVariable LocalDate fecha){
        return ventaser.resumenDiarioDeVentas(fecha);
    }
    
    @GetMapping("/ventas/mayor_venta")
    public VentaMayorDTO ventaDeMayorMonto(){
        return ventaser.ventaMasAlta();
    }
}
