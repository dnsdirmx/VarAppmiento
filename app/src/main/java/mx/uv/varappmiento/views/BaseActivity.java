package mx.uv.varappmiento.views;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.orm.SugarContext;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.controllers.Controller;
import mx.uv.varappmiento.controllers.InformantesController;
import mx.uv.varappmiento.helpers.Callbacks.VarAppiCallback;
import mx.uv.varappmiento.models.Informante;
import mx.uv.varappmiento.models.Pojo;
import mx.uv.varappmiento.services.SyncService;

public class BaseActivity extends AppCompatActivity {
    DrawerLayout fullView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    public void setContentView(int layoutResID) {
        fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (useToolbar()) {
            setSupportActionBar(toolbar);
            setTitle(getString(R.string.app_name));
        } else {
            toolbar.setVisibility(View.GONE);
        }
        InformantesController.getInstance().setView(this);
        SugarContext.init(this);
        if (!InformantesController.getInstance().isLogin())
            fullView.removeView(findViewById(R.id.navigationView));
        else {
            NavigationView nv = (NavigationView) findViewById(R.id.navigationView);
            nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    fullView.closeDrawers();
                    switch(item.getItemId())
                    {
                        case R.id.item_sync:
                            //TODO esto va en el controlador de main
                            startService(new Intent(BaseActivity.this, SyncService.class));

                            break;
                        case R.id.item_salir:
                            InformantesController.getInstance().logout(new VarAppiCallback() {
                                @Override
                                public void onResult(int status, Pojo object) {
                                    if(status == InformantesController.STATUS_LOGOUT_SUCCESSFUL)
                                        Toast.makeText(BaseActivity.this,"Se ha cerrado la sesión",Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(BaseActivity.this,"No se ha cerrado la sesión code: " + status,Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onFailure(int status, String message) {
                                    Toast.makeText(BaseActivity.this,"No se ha cerrado la sesión code: " + status + " " + message,Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                    }
                    return false;
                }
            });
        }
    }

    protected boolean useToolbar() {
        return true;
    }

}
