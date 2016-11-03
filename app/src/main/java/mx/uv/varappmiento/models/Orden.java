package mx.uv.varappmiento.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orm.SugarRecord;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by willo on 27/07/2016.
 */
public class Orden extends SugarRecord implements Pojo {
    //private Long id;

    private String clase;
    private String orden;
    private Integer imagen_id;

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public Integer getImagen_id() {
        return imagen_id;
    }

    public void setImagen_id(Integer imagen_id) {
        this.imagen_id = imagen_id;
    }

    public static Orden fromJson(String json){
        Reader reader = new InputStreamReader(new ByteArrayInputStream(json.getBytes()));
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(reader,Orden.class);
    }
    public static String toJson(Orden orden)
    {
        Gson gson = new Gson();
        return gson.toJson(orden);

    }
    @Override
    public String toString()
    {
        return Orden.toJson(this);
    }

    public Imagen getImagen()
    {
        return Imagen.findById(Imagen.class,this.getImagen_id());
    }

    public ArrayList<Recomendacion> getRecomendaciones()
    {
        ArrayList<Recomendacion> lRecomendaciones = null;
        Iterator<Recomendacion> recomendaciones = Recomendacion.findAll(Recomendacion.class);
        lRecomendaciones = new ArrayList<Recomendacion>();
        while(recomendaciones.hasNext())
        {
            Recomendacion recomendacion = recomendaciones.next();
            if(recomendacion.getOrden_id() == this.getId().intValue())
            {
                lRecomendaciones.add(recomendacion);
            }
        }

        return lRecomendaciones;
    }
}
