package mx.uv.varappmiento.views.AsistenciaVaramiento;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.models.Orden;
import mx.uv.varappmiento.models.Recomendacion;
import mx.uv.varappmiento.views.BaseActivity;

public class RecomendacionActivity extends BaseActivity {

    public static final String ORDEN_ID = "orden_id";
    ArrayList<Recomendacion> lRecomendaciones;

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
            if (lRecomendaciones.size() > 0)
                setRecomendacion(lRecomendaciones.get(0));
        }
        else
            Toast.makeText(this,"No se encontro el orden: " + orden_id,Toast.LENGTH_SHORT).show();
    }
    public void setRecomendacion(Recomendacion recomendacion)
    {
        ImageView imgRecomendacion = (ImageView) findViewById(R.id.imgRecomendacion);
        TextView descripcion = (TextView) findViewById(R.id.txtDescripcion);
        Button btnSig = (Button) findViewById(R.id.btnSiguiente);
        Button btnAnt = (Button) findViewById(R.id.btnAnterior);

        imgRecomendacion.setImageBitmap(recomendacion.getImagen().getFile());
        descripcion.setText(recomendacion.getDescripcion());

    }
}
