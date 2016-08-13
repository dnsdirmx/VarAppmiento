package mx.uv.varappmiento.services;

import android.app.IntentService;
import android.content.Intent;

import mx.uv.varappmiento.controllers.OrdenesController;

/**
 * Created by willo on 10/08/2016.
 */
public class SyncService extends IntentService{

    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //metodo de sincnonizacion
        OrdenesController.getInstance().syncOrdenes();

    }
}
