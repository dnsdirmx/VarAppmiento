package mx.uv.varappmiento.views.AsistenciaVaramiento;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.controllers.InformantesController;
import mx.uv.varappmiento.controllers.MainController;
import mx.uv.varappmiento.models.Orden;
import mx.uv.varappmiento.models.Recomendacion;
import mx.uv.varappmiento.views.BaseActivity;

public class RecomendacionActivity extends BaseActivity {

    public static final String ORDEN_ID = "orden_id";
    ArrayList<Recomendacion> lRecomendaciones;
    private int currentRecomendacion = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendacion);

        Bundle extras = this.getIntent().getExtras();
        int orden_id = extras.getInt(RecomendacionActivity.ORDEN_ID);

        Log.d("recomendacion","orden id :" + orden_id);
        Orden orden = Orden.findById(Orden.class,orden_id);

        if(orden != null) {
            lRecomendaciones = Orden.findById(Orden.class, orden_id).getRecomendaciones();
            if (lRecomendaciones.size() > 0) {
                setRecomendacion(lRecomendaciones.get(0), false);
                currentRecomendacion = 0;
            }
            else {
                Toast.makeText(this, "Es necesario sincronizar la aplicación para obtener las ultimas actualizaciones", Toast.LENGTH_SHORT);
                finish();
            }
        }
        else {
            Toast.makeText(this, "No se encontro el orden: " + orden_id, Toast.LENGTH_SHORT).show();
            finish();
        }

        Button btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sigRecomendacion();
            }
        });
        Button btnAnterior = (Button) findViewById(R.id.btnAnterior);
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                antRecomendacion();
            }
        });
        getSupportActionBar().setTitle("Recomendación");
    }
    public void setRecomendacion(Recomendacion recomendacion, boolean ultima)
    {
        Log.d("recomend" +
                "acion","rec " + ultima + ":" + recomendacion.getId());
        ImageView imgRecomendacion = (ImageView) findViewById(R.id.imgRecomendacion);
        TextView descripcion = (TextView) findViewById(R.id.txtDescripcion);
        final Button btnSig = (Button) findViewById(R.id.btnSiguiente);
        Button btnAnt = (Button) findViewById(R.id.btnAnterior);

        imgRecomendacion.setImageBitmap(recomendacion.getImagen().getFile());
        descripcion.setText(recomendacion.getDescripcion());

        if(ultima) {
            btnSig.setText(getString(R.string.ir_menu));
        }
        else
            btnSig.setText(getString(R.string.btnSiguiente));
    }

    public void sigRecomendacion()
    {
        Log.d("recomendacion","current : " + currentRecomendacion);
        if(lRecomendaciones.size() == (currentRecomendacion + 1))
            MainController.getInstance().lanzarInicio();
        else
        {
            currentRecomendacion++;
            Log.d("recomendacion","current : " + currentRecomendacion);
            if(lRecomendaciones.size() == (currentRecomendacion + 1))
                setRecomendacion(lRecomendaciones.get(currentRecomendacion),true);
            else
                setRecomendacion(lRecomendaciones.get(currentRecomendacion),false);
        }
    }
    public void antRecomendacion()
    {
        Log.d("recomendacion","current : " + currentRecomendacion);
        if(currentRecomendacion <= 0)
        {
            this.finish();
        }
        if((currentRecomendacion - 1) >= 0) {
            currentRecomendacion--;
            setRecomendacion(lRecomendaciones.get(currentRecomendacion), false);
        }
    }

}
