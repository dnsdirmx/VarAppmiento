package mx.uv.varappmiento.services;

import android.app.IntentService;
import android.content.Intent;

import mx.uv.varappmiento.controllers.OrdenesController;

/**
 * Created by willo on 10/08/2016.
 */
public class SyncService extends IntentService{

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SyncService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //metodo de sincnonizacion
        OrdenesController.getInstance().syncOrdenes();

    }
}
