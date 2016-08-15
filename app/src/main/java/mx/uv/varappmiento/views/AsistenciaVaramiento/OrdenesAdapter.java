package mx.uv.varappmiento.views.AsistenciaVaramiento;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.controllers.MainController;
import mx.uv.varappmiento.models.Orden;

/**
 * Created by willo on 14/08/2016.
 */
public class OrdenesAdapter extends android.support.v7.widget.RecyclerView.Adapter {

    private ArrayList<Orden> ordenes;

    public OrdenesAdapter()
    {

        Iterator<Orden> ordenesIt = Orden.findAll(Orden.class);
        ordenes = new ArrayList<Orden>();
        while(ordenesIt.hasNext())
            ordenes.add(ordenesIt.next());
    }

    public class OrdenViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public ImageView imagen;

        public OrdenViewHolder(View view) {
            super(view);
            nombre = (TextView) view.findViewById(R.id.orden_nombre);
            imagen = (ImageView) view.findViewById(R.id.orden_imagen);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orden_card_view, parent, false);
        return new OrdenViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final OrdenViewHolder ordenHolder = (OrdenViewHolder) holder;
        Orden actual = null;
        ordenHolder.nombre.setText(ordenes.get(position).getOrden());
        ordenHolder.imagen.setImageBitmap(ordenes.get(position).getImagen().getFile());
        ordenHolder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainController.getInstance().getContext(),"Seleccionado orden:" + ordenHolder.nombre.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ordenes.size();
    }
}
