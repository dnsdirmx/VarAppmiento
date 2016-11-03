package mx.uv.varappmiento.controllers;

import android.util.Log;
import android.widget.Toast;

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
}
