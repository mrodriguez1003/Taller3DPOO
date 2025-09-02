package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteNatural;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas {

    protected int COSTO_POR_KM_NATURAL      = 600;
    protected int COSTO_POR_KM_CORPORATIVO  = 900;

    protected double DESCUENTO_PEQ          = 0.02;
    protected double DESCUENTO_MEDIANAS     = 0.10;
    protected double DESCUENTO_GRANDES      = 0.20;

    public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
        Ruta ruta = vuelo.getRuta();                
        int distancia = calcularDistanciaVuelo(ruta); 
        if (cliente instanceof ClienteNatural) {
            return distancia * COSTO_POR_KM_NATURAL;
        } else {
            return distancia * COSTO_POR_KM_CORPORATIVO;
        }
    }

    public double calcularPorcentajeDescuento(Cliente cliente) {
        if (cliente instanceof ClienteCorporativo cc) {
            int tam = cc.getTamanoEmpresa(); 
            if (tam == 3) return DESCUENTO_PEQ;
            if (tam == 2) return DESCUENTO_MEDIANAS;
            if (tam == 1) return DESCUENTO_GRANDES;
        }
        return 0.0; 
    }
}
