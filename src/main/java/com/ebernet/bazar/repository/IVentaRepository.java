package com.ebernet.bazar.repository;

import com.ebernet.bazar.model.Venta;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long>{
    @Query("SELECT v FROM Venta v WHERE v.fecha = ?1")
    public List<Venta> ventasPorFecha(LocalDate fecha);
   @Query("SELECT v " +
           "FROM Venta v " +
           "WHERE (SELECT SUM(pr.costo) FROM v.productos pr) = " +
           "(SELECT MAX(totalCost) FROM (SELECT SUM(pr2.costo) AS totalCost FROM Venta v2 JOIN v2.productos pr2 GROUP BY v2))")
    public Venta ObtenerVentaMaximoTotal();
    
}
