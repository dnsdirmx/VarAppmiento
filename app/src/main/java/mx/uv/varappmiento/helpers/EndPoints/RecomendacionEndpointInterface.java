package mx.uv.varappmiento.helpers.EndPoints;

import java.util.List;

import mx.uv.varappmiento.models.Recomendacion;
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
public interface RecomendacionEndpointInterface {

    @GET("recomendacion/")
    Call<List<Recomendacion>> getRecomendaciones();
    @GET("recomendacion/{id}")
    Call<Recomendacion> getRecomendacion(@Path("id") Integer id);
    @POST("recomendacion/")
    Call<Recomendacion> newRecomendacion(@Body Recomendacion recomendacion);
    @PUT("recomendacion/{id}")
    Call<Recomendacion> updateRecomendacion(@Path("id") Integer id, @Body Recomendacion recomendacion);
    @DELETE("recomendacion/{id}")
    Call<Response> deleteRecomendacion(@Path("id") Integer id);
}
