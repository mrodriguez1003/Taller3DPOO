package uniandes.dpoo.aerolinea.modelo;

import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;
import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;

public class Vuelo {
	
	private String fecha; 
	private Ruta ruta;
	private Avion avion;
	
	public Vuelo(Ruta ruta, String fecha, Avion avion) 
		{
		this.ruta = ruta; 
		this.fecha = fecha;
	    this.avion = avion;
	}
	
	public Ruta getRuta() {
        return ruta;
    }
	
	public String getFecha() {
        return fecha;
    }
	
	public Avion getAvion() {
        return avion;
    }
	
	public Collection<Tiquete> getTiquetes() {
        return tiquetes;
    }
	
	public int venderTiquetes(Cliente cliente, CalculadoraTarifas tarifa, int cantidad) 
	{
	    if (cantidad <= 0) {
	        return 0;
	    }
	    
	}
	    
	public boolean equals(Object obj) 
	{
	    if (obj == null || !(obj instanceof Vuelo)) {
	        return false;
	    }
	    
	    Vuelo otroVuelo = (Vuelo) obj;
	    
	    if (this.fecha.equals(otroVuelo.fecha) && this.ruta.getRuta().equals(otroVuelo.ruta.getRuta())) 
	    {
	        return true;
	    } 
	    else 
	    {
	        return false;
	    }
	}
}
