package mx.uv.varappmiento.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orm.SugarRecord;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Date;

import mx.uv.varappmiento.controllers.MainController;
import okhttp3.ResponseBody;

/**
 * Created by willo on 27/07/2016.
 */
public class Imagen extends SugarRecord implements Pojo{
    //Integer id;

    private String nombre;
    private String localpath;
    private Date actualizado;

    public Date getActualizado() {
        return actualizado;
    }

    public void setActualizado(Date actualizado) {
        this.actualizado = actualizado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalpath() {
        return localpath;
    }

    public void setLocalpath(String localpath) {
        this.localpath = localpath;
    }

    public static Imagen fromJson(String json){
        Reader reader = new InputStreamReader(new ByteArrayInputStream(json.getBytes()));
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(reader,Imagen.class);
    }
    public static String toJson(Imagen imagen)
    {
        Gson gson = new Gson();
        return gson.toJson(imagen);

    }
    @Override
    public String toString()
    {
        return Imagen.toJson(this);
    }


    public boolean setFile(InputStream input, long length) {
        boolean estado = false;

        String fileName = this.getNombre();
        FileOutputStream outputStream = null;

        try {
            Context context = MainController.getInstance().getView().getApplicationContext();
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);

            long fileSizeDownloaded = 0;
            byte[] fileReader = new byte[4096];

            while (true) {
                int read = input.read(fileReader);
                if (read == -1) {
                    break;
                }
                outputStream.write(fileReader, 0, read);
                fileSizeDownloaded += read;
                Log.d("files", "file download: " + fileSizeDownloaded + " of " + length);
            }

            this.setLocalpath(MainController.getInstance().getContext().getFilesDir() + File.pathSeparator + this.getNombre());

            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estado;
    }
    public Bitmap getFile()
    {
        Bitmap img = null;
        img = BitmapFactory.decodeFile(this.getLocalpath());
        return img;
    }
}
