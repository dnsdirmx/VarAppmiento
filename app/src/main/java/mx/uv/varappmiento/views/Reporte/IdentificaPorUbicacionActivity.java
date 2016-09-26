package mx.uv.varappmiento.views.Reporte;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.views.BaseActivity;

public class IdentificaPorUbicacionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identifica_por_ubicacion);

        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle("Selecciona el mamífero marino que mas se parezca");
    }
}
