package com.ebernet.bazar.service;

import com.ebernet.bazar.dto.ResumenVentasDiaDTO;
import com.ebernet.bazar.dto.VentaDTO;
import com.ebernet.bazar.dto.VentaMayorDTO;
import com.ebernet.bazar.model.Producto;
import com.ebernet.bazar.model.Venta;
import com.ebernet.bazar.repository.IClienteRepository;
import com.ebernet.bazar.repository.IProductoRepository;
import com.ebernet.bazar.repository.IVentaRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService implements IVentaService{
    @Autowired
    private IVentaRepository ventarep;
    @Autowired
    private IProductoRepository produrep;
    @Autowired
    private IClienteRepository clienterep;

    @Override
    public void crearVenta(VentaDTO ventadto) {
        Venta v = new Venta();
        v.setFecha(ventadto.getFecha());
        v.setCliente(clienterep.findById(ventadto.getIdCliente()).orElse(null));
        List<Producto> prods = new ArrayList<Producto>();
        for(Long id : ventadto.getIdsProductos()){
            Producto p = produrep.findById(id).orElse(null);
            if (p.getCantidad() < 1) {
               throw new RuntimeException("No se puede realizar la venta. El producto " + p.getNombre() + " no tiene mas stock.");
            }
            if(p!=null){
                p.setCantidad(p.getCantidad()-1);
                produrep.save(p);
                prods.add(p);                
            }
        }
        v.setProductos(prods);
        ventarep.save(v);
    }

    @Override
    public List<Venta> listaVentas() {
        return ventarep.findAll();
    }

    @Override
    public Venta traerVenta(Long idVenta) {
        return ventarep.findById(idVenta).orElse(null);
    }

    @Override
    public void eliminarVenta(Long idVenta) {
        manejarStockEliminarVenta(idVenta);
        ventarep.deleteById(idVenta);
    }

    @Override
    public Venta editarVenta(Long idVenta, VentaDTO nuevaVenta) {
        Venta v = ventarep.findById(idVenta).orElse(null);
        if(v!=null){
           if(nuevaVenta.getFecha()!=null && nuevaVenta.getFecha()!=v.getFecha()){
               v.setFecha(nuevaVenta.getFecha());
           }
           if(nuevaVenta.getIdCliente()!=null && nuevaVenta.getIdCliente()!=v.getCliente().getId_cliente()){
               v.setCliente(clienterep.findById(nuevaVenta.getIdCliente()).orElse(null));
           }
           List<Producto> productosNuevos = new ArrayList<Producto>();
           for(Long id : nuevaVenta.getIdsProductos()){
               Producto p = produrep.findById(id).orElse(null);
               if(p!=null){
                   productosNuevos.add(p);
               }
           }
           List<Producto> productosEliminados = obtenerProductosEliminados(v.getProductos(), productosNuevos);
           List<Producto> productosAgregados = obtenerProductosAgregados(v.getProductos(), productosNuevos);
           manejarStock(productosEliminados, productosAgregados);           
            v.setProductos(productosNuevos);
            ventarep.save(v);
        }
        return v;
    }
    private void manejarStockEliminarVenta(Long idVenta){
        Venta v = ventarep.findById(idVenta).orElse(null);
        if(v!=null){
            for(Producto p : v.getProductos()){
                p.setCantidad(p.getCantidad()+1);
            }
            produrep.saveAll(v.getProductos());
        }
    }
    private void manejarStock(List<Producto> productosEliminados, List<Producto>productosAgregados){
        for(Producto p : productosEliminados){
            p.setCantidad(p.getCantidad()+1);
        }
        produrep.saveAll(productosEliminados);
        for(Producto p : productosAgregados){
            System.out.println("NOMBRE: "+p.getNombre()+"| CANTIDAD: "+p.getCantidad());
               if (p.getCantidad() < 1) {
                   System.out.println("CANTIDAD MENOR A 1 LANZANDO EXCEPCION");
                  throw new RuntimeException("No se puede realizar la venta. El producto " + p.getNombre() + " no tiene mas stock.");
               }
            p.setCantidad(p.getCantidad()-1); 
        }
        produrep.saveAll(productosAgregados);
    }
    private List<Producto> obtenerProductosAgregados(List<Producto> productosOriginales, List<Producto> productosNuevos) {
        Map<Producto, Integer> cantidadProductosOriginales = obtenerCantidadPorProducto(productosOriginales);
        Map<Producto, Integer> cantidadProductosNuevos = obtenerCantidadPorProducto(productosNuevos);

        List<Producto> productosAgregados = new ArrayList<>();

        for (Map.Entry<Producto, Integer> entry : cantidadProductosNuevos.entrySet()) {
            Producto producto = entry.getKey();
            Integer cantidadNueva = entry.getValue();
            Integer cantidadOriginal = cantidadProductosOriginales.getOrDefault(producto, 0);
            if (cantidadNueva > cantidadOriginal) {
                int cantidadAgregada = cantidadNueva - cantidadOriginal;
                for (int i = 0; i < cantidadAgregada; i++) {
                    productosAgregados.add(producto);
                }
            }
        }

        return productosAgregados;
    }
    
    private List<Producto> obtenerProductosEliminados(List<Producto> productosOriginales, List<Producto> productosNuevos) {
        Map<Producto, Integer> cantidadProductosOriginales = obtenerCantidadPorProducto(productosOriginales);
        Map<Producto, Integer> cantidadProductosNuevos = obtenerCantidadPorProducto(productosNuevos);

        List<Producto> productosEliminados = new ArrayList<>();

        for (Map.Entry<Producto, Integer> entry : cantidadProductosOriginales.entrySet()) {
            Producto producto = entry.getKey();
            Integer cantidadOriginal = entry.getValue();
            Integer cantidadNueva = cantidadProductosNuevos.getOrDefault(producto, 0);
            if (cantidadOriginal > cantidadNueva) {
                int cantidadEliminada = cantidadOriginal - cantidadNueva;
                for (int i = 0; i < cantidadEliminada; i++) {
                    productosEliminados.add(producto);
                }
            }
        }

        return productosEliminados;
    }

    private Map<Producto, Integer> obtenerCantidadPorProducto(List<Producto> productos) {
        Map<Producto, Integer> cantidadPorProducto = new HashMap<>();
        for (Producto producto : productos) {
            cantidadPorProducto.put(producto, cantidadPorProducto.getOrDefault(producto, 0) + 1);
        }
        return cantidadPorProducto;
    }

    @Override
    public ResumenVentasDiaDTO resumenDiarioDeVentas(LocalDate fecha) {
        List<Venta> ventas = ventarep.ventasPorFecha(fecha);
        ResumenVentasDiaDTO resumen = new ResumenVentasDiaDTO();
        resumen.setFecha(fecha);
        Double sumatoriaVentas = 0.0;
        for(Venta v : ventas){
            resumen.setCantidadDeProductos(resumen.getCantidadDeProductos()+v.getProductos().size());
            resumen.setCantidadDeVentas(resumen.getCantidadDeVentas()+1);
            for(Producto p : v.getProductos()){
                sumatoriaVentas+=p.getCosto();
            }
            resumen.setSumatoriaVentas(sumatoriaVentas);
        }
        return resumen;
    }

    @Override
    public VentaMayorDTO ventaMasAlta() {
        Venta v = ventarep.ObtenerVentaMaximoTotal();
        VentaMayorDTO ventaMayor = new VentaMayorDTO();
        ventaMayor.setCantidadProductos(v.getProductos().size());
        ventaMayor.setId_venta(v.getId_venta());
        ventaMayor.setFecha(v.getFecha());
        ventaMayor.setNombreYApellidoCliente(v.getCliente().getNombre()+" "+v.getCliente().getApellido());
        Double montoTotal = 0.0;
        for(Producto p : v.getProductos()){
            montoTotal+=p.getCosto();
        }
        ventaMayor.setMontoTotal(montoTotal);
        return ventaMayor;
    }
    
}
