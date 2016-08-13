package mx.uv.varappmiento.controllers;

import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import mx.uv.varappmiento.helpers.Callbacks.VarAppiCallback;
import mx.uv.varappmiento.helpers.EndPoints.ImagenEndpointInterface;
import mx.uv.varappmiento.helpers.EndPoints.InformanteEndpointInterface;
import mx.uv.varappmiento.helpers.EndPoints.OrdenEndpointInterface;
import mx.uv.varappmiento.helpers.VarAppiConsumer;
import mx.uv.varappmiento.models.Imagen;
import mx.uv.varappmiento.models.Informante;
import mx.uv.varappmiento.models.Orden;
import mx.uv.varappmiento.models.Pojo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by willo on 09/08/2016.
 */
public class OrdenesController extends Controller {
    private static OrdenesController ourInstance = new OrdenesController();
    public static OrdenesController getInstance() {
        return ourInstance;
    }
    private OrdenesController(){}

    private static <T> Iterable<T> iterable(final Iterator<T> it){
        return new Iterable<T>(){ public Iterator<T> iterator(){ return it; } };
    }


    public void syncOrdenes()
    {
        VarAppiConsumer vapc = VarAppiConsumer.getInstance();
        OrdenEndpointInterface service = vapc.getRetrofit().create(OrdenEndpointInterface.class);
        Call<List<Orden>> callOrdenes = service.getOrdenes();
        final VarAppiCallback imagenCallblack = new VarAppiCallback() {
            @Override
            public void onResult(int status, Pojo object) {
                Imagen imagen = (Imagen) object;
                imagen.save();
            }

            @Override
            public void onFailure(int status, String message) {
                Toast.makeText(MainController.getInstance().getContext(),message,Toast.LENGTH_SHORT);
            }
        };
        Callback ordenesCallback = new Callback<List<Orden>>() {
            @Override
            public void onResponse(Call<List<Orden>> call, Response<List<Orden>> response) {
                Iterator<Orden> ordenes;
                for(Orden orden : iterable(Orden.findAll(Orden.class)))
                {
                    orden.delete();
                }
                for (Orden orden: response.body()) {
                    orden.save();
                    ImagenController.getInstance().getImagen(orden.getImagen_id(), imagenCallblack);
                }

                //ciclo para bajar las imagenes de cada orden
            }

            @Override
            public void onFailure(Call<List<Orden>> call, Throwable t) {
                Toast.makeText(MainController.getInstance().getContext(),"No se ha podido descargar el catalogo de ordenes",Toast.LENGTH_LONG).show();
            }
        };

        callOrdenes.enqueue(ordenesCallback);


    }

}
