package mx.uv.varappmiento.models;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willo on 06/09/2016.
 */
public class Especimen extends SugarRecord implements Pojo{
    Integer id_imagen;
    Reporte reporte;

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    Especie especie;

    public List<Imagen> getImagenes()
    {
        List<Imagen> listaImagenes = new ArrayList<Imagen>();
        List<Especimen_Imagen> lista = Especimen_Imagen.find(Especimen_Imagen.class, "especimen = ?", String.valueOf(getId()));
        for (Especimen_Imagen esp_img: lista) {
            listaImagenes.add(esp_img.getImagen());
        }
        return listaImagenes;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
}
}
