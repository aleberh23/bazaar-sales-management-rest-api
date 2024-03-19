package com.ebernet.bazar.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaMayorDTO {
    private Long id_venta;
    private Double montoTotal;
    private int cantidadProductos;
    private LocalDate fecha;
    private String nombreYApellidoCliente;
    
}
