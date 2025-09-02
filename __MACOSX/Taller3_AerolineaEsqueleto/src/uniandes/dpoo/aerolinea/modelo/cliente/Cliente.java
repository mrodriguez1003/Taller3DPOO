package uniandes.dpoo.aerolinea.modelo.cliente;

import java.util.ArrayList;
import java.util.List;

import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public abstract class Cliente
{
    protected List<Tiquete> tiquetes;

    public Cliente() {
        this.tiquetes = new ArrayList<>();
    }

    public String getTipoCliente();
    public String getIdentificador();

    public void agregarTiquete(Tiquete tiquete) {
        if (tiquete != null) {
            tiquetes.add(tiquete);
        }
    }

    public int calcularValorTotalTiquetes() {
        int total = 0;
        for (Tiquete t : tiquetes) {
            total += t.getTarifa();  
        }
        return total;
    }

    public void usarTiquetes(Vuelo vuelo) {
        for (Tiquete t : tiquetes) {
            if (t.getVuelo().equals(vuelo)) {
                t.marcarComoUsado(); 
            }
        }
    }
}
