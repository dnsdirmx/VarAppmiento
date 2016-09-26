package mx.uv.varappmiento.helpers.EndPoints;


import java.util.List;

import mx.uv.varappmiento.models.Informante;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by willo on 27/06/2016.
 */
public interface InformanteEndpointInterface {
    @GET("informante/logout")
    Call<ResponseBody> logoutInformante(@Header("Authorization") String authorization);
    @GET("informante/")
    Call<List<Informante>> getInformantes(@Header("Authorization") String authorization);
    @GET("informante/{id}")
    Call<Informante> getInformante(@Header("Authorization") String authorization,@Path("id") Integer id);
    @PUT("informante/{id}")
    Call<Informante> updateInformante(@Header("Authorization") String authorization,@Path("id") Integer id, @Body Informante informante);
    @DELETE("informante/{id}")
    Call<ResponseBody> deleteInformante(@Header("Authorization") String authorization,@Path("id") Integer id);

    @POST("informante")
    Call<Informante> newInformante(@Body Informante informante);
    @POST("informante/exist")
    Call<Informante> existInformante(@Body Informante informante);
    @POST("informante/login")
    Call<Informante> loginInformante(@Body Informante informante);

    @POST("informante/recovery")
    Call<ResponseBody> recoveryPassword(@Body Informante informante);
}
