package mx.uv.varappmiento.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orm.SugarRecord;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * Created by willo on 17/07/2016.
 */
public class Informante extends SugarRecord implements Pojo {
    public static final int MAXLEN_PHONE_NUMBER = 10 ;
    public static final int MINLEN = 4;
    //El ORM genera automaticamente un id de tipo long
    //private Integer id;

    private String nombre;
    private String email;
    private String telefono;
    private String password;
    private String api_android_token;

    public String getApi_android_token() {
        return api_android_token;
    }

    public void setApi_android_token(String api_android_token) {
        this.api_android_token = api_android_token;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public static void main(String[] args)
    {
        Informante informante = new Informante();

        informante.setNombre("Hector Villla Garia");
        informante.setEmail("dnsdirm@hotmail.com");
        informante.setPassword("12345");
        informante.setTelefono("2282145602");
        long algo = informante.save();
        System.out.println("Algo: " + informante.getId());
        Informante info2 = Informante.findById(Informante.class,informante.getId());

    }

    public static Informante fromJson(String json){
        Reader reader = new InputStreamReader(new ByteArrayInputStream(json.getBytes()));
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(reader,Informante.class);
    }
    public static String toJson(Informante informante)
    {
        Gson gson = new Gson();
        return gson.toJson(informante);

    }
    @Override
    public String toString()
    {
        return Informante.toJson(this);
    }


}
