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
public class ResumenVentasDiaDTO {
    private LocalDate fecha;
    private int cantidadDeVentas;
    private int cantidadDeProductos;
    private Double sumatoriaVentas;
}
