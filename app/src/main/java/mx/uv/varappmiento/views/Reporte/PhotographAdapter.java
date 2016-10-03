package mx.uv.varappmiento.views.Reporte;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.controllers.MainController;
import mx.uv.varappmiento.controllers.ReportesController;
import mx.uv.varappmiento.models.Especimen;
import mx.uv.varappmiento.models.Imagen;
import mx.uv.varappmiento.models.Reporte;

/**
 * Created by willo on 26/09/2016.
 */

public class PhotographAdapter extends RecyclerView.Adapter<PhotographAdapter.PhotoViewHolder>{
    ArrayList<Imagen> imagenes;
    public PhotographAdapter()
    {
        getImagenes();
    }
    private void getImagenes()
    {
        imagenes = new ArrayList<Imagen>();
        Reporte reporte = ReportesController.getInstance().getCurrentReporte();
        Log.d(MainController.getInstance().getContext().getString(R.string.app_name),"Numero de especimenes" + reporte.getEspecimenes().size());
        for (Especimen especimen:
                reporte.getEspecimenes()) {
            List<Imagen> eimagenes = especimen.getImagenes();
            for (Imagen img:
                    eimagenes) {
                imagenes.add(img);
            }
        }
        Log.d(MainController.getInstance().getContext().getString(R.string.app_name),"Numero de imagenes" + imagenes.size());

    }
    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_photo, parent, false);
        PhotoViewHolder pvh = new PhotoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.especimen_photo.setImageBitmap(imagenes.get(position).getFile());
    }

    @Override
    public int getItemCount() {
        if(imagenes != null)
            return imagenes.size();
        return 0;
    }

    public void updateData() {
        getImagenes();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView especimen_photo;

        PhotoViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            especimen_photo = (ImageView)itemView.findViewById(R.id.especimen_photo);
        }
    }
}