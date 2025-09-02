package uniandes.dpoo.aerolinea.tiquetes

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public class Tiquete {
	
	 	private final String codigo;      
	    private boolean usado;        
	 
	    public Tiquete(String codigo, Vuelo vuelo, Cliente clienteComprador, int tarifa) {
	        this.codigo = codigo;
	        this.vuelo = vuelo;
	        this.clienteComprador = cliente;
	        this.tarifa = tarifa;
	    }
	    
	    public Cliente getCliente() {
	        return cliente;
	    }
	    
	    public Vuelo getVuelo() {
	        return vuelo;
	    }
	    
	    public String getCodigo() {
	        return codigo;
	    }
	    
	    public int getTarifa() {
	        return tarifa;
	    }
	    
	    public void marcarUsado() {
	        this.usado = true;
	    }
	    
	    public boolean esUsado() {
	        return usado;
	    }

}
