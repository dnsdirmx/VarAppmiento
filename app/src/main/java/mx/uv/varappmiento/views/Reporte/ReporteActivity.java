package mx.uv.varappmiento.views.Reporte;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.controllers.MainController;
import mx.uv.varappmiento.controllers.ReportesController;
import mx.uv.varappmiento.models.Reporte;
import mx.uv.varappmiento.views.BaseActivity;
import mx.uv.varappmiento.views.Reporte.PhotographActivity;
import mx.uv.varappmiento.views.Reporte.PhotographSurfaceView;

public class ReporteActivity extends BaseActivity {
    public static String ID_REPORTE_TEMP = "REPORTE_ID";
    private Reporte reporte;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);
        ReportesController.getInstance().setViewReporte(this);
        camaraNuevoEspecimen();
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.reporte_title));

        RecyclerView rvPhotos = (RecyclerView)findViewById(R.id.rvPhotos);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPhotos.setLayoutManager(llm);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new MapaInnerClass());

        Button btnEnviar = (Button) findViewById(R.id.btnEnvia);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ReporteActivity.this)
                        .setTitle("Reporte")
                        .setMessage("¿Estas seguro que deseas enviar el reporte?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });
    }

    private void camaraNuevoEspecimen() {
        Intent nuevoEspecimen = new Intent(this, PhotographActivity.class);
        startActivity(nuevoEspecimen);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Reporte")
                .setMessage("¿Deseas salir del reporte sin enviarlo?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ReporteActivity.this.finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    public void finishCameraView() {
        new AlertDialog.Builder(this)
                .setTitle("Reporte")
                .setMessage("¿Deseas tomar una fotografia mas a otro especimen?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        camaraNuevoEspecimen();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    public void updateMapview() {
        LatLng latlgn = new LatLng(ReportesController.getInstance().getCurrentReporte().getLatitud(),ReportesController.getInstance().getCurrentReporte().getLongitud());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlgn , 14.0f) );
    }


    private class MapaInnerClass implements OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            ReporteActivity.this.googleMap = googleMap;
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            if (ActivityCompat.checkSelfPermission(ReporteActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ReporteActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            googleMap.setMyLocationEnabled(true);
            googleMap.setIndoorEnabled(true);
            googleMap.setBuildingsEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
        }
    }


}
