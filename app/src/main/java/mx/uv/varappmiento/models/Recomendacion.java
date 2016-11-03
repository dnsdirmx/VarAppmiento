package mx.uv.varappmiento.models;

import com.orm.SugarRecord;

/**
 * Created by willo on 15/08/2016.
 */
public class Recomendacion extends SugarRecord implements Pojo {

    // private Integer Id;

    private String descripcion;
    private Integer orden_id;
    private Integer imagen_id;

    public Integer getImagen_id() {
        return imagen_id;
    }

    public void setImagen_id(Integer imagen_id) {
        this.imagen_id = imagen_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden_id() {
        return orden_id;
    }

    public void setOrden_id(Integer orden_id) {
        this.orden_id = orden_id;
    }

    public Imagen getImagen()
    {
        return Imagen.findById(Imagen.class,this.getImagen_id());
    }
}
