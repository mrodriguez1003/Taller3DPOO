package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteNatural;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas
{

    protected static final int    COSTO_POR_KM_NATURAL     = 600;
    protected static final int    COSTO_POR_KM_CORPORATIVO = 900;
    protected static final double DESCUENTO_PEQ            = 0.02; 
    protected static final double DESCUENTO_MEDIANAS       = 0.10; 
    protected static final double DESCUENTO_GRANDES        = 0.20; 

    @Override
    protected int calcularCostoBase(Vuelo vuelo, Cliente cliente)
    {
        int distancia = calcularDistanciaVuelo(vuelo.getRuta());
        int costoKm;
        if (cliente instanceof ClienteNatural) {
            costoKm = COSTO_POR_KM_NATURAL;
        } else {
            costoKm = COSTO_POR_KM_CORPORATIVO;
        }
        return distancia * costoKm;
    }

    @Override
    protected double calcularPorcentajeDescuento(Cliente cliente)
    {
        if (cliente instanceof ClienteCorporativo) {
            ClienteCorporativo cc = (ClienteCorporativo) cliente;
            int tam = cc.getTamanoEmpresa();
            if (tam == 1) return DESCUENTO_GRANDES;
            if (tam == 2) return DESCUENTO_MEDIANAS;
            if (tam == 3) return DESCUENTO_PEQ;
        }
        return 0.0;
    }
}
