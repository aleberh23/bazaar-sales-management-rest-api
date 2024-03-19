package com.ebernet.bazar.service;

import com.ebernet.bazar.dto.ResumenVentasDiaDTO;
import com.ebernet.bazar.dto.VentaDTO;
import com.ebernet.bazar.dto.VentaMayorDTO;
import com.ebernet.bazar.model.Producto;
import com.ebernet.bazar.model.Venta;
import java.time.LocalDate;
import java.util.List;

public interface IVentaService {
    public void crearVenta(VentaDTO ventadto);
    public List<Venta> listaVentas();
    public Venta traerVenta(Long idVenta);
    public void eliminarVenta(Long idVenta);
    public Venta editarVenta(Long idVenta, VentaDTO nuevaVenta); 
    //resumen ventas por fecha
    public ResumenVentasDiaDTO resumenDiarioDeVentas(LocalDate fecha);
    //venta con monto mas alto
    public VentaMayorDTO ventaMasAlta();
    
    
    
}
