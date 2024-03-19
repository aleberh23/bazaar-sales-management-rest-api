package com.ebernet.bazar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_venta;
    private LocalDate fecha;
    @ManyToMany
    @JoinTable(name = "venta_producto",
               joinColumns = @JoinColumn(name = "id_venta"),
               inverseJoinColumns = @JoinColumn(name = "id_prod"))
    private List<Producto> productos;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
