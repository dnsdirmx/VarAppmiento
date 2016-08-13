package mx.uv.varappmiento.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.orm.SugarContext;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.controllers.InformantesController;
import mx.uv.varappmiento.controllers.MainController;
import mx.uv.varappmiento.views.AsistenciaVaramiento.OrdenesActivity;
import mx.uv.varappmiento.views.Reporte.ReporteActivity;

public class PrincipalActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        SugarContext.init(this);
        MainController.init(this);
        MainController.getInstance().validateAuthentication();

        setTitle(getString(R.string.app_name));
    }

    public void createReport(View view)
    {
        Intent newReport = new Intent(this, ReporteActivity.class);
        startActivity(newReport);
    }
    public void createAssistance(View view)
    {
        Intent newAssistance = new Intent(this, OrdenesActivity.class);
        startActivity(newAssistance);
    }
}
