package com.ebernet.bazar.repository;

import com.ebernet.bazar.model.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long>{
    @Query("SELECT p FROM Producto p WHERE p.cantidad < 5")
    public List<Producto> traerProductosConCantidadMenorA5();
}
