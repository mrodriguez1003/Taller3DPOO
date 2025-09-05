package uniandes.dpoo.aerolinea.persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public class PersistenciaAerolineaJson implements IPersistenciaAerolinea
{

    private static final String AVIONES       = "aviones";
    private static final String AEROPUERTOS   = "aeropuertos";
    private static final String RUTAS         = "rutas";
    private static final String VUELOS        = "vuelos";

    private static final String NOMBRE        = "nombre";
    private static final String CAPACIDAD     = "capacidad";

    private static final String CODIGO        = "codigo";
    private static final String CIUDAD        = "ciudad";
    private static final String LATITUD       = "latitud";
    private static final String LONGITUD      = "longitud";

    private static final String CODIGO_RUTA   = "codigoRuta";
    private static final String ORIGEN        = "origen";
    private static final String DESTINO       = "destino";
    private static final String HORA_SALIDA   = "horaSalida";
    private static final String HORA_LLEGADA  = "horaLlegada";

    private static final String FECHA         = "fecha";
    private static final String NOMBRE_AVION  = "nombreAvion";

    @Override
    public void cargarAerolinea( String archivo, Aerolinea aerolinea )
            throws IOException, InformacionInconsistenteException
    {
        String contenido = new String(Files.readAllBytes(new File(archivo).toPath()));
        JSONObject raiz = new JSONObject(contenido);

        Map<String, Avion> avionesPorNombre = new HashMap<String, Avion>();
        Map<String, Aeropuerto> aeropuertosPorCodigo = new HashMap<String, Aeropuerto>();
        Map<String, Ruta> rutasPorCodigo = new HashMap<String, Ruta>();

        if (raiz.has(AVIONES)) {
            JSONArray jAviones = raiz.getJSONArray(AVIONES);
            for (int i = 0; i < jAviones.length(); i++) {
                JSONObject ja = jAviones.getJSONObject(i);
                String nombre = ja.getString(NOMBRE);
                int capacidad = ja.getInt(CAPACIDAD);

                Avion avion = new Avion(nombre, capacidad);
                aerolinea.agregarAvion(avion);
                avionesPorNombre.put(nombre, avion);
            }
        }

        if (raiz.has(AEROPUERTOS)) {
            JSONArray jAeros = raiz.getJSONArray(AEROPUERTOS);
            for (int i = 0; i < jAeros.length(); i++) {
                JSONObject jo = jAeros.getJSONObject(i);
                String codigo = jo.getString(CODIGO);
                String nombre = jo.getString(NOMBRE);
                String ciudad = jo.getString(CIUDAD);
                double lat = jo.getDouble(LATITUD);
                double lon = jo.getDouble(LONGITUD);

                Aeropuerto ap = new Aeropuerto(nombre, codigo, ciudad, lat, lon);
                aeropuertosPorCodigo.put(codigo, ap);
            }
        }

        if (raiz.has(RUTAS)) {
            JSONArray jRutas = raiz.getJSONArray(RUTAS);
            for (int i = 0; i < jRutas.length(); i++) {
                JSONObject jr = jRutas.getJSONObject(i);
                String codigoRuta = jr.getString(CODIGO_RUTA);
                String codOrigen  = jr.getString(ORIGEN);
                String codDestino = jr.getString(DESTINO);
                String horaSalida = jr.getString(HORA_SALIDA);
                String horaLlegada= jr.getString(HORA_LLEGADA);

                Aeropuerto origen = aeropuertosPorCodigo.get(codOrigen);
                Aeropuerto destino = aeropuertosPorCodigo.get(codDestino);

                if (origen == null || destino == null) {
                    throw new InformacionInconsistenteException(
                        "Ruta " + codigoRuta + " referencia aeropuertos inexistentes: " +
                        "origen=" + codOrigen + ", destino=" + codDestino);
                }

                Ruta ruta = new Ruta(origen, destino, horaSalida, horaLlegada, codigoRuta);
                aerolinea.agregarRuta(ruta);
                rutasPorCodigo.put(codigoRuta, ruta);
            }
        }

        if (raiz.has(VUELOS)) {
            JSONArray jVuelos = raiz.getJSONArray(VUELOS);
            for (int i = 0; i < jVuelos.length(); i++) {
                JSONObject jv = jVuelos.getJSONObject(i);
                String fecha = jv.getString(FECHA);
                String codigoRuta = jv.getString(CODIGO_RUTA);
                String nombreAvion = jv.getString(NOMBRE_AVION);

                Ruta ruta = rutasPorCodigo.get(codigoRuta);
                Avion avion = avionesPorNombre.get(nombreAvion);

                if (ruta == null) {
                    throw new InformacionInconsistenteException(
                        "Vuelo " + fecha + " referencia ruta inexistente: " + codigoRuta);
                }
                if (avion == null) {
                    throw new InformacionInconsistenteException(
                        "Vuelo " + fecha + " referencia avión inexistente: " + nombreAvion);
                }
                aerolinea.getVuelos().add(new Vuelo(ruta, fecha, avion));
            }
        }
    }

    @Override
    public void salvarAerolinea( String archivo, Aerolinea aerolinea ) throws IOException
    {
        JSONObject raiz = new JSONObject();

        JSONArray jAviones = new JSONArray();
        for (Avion a : aerolinea.getAviones()) {
            JSONObject ja = new JSONObject();
            ja.put(NOMBRE, a.getNombre());
            ja.put(CAPACIDAD, a.getCapacidad());
            jAviones.put(ja);
        }
        raiz.put(AVIONES, jAviones);

        Map<String, JSONObject> aeroUnicos = new HashMap<String, JSONObject>();
        for (Ruta r : aerolinea.getRutas()) {
            Aeropuerto o = r.getOrigen();
            Aeropuerto d = r.getDestino();

            if (!aeroUnicos.containsKey(o.getCodigo())) {
                JSONObject jo = new JSONObject();
                jo.put(CODIGO, o.getCodigo());
                jo.put(NOMBRE, o.getNombre());
                jo.put(CIUDAD, o.getNombreCiudad());
                jo.put(LATITUD, o.getLatitud());
                jo.put(LONGITUD, o.getLongitud());
                aeroUnicos.put(o.getCodigo(), jo);
            }
            if (!aeroUnicos.containsKey(d.getCodigo())) {
                JSONObject jd = new JSONObject();
                jd.put(CODIGO, d.getCodigo());
                jd.put(NOMBRE, d.getNombre());
                jd.put(CIUDAD, d.getNombreCiudad());
                jd.put(LATITUD, d.getLatitud());
                jd.put(LONGITUD, d.getLongitud());
                aeroUnicos.put(d.getCodigo(), jd);
            }
        }
        JSONArray jAeros = new JSONArray();
        for (JSONObject jo : aeroUnicos.values()) {
            jAeros.put(jo);
        }
        raiz.put(AEROPUERTOS, jAeros);

        JSONArray jRutas = new JSONArray();
        for (Ruta r : aerolinea.getRutas()) {
            JSONObject jr = new JSONObject();
            jr.put(CODIGO_RUTA, r.getCodigoRuta());
            jr.put(ORIGEN, r.getOrigen().getCodigo());
            jr.put(DESTINO, r.getDestino().getCodigo());
            jr.put(HORA_SALIDA, r.getHoraSalida());
            jr.put(HORA_LLEGADA, r.getHoraLlegada());
            jRutas.put(jr);
        }
        raiz.put(RUTAS, jRutas);

        JSONArray jVuelos = new JSONArray();
        for (Vuelo v : aerolinea.getVuelos()) {
            JSONObject jv = new JSONObject();
            jv.put(FECHA, v.getFecha());
            jv.put(CODIGO_RUTA, v.getRuta().getCodigoRuta());
            jv.put(NOMBRE_AVION, v.getAvion().getNombre());
            jVuelos.put(jv);
        }
        raiz.put(VUELOS, jVuelos);

        PrintWriter pw = new PrintWriter(archivo);
        raiz.write(pw, 2, 0);
        pw.close();
    }
}
