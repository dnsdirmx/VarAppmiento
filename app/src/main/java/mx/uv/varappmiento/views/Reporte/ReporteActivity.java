package mx.uv.varappmiento.views.Reporte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.models.Reporte;
import mx.uv.varappmiento.views.BaseActivity;
import mx.uv.varappmiento.views.Reporte.PhotographActivity;
import mx.uv.varappmiento.views.Reporte.PhotographSurfaceView;

public class ReporteActivity extends BaseActivity {

    private Reporte reporte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);
        reporte = new Reporte();
        camaraNuevoEspecimen();
    }

    private void camaraNuevoEspecimen() {
        Intent nuevoEspecimen = new Intent(this, PhotographActivity.class);
        startActivity(nuevoEspecimen);
    }
}
