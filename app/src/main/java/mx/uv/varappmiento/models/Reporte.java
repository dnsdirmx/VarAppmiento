package mx.uv.varappmiento.models;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by willo on 20/07/2016.
 */
public class Reporte extends SugarRecord implements Pojo{


    private Double latitud;
    private Double longitud;
    private Double precision;

    private Date creado;
    private String observaciones;

    private Informante informante;

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    //Integer id;


    public List<Especimen> getEspecimenes() {
        return Especimen.find(Especimen.class, "reporte = ?", String.valueOf(getId()));
    }
}
