package uniandes.dpoo.aerolinea.modelo;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Objects;

import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;

public class Vuelo {

    private String fecha; 
    private Ruta ruta;
    private Avion avion;

    private Collection<Tiquete> tiquetes = new ArrayList<>();

    public Vuelo(Ruta ruta, String fecha, Avion avion) {
        this.ruta = ruta; 
        this.fecha = fecha;
        this.avion = avion;
    }

    public Ruta getRuta() 
    {
        return ruta;
    }

    public String getFecha() 
    {
        return fecha;
    }

    public Avion getAvion() 
    {
        return avion;
    }

    public Collection<Tiquete> getTiquetes() 
    {
        return tiquetes;
    }

    public int venderTiquetes(Cliente cliente, CalculadoraTarifas calculadora, int cantidad) 
            throws VueloSobrevendidoException 
    {
        if (cantidad <= 0) {
            return 0;
        }

        int capacidad = avion.getCapacidad();
        int disponibles = capacidad - tiquetes.size();

        if (cantidad > disponibles) {
            throw new VueloSobrevendidoException("No hay cupos suficientes: solicitados " 
                    + cantidad + ", disponibles " + disponibles);
        }

        int total = 0;
        Vuelo vueloActual = this;   

        for (int i = 0; i < cantidad; i++) {
            int precio = calculadora.calcularTarifa(vueloActual, cliente);
            total += precio;

            Tiquete t = GeneradorTiquetes.generarTiquete(vueloActual, cliente, precio);
            GeneradorTiquetes.registrarTiquete(t);

            tiquetes.add(t);
            cliente.agregarTiquete(t);
        }
        return total;
    }


    public boolean equals(Object obj) 
    {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Vuelo)) {
            return false;
        }

        Vuelo otroVuelo = (Vuelo) obj;

        if (fecha == null || otroVuelo.fecha == null) {
            return false;
        }

        if (!fecha.equals(otroVuelo.fecha)) {
            return false;
        }

        if (ruta == null || otroVuelo.ruta == null) {
            return false;
        }

        if (!ruta.getCodigoRuta().equals(otroVuelo.ruta.getCodigoRuta())) {
            return false;
        }

        return true;
    }
    
}
