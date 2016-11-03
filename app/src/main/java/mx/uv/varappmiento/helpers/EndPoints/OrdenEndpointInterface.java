package mx.uv.varappmiento.helpers.EndPoints;

import java.util.List;

import mx.uv.varappmiento.models.Orden;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by willo on 27/06/2016.
 */
public interface OrdenEndpointInterface {
    @GET("orden/")
    Call<List<Orden>> getOrdenes();
    @GET("orden/{id}")
    Call<Orden> getOrden(@Path("id") Integer id);
    @POST("orden/")
    Call<Orden> newOrden(@Body Orden orden);
    @PUT("orden/{id}")
    Call<Orden> updateOrden(@Path("id") Integer id, @Body Orden orden);
    @DELETE("orden/{id}")
    Call<Response> deleteOrden(@Path("id") Integer id);
}
