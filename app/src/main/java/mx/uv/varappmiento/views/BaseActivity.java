package mx.uv.varappmiento.views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.SugarContext;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.controllers.Controller;
import mx.uv.varappmiento.controllers.InformantesController;
import mx.uv.varappmiento.controllers.MainController;
import mx.uv.varappmiento.helpers.Callbacks.VarAppiCallback;
import mx.uv.varappmiento.models.Informante;
import mx.uv.varappmiento.models.Pojo;
import mx.uv.varappmiento.services.SyncService;

public class BaseActivity extends AppCompatActivity {
    DrawerLayout fullView = null;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
            if(useToolbar())
            {
                //toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_white_48dp));



                toolbar.setTitleTextColor(Color.WHITE);
                mDrawerToggle = new ActionBarDrawerToggle(
                        BaseActivity.this,                    /* host Activity */
                        fullView,                    /* DrawerLayout object */
                        R.drawable.ic_menu_white_24dp,
                        R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                        R.string.navigationic_drawer_drawer_close  /* "close drawer" description for accessibility */
                );

                fullView.setDrawerListener(mDrawerToggle);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
                getSupportActionBar().setHomeButtonEnabled(true);
            }
            View headerLayout = nv.getHeaderView(0);
            TextView txtUsuarioNombre = ( TextView)  headerLayout.findViewById(R.id.txtUsuarioNombre);
            txtUsuarioNombre.setText(InformantesController.getInstance().getActive().getNombre());
            nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    fullView.closeDrawers();
                    switch(item.getItemId())
                    {
                        case R.id.item_principal:
                            if(!(BaseActivity.this instanceof PrincipalActivity))
                                MainController.getInstance().lanzarInicio();
                            break;
                        case R.id.item_actualizar_informante:
                            InformantesController.getInstance().startUpdateView();
                            break;
                        case R.id.item_about:
                            MainController.getInstance().lanzaAcerca();
                            break;
                        //case R.id.item_sync:
                            //TODO esto va en el controlador de main
                            //startService(new Intent(BaseActivity.this, SyncService.class));

                            //break;
                        case R.id.item_configuracion:
                            MainController.getInstance().lanzaConfiguracion();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) {
            if(fullView.isDrawerOpen(Gravity.LEFT)) {
                fullView.closeDrawer(Gravity.LEFT);
            }else{
                fullView.openDrawer(Gravity.LEFT);
            }

        }
        return super.onOptionsItemSelected(item);
    }
    protected boolean useToolbar() {
        return true;
    }

}
