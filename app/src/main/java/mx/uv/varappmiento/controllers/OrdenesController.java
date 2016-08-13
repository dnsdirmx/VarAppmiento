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
    private static final int FILE_NOT_FOUND = 10;
    private static final int FILE_SAVED = 11;
    private static final int IMAGE_INFO_NOT_FOUND = 12;
    private static OrdenesController ourInstance = new OrdenesController();
    public static OrdenesController getInstance() {
        return ourInstance;
    }
    private OrdenesController(){}

    private static <T> Iterable<T> iterable(final Iterator<T> it){
        return new Iterable<T>(){ public Iterator<T> iterator(){ return it; } };
    }
    public void downloadImage(final Integer id, final VarAppiCallback callback)
    {
        VarAppiConsumer vapc = VarAppiConsumer.getInstance();
        ImagenEndpointInterface service = vapc.getRetrofit().create(ImagenEndpointInterface.class);
        Call<ResponseBody> downloadCallback = service.downloadImagen(InformantesController.getInstance().getActive().getApi_android_token(), id);
        downloadCallback.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Imagen imagen = Imagen.findById(Imagen.class,id);
                imagen.setFile(response.body().byteStream(),response.body().contentLength());
                imagen.save();
                callback.onResult(OrdenesController.FILE_SAVED,imagen);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(OrdenesController.FILE_NOT_FOUND,"No se pudo descargar la imagen");
            }
        });
    }
    public void getImagen(Integer id, final VarAppiCallback callback)
    {
        VarAppiConsumer vapc = VarAppiConsumer.getInstance();
        ImagenEndpointInterface service = vapc.getRetrofit().create(ImagenEndpointInterface.class);
        Call<Imagen> callImagen = service.getImagen(InformantesController.getInstance().getActive().getApi_android_token(), id);
        final VarAppiCallback downloadCallback = new VarAppiCallback() {
            @Override
            public void onResult(int status, Pojo object) {
                callback.onResult(status,object);
            }

            @Override
            public void onFailure(int status, String message) {
                callback.onFailure(status,message);
            }
        };
        
        callImagen.enqueue(new Callback<Imagen>() {
            @Override
            public void onResponse(Call<Imagen> call, Response<Imagen> response) {
                response.body().save();
                downloadImage(response.body().getId().intValue(),downloadCallback);
            }

            @Override
            public void onFailure(Call<Imagen> call, Throwable t) {
                callback.onFailure(OrdenesController.IMAGE_INFO_NOT_FOUND,"No se pudo descargar la informacion de la imagen");
            }
        });
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
                    getImagen(orden.getImagen_id(), imagenCallblack);
                }

                //ciclo para bajar las imagenes de cada orden
            }

            @Override
            public void onFailure(Call<List<Orden>> call, Throwable t) {
                //TODO mostrar msj de error
            }
        };

        callOrdenes.enqueue(ordenesCallback);


    }

}
