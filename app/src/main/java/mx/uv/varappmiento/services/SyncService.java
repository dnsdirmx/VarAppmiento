package mx.uv.varappmiento.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.orm.SugarContext;

import java.util.Iterator;
import java.util.List;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.controllers.ImagenController;
import mx.uv.varappmiento.controllers.InformantesController;
import mx.uv.varappmiento.controllers.MainController;
import mx.uv.varappmiento.controllers.OrdenesController;
import mx.uv.varappmiento.helpers.Callbacks.VarAppiCallback;
import mx.uv.varappmiento.helpers.EndPoints.ImagenEndpointInterface;
import mx.uv.varappmiento.helpers.EndPoints.OrdenEndpointInterface;
import mx.uv.varappmiento.helpers.EndPoints.RecomendacionEndpointInterface;
import mx.uv.varappmiento.helpers.VarAppiConsumer;
import mx.uv.varappmiento.models.Imagen;
import mx.uv.varappmiento.models.Orden;
import mx.uv.varappmiento.models.Pojo;
import mx.uv.varappmiento.models.Recomendacion;
import mx.uv.varappmiento.views.PrincipalActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by willo on 10/08/2016.
 */
public class SyncService extends IntentService{

    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //metodo de sincnonizacion
        Log.d("SERVICE","Lanzando metodo para sincronizzar ordenes");
        SugarContext.init(MainController.getInstance().getContext());
        syncOrdenes();





        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_add_a_photo_white_48dp)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, PrincipalActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(PrincipalActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());

    }
    private static <T> Iterable<T> iterable(final Iterator<T> it){
        return new Iterable<T>(){ public Iterator<T> iterator(){ return it; } };
    }
    public void SyncRecomendaciones()
    {
        Log.d("SERVICE","metodo recomendaoiones sinc");
        VarAppiConsumer vapc = VarAppiConsumer.getInstance();
        RecomendacionEndpointInterface service = vapc.getRetrofit().create(RecomendacionEndpointInterface.class);
        Call<List<Recomendacion>> callRecomendaciones = service.getRecomendaciones();

        final VarAppiCallback imagenCallblack = new VarAppiCallback() {
            @Override
            public void onResult(int status, Pojo object) {
                Log.d("SERVICE","imagen callback result");
                Imagen imagen = (Imagen) object;
                imagen.save();
                Toast.makeText(MainController.getInstance().getContext(),"Almacenada imagen: " + imagen.getId() ,Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(int status, String message) {
                Log.d("SERVICE","imagen callback failure");
                Toast.makeText(MainController.getInstance().getContext(),message,Toast.LENGTH_SHORT);
            }
        };
        Callback recomendacionCallback = new Callback<List<Recomendacion>>() {
            @Override
            public void onResponse(Call<List<Recomendacion>> call, Response<List<Recomendacion>> response) {
                    Log.d("SERVICE","recomendaciones callback");
                    Iterator<Orden> ordenes;
                    for(Recomendacion recomendacion : iterable(Recomendacion.findAll(Recomendacion.class)))
                    {
                        recomendacion.delete();
                    }
                    for (Recomendacion recomendacion: response.body()) {
                        Log.d("SERVICE","obteniendo imagen de recomendacion");
                        recomendacion.save();
                        getImagen(recomendacion.getImagen_id(), imagenCallblack);
                    }
                    //ciclo para bajar las imagenes de cada orden
            }

            @Override
            public void onFailure(Call<List<Recomendacion>> call, Throwable t) {
                Toast.makeText(MainController.getInstance().getContext(),"No se ha podido descargar el catalogo de recomendaciones",Toast.LENGTH_LONG).show();
            }
        };

        callRecomendaciones.enqueue(recomendacionCallback);

    }
    public void syncOrdenes()
    {
        Log.d("SERVICE","metodo ordenes sinc");
        VarAppiConsumer vapc = VarAppiConsumer.getInstance();
        OrdenEndpointInterface service = vapc.getRetrofit().create(OrdenEndpointInterface.class);
        Call<List<Orden>> callOrdenes = service.getOrdenes();

        final VarAppiCallback imagenCallblack = new VarAppiCallback() {
            @Override
            public void onResult(int status, Pojo object) {
                Log.d("SERVICE","imagen callback result");
                Imagen imagen = (Imagen) object;
                imagen.save();
                Toast.makeText(MainController.getInstance().getContext(),"Almacenada imagen: " + imagen.getId() ,Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(int status, String message) {
                Log.d("SERVICE","imagen callback failure");
                Toast.makeText(MainController.getInstance().getContext(),message,Toast.LENGTH_SHORT);
            }
        };

        Callback ordenesCallback = new Callback<List<Orden>>() {
            @Override
            public void onResponse(Call<List<Orden>> call, Response<List<Orden>> response) {
                Log.d("SERVICE","ordenes callback");
                Iterator<Orden> ordenes;
                for(Orden orden : iterable(Orden.findAll(Orden.class)))
                {
                    orden.delete();
                }
                for (Orden orden: response.body()) {
                    Log.d("SERVICE","obteniendo imagen de orden");
                    orden.save();
                    getImagen(orden.getImagen_id(), imagenCallblack);
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

    public void getImagen(final Integer id, final VarAppiCallback callback)
    {
        Log.d("SERVICE","imagen getImagen" + id);

        VarAppiConsumer vapc = VarAppiConsumer.getInstance();
        ImagenEndpointInterface service = vapc.getRetrofit().create(ImagenEndpointInterface.class);

        Call<Imagen> callImagen = service.getImagen(InformantesController.getInstance().getActive().getApi_android_token(), id);

        final VarAppiCallback downloadCallback = new VarAppiCallback() {
            @Override
            public void onResult(int status, Pojo object) {
                Toast.makeText(MainController.getInstance().getContext(),"Descargada imagen. status : " + status,Toast.LENGTH_SHORT).show();
                Log.d("SERVICE","imagen  downloadcallback result: " + id);
                callback.onResult(status,object);
            }

            @Override
            public void onFailure(int status, String message) {
                Log.d("SERVICE","imagen download callback failed: " + id);
                callback.onFailure(status,message);
            }
        };

        callImagen.enqueue(new Callback<Imagen>() {
            @Override
            public void onResponse(Call<Imagen> call, Response<Imagen> response) {
                response.body().save();
                Log.d("SERVICE","imagen obtiene json imagen -- obteniendo archivo imagen: " + id);
                downloadImage(response.body().getId().intValue(),downloadCallback);
            }

            @Override
            public void onFailure(Call<Imagen> call, Throwable t) {
                Log.d("SERVICE","imagen callback faoilure al bajar json imagen: " + id + " " + t.getMessage());
                callback.onFailure(ImagenController.IMAGE_INFO_NOT_FOUND,"No se pudo descargar la informacion de la imagen");
                t.printStackTrace();
            }
        });
    }
    public void downloadImage(final Integer id, final VarAppiCallback callback)
    {
        Log.d("SERVICE","metodo dowwnload imagen: "+ id);
        VarAppiConsumer vapc = VarAppiConsumer.getInstance();
        ImagenEndpointInterface service = vapc.getRetrofit().create(ImagenEndpointInterface.class);
        Call<ResponseBody> downloadCallback = service.downloadImagen(InformantesController.getInstance().getActive().getApi_android_token(), id);

        downloadCallback.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("SERVICE","obtieene el archivo de la imagen: " + id);

                Imagen imagen = Imagen.findById(Imagen.class,id);
                imagen.setFile(response.body().byteStream(),response.body().contentLength());
                imagen.save();

                callback.onResult(ImagenController.FILE_SAVED,imagen);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("SERVICE","doanloadiamgen fallo: " + id + " " + t.getMessage());
                callback.onFailure(ImagenController.FILE_NOT_FOUND,"No se pudo descargar la imagen");
                t.printStackTrace();
            }
        });
    }
}
