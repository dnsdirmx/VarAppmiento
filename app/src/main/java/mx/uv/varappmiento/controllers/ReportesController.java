package mx.uv.varappmiento.controllers;

import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.models.Informante;
import mx.uv.varappmiento.models.Reporte;
import mx.uv.varappmiento.services.LocationService;
import mx.uv.varappmiento.views.Reporte.IdentificaPorUbicacionActivity;
import mx.uv.varappmiento.views.Reporte.ReporteActivity;

/**
 * Created by willo on 06/09/2016.
 */
public class ReportesController extends Controller {
    Reporte currentReporte = null;
    private static ReportesController ourInstance = new ReportesController();
    private ReporteActivity view = null;

    public static ReportesController getInstance() {
        return ourInstance;
    }

    private ReportesController() {
    }
    public void setViewReporte(ReporteActivity view)
    {
        this.view = view;
    }
    public void startReporteView()
    {
        currentReporte = new Reporte();
        currentReporte.save();
        Log.d(MainController.getInstance().getContext().getString(R.string.app_name),"Id reporte:" + currentReporte.getId());
        startServiceLocation();
        Intent intent = new Intent(MainController.getInstance().getContext(), ReporteActivity.class);
        MainController.getInstance().getContext().startActivity(intent);
    }

    private void startServiceLocation() {
        MainController.getInstance().getContext().startService(new Intent(MainController.getInstance().getContext(), LocationService.class));
    }

    public Reporte getCurrentReporte() {
        return currentReporte;
    }

    public void iniciaIdentificaEspecie() {
        Intent intent = new Intent(MainController.getInstance().getContext(), IdentificaPorUbicacionActivity.class);
        MainController.getInstance().getContext().startActivity(intent);
    }

    public boolean reporteHasPhotos()
    {
        return (currentReporte.getEspecimenes().size() > 0 ? true : false);
    }

    public void setCurrentLocation(Location currentLocation) {
        Log.d(MainController.getInstance().getContext().getString(R.string.app_name),"Location:" + currentLocation.getLatitude() + " - " + currentLocation.getLongitude());
        if(currentReporte != null)
        {
            currentReporte.setLongitud(currentLocation.getLongitude());
            currentReporte.setLatitud(currentLocation.getLatitude());
            view.updateMapview();
        }
    }

    public void finishCameraView() {
        view.finishCameraView();
    }
}
