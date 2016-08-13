package mx.uv.varappmiento.controllers;

import mx.uv.varappmiento.helpers.Callbacks.VarAppiCallback;
import mx.uv.varappmiento.helpers.EndPoints.ImagenEndpointInterface;
import mx.uv.varappmiento.helpers.VarAppiConsumer;
import mx.uv.varappmiento.models.Imagen;
import mx.uv.varappmiento.models.Pojo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dns on 13/08/16.
 */
public class ImagenController extends Controller {
    public static final int FILE_NOT_FOUND = 10;
    public static final int FILE_SAVED = 11;
    public static final int IMAGE_INFO_NOT_FOUND = 12;

    private static ImagenController ourInstance = new ImagenController();
    public static ImagenController getInstance() {
        return ourInstance;
    }
    private ImagenController(){}

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
                callback.onFailure(ImagenController.IMAGE_INFO_NOT_FOUND,"No se pudo descargar la informacion de la imagen");
            }
        });
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
                callback.onResult(ImagenController.FILE_SAVED,imagen);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(ImagenController.FILE_NOT_FOUND,"No se pudo descargar la imagen");
            }
        });
    }
}
