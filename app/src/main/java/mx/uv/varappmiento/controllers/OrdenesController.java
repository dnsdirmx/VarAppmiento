package mx.uv.varappmiento.controllers;

import android.util.Log;
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





}
