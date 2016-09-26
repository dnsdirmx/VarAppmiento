package mx.uv.varappmiento.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.orm.SugarContext;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.controllers.InformantesController;
import mx.uv.varappmiento.controllers.MainController;
import mx.uv.varappmiento.controllers.ReportesController;
import mx.uv.varappmiento.views.AsistenciaVaramiento.OrdenesActivity;
import mx.uv.varappmiento.views.Especies.TarjetaEspecieActivity;
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

    public void creaTemp(View view)
    {
        Intent intent = new Intent(this, TarjetaEspecieActivity.class);
        startActivity(intent);
    }
    public void createReport(View view)
    {
        ReportesController.getInstance().startReporteView();
    }
    public void createAssistance(View view)
    {
        Intent newAssistance = new Intent(this, OrdenesActivity.class);
        startActivity(newAssistance);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("VarAppmiento")
                .setMessage("¿Deseas salir de la aplicación?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PrincipalActivity.this.finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
