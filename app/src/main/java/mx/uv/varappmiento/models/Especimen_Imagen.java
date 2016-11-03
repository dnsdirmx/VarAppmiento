package mx.uv.varappmiento.models;

import com.orm.SugarRecord;

/**
 * Created by willo on 06/09/2016.
 */
public class Especimen_Imagen extends SugarRecord {

    public Especimen getEspecimen() {
        return especimen;
    }

    public void setEspecimen(Especimen especimen) {
        this.especimen = especimen;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    Especimen especimen;
    Imagen imagen;
}
