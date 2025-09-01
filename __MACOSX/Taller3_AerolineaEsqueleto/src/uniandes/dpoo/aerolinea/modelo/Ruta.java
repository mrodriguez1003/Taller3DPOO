package uniandes.dpoo.aerolinea.modelo;

public class Ruta {
	
	private String horaSalida;
	private String horaLlegada; 
	private String codigoRuta;
	
	private Aeropuerto origen;   
    private Aeropuerto destino;
	
	 public Ruta( Aeropuerto origen, Aeropuerto destino, String horaSalida, String horaLlegada, String codigoRuta)
	    {
	        
	        this.origen = origen;
	        this.destino = destino;
	        this.horaSalida = horaSalida;
	        this.horaLlegada = horaLlegada;
	        this.codigoRuta = codigoRuta;
	    }
	 
	 public String getCodigoRuta( )
	    {
	        return codigoRuta;
	    }

	 public Aeropuerto getOrigen( )
	    {
	        return origen;
	    }

	 public Aeropuerto getDestino( )
	    {
	        return destino;
	    }

	 public String getHoraSalida( )
	    {
	        return horaSalida;
	    }

	 public String getHoraLlegada( )
	    {
	        return horaLlegada;
	    }
	 
	 public int getDuracion() {
	        int minutosInicio = getMinutos(horaSalida);
	        int minutosLlegada = getMinutos(horaLlegada);
	        
	        int duracion = minutosLlegada - minutosInicio;
	        if (duracion < 0) {
	        	duracion += 24 * 60; 
	        }
	        
	        return duracion;
	    }
	 
	 public int getMinutos(String horaCompleta)
	    {
		 String[] partes = horaCompleta.split(":");
	        int horas = Integer.parseInt(partes[0]);
	        int minutos = Integer.parseInt(partes[1]);
	        return horas * 60 + minutos;
	    }
	 
	 public int getHoras(String horaCompleta)
	    {
		 String[] partes = horaCompleta.split(":");
	       	return Integer.parseInt(partes[0]);
	    }
}
