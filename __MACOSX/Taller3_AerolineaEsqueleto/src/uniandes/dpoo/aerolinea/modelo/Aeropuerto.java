package uniandes.dpoo.aerolinea.modelo;

public class Aeropuerto {
	
	private String nombre;
	private String codigo;
	private String nombreCiudad;
	private double latitud;
	private double longitud;
	
	private static final Set<String> codigosUtilizados = new HashSet<String>();
	private static final int RADIO_TERRESTRE = 6371;
	
	public Aeropuerto(String nombre, String codigo, String nombreCiudad, double latitud, double longitud) 
    {
        this.nombre = nombre;
        this.codigo = codigo;
        this.nombreCiudad = nombreCiudad;
        this.latitud = latitud;
        this.longitud = longitud;
    }
	
	 public String getNombre( )
	    {
	        return nombre;
	    }
	 
	 public String getCodigo( )
	    {
	        return codigo;
	    }
	 
	 public String getNombreCiudad( )
	    {
	        return nombreCiudad;
	    }
	 
	 public double getLatitud( )
	    {
	        return latitud;
	    }
	 
	 public double getLongitud( )
	    {
	        return longitud;
	    }
	 
	 public static final int calcularDistancia(Aeropuerto aeropuerto1, Aeropuerto aeropuerto2) 
	 {
		 	double lat1 = Math.toRadians(aeropuerto1.getLatitud());
	        double lon1 = Math.toRadians(aeropuerto1.getLongitud());

	        double lat2 = Math.toRadians(aeropuerto2.getLatitud());
	        double lon2 = Math.toRadians(aeropuerto2.getLongitud());

	        double deltaX = (lon2 - lon1) * Math.cos((lat1 + lat2) / 2.0);
	        double deltaY = (lat2 - lat1);

	        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY) * RADIO_TERRESTRE;
	        return (int) Math.round(distancia);
	 }
}

