package mx.uv.varappmiento.views.Reporte;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.controllers.MainController;
import mx.uv.varappmiento.controllers.ReportesController;
import mx.uv.varappmiento.views.BaseActivity;

public class PhotographActivity extends BaseActivity {

    private PhotographSurfaceView mPhotographSurfaceView;
    private FrameLayout cameraPreview;
    ImageButton btnAceptar;
    ImageButton btnRetomar;
    ImageButton btnAgregar;
    ImageButton btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photograph);

        cameraPreview = (FrameLayout)findViewById(R.id.cameraPreview);
        //cambiando la actividad
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mPhotographSurfaceView = new PhotographSurfaceView(this,cameraPreview);
        cameraPreview.addView(mPhotographSurfaceView);

        btnAceptar = (ImageButton)findViewById(R.id.btnAceptar);
        btnRetomar = (ImageButton)findViewById(R.id.btnRetomar);
        btnAgregar = (ImageButton)findViewById(R.id.btnAgregar);
        btnContinuar = (ImageButton) findViewById(R.id.btnContinuar);

        btnAceptar.bringToFront();
        btnRetomar.bringToFront();
        btnAgregar.bringToFront();
        btnContinuar.bringToFront();

        btnAceptar.setVisibility(View.VISIBLE);
        btnRetomar.setVisibility(View.GONE);
        btnAgregar.setVisibility(View.GONE);
        btnContinuar.setVisibility(View.GONE);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotographSurfaceView.savePicture();
                mPhotographSurfaceView.resetPreview();
                //preguntas para no me acuerdo
            }
        });
        btnRetomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotographSurfaceView.resetPreview();

                btnAceptar.setVisibility(View.VISIBLE);
                btnRetomar.setVisibility(View.GONE);
                btnAgregar.setVisibility(View.GONE);
                btnContinuar.setVisibility(View.VISIBLE);
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotographSurfaceView.savePicture();
                mPhotographSurfaceView.resetPreview();

                btnAceptar.setVisibility(View.VISIBLE);
                btnRetomar.setVisibility(View.GONE);
                btnAgregar.setVisibility(View.GONE);
                btnContinuar.setVisibility(View.VISIBLE);
            }
        });
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotographSurfaceView.takePhoto();

                btnAceptar.setVisibility(View.GONE);
                btnRetomar.setVisibility(View.VISIBLE);
                btnAgregar.setVisibility(View.VISIBLE);
                btnContinuar.setVisibility(View.VISIBLE);
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continuar();
            }
        });

        muestraRecomendacion();
   }

    private void muestraRecomendacion()
    {
        new AlertDialog.Builder(this)
                .setTitle("Recomendaciones")
                .setMessage("Puedes ayudarnos a mejorar el reporte colocando un objeto de tamaño conocido(Por ejemplo : listado de objetos) al lado del mamifero marino varado para asi obtener el tamaño aproximado del mismo.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
    private void continuar() {
        new AlertDialog.Builder(this)
                .setTitle("Especimen")
                .setMessage("¿Deseas identificar la especie a la que pertenece el mamífero marino?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ReportesController.getInstance().iniciaIdentificaEspecie();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ReportesController.getInstance().finishCameraView();

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
    protected void terminarActividad()
    {
        PhotographActivity.this.finish();
    }

    protected boolean useToolbar()
    {
        return false;
    }

    @Override
    public void onBackPressed() {
        if(!ReportesController.getInstance().reporteHasPhotos()) {
            new AlertDialog.Builder(this)
                    .setTitle("Reporte")
                    .setMessage("Tienes que tomar al menos una fotografia")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
        else {
            ReportesController.getInstance().finishCameraView();
            PhotographActivity.this.finish();
        }
    }
}
