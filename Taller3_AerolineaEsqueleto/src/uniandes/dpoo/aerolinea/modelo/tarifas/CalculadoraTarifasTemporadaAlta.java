package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public class CalculadoraTarifasTemporadaAlta extends CalculadoraTarifas {

    public final int COSTO_POR_KM = 1000;

    public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
        Ruta ruta = vuelo.getRuta(); 
        int distancia = calcularDistanciaVuelo(ruta);
        return distancia * COSTO_POR_KM;
    }

    public double calcularPorcentajeDescuento(Cliente cliente) {
        return 0.0;
    }
}
