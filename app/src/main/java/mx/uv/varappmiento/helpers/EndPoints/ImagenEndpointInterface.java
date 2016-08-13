package mx.uv.varappmiento.helpers.EndPoints;

import java.util.List;

import mx.uv.varappmiento.models.Imagen;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by willo on 27/06/2016.
 */
public interface ImagenEndpointInterface {
    @GET("imagen/")
    Call<List<Imagen>> getImagenes();
    @GET("imagen/{id}")
    Call<Imagen> getImagen(@Header("Authorization") String authorization, @Path("id") Integer id);
    @POST("imagen/")
    Call<Imagen> newImagen(@Header("Authorization") String authorization, @Body Imagen imagen);
    @PUT("imagen/{id}")
    Call<Imagen> updateImagen(@Header("Authorization") String authorization, @Path("id") Integer id, @Body Imagen imagen);
    @DELETE("imagen/{id}")
    Call<Response> deleteImagen(@Header("Authorization") String authorization,@Path("id") Integer id);
    @POST("imagen/{id}/file")
    Call<Imagen> uploadImagen(@Header("Authorization") String authorization, @Path("id") Integer id, @Part("imagen") RequestBody imagen);
    @GET("imagen/{id}/file")
    Call<ResponseBody> downloadImagen(@Header("Authorization") String authorization, @Path("id") Integer id);
}
