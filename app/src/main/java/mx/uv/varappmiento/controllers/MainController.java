package mx.uv.varappmiento.controllers;

import android.content.Context;
import android.content.Intent;

import com.orm.SugarContext;

import mx.uv.varappmiento.views.AboutActivity;
import mx.uv.varappmiento.views.BaseActivity;
import mx.uv.varappmiento.views.Informante.RecoveryPasswordActivity;
import mx.uv.varappmiento.views.PrincipalActivity;
import mx.uv.varappmiento.views.SettingsActivity;

/**
 * Created by willo on 27/07/2016.
 */
public class MainController extends Controller {



    private Context context;
    private static MainController ourInstance = new MainController();
    public static MainController getInstance() {
        return ourInstance;
    }

    private MainController() {
        super();
    }

    public Context getContext() {
        return context;
    }

    public static void init(Context context)
    {
        getInstance().setContext(context);
        getInstance().setView((BaseActivity) context);
    }

    public void setContext(Context context) {
        this.context = context;
        SugarContext.init(this.context);
    }

    public void validateAuthentication()
    {
        InformantesController currentController = InformantesController.getInstance();
        currentController.setView(activity);
        if(!currentController.isLogin())
            currentController.startSignIn();
    }

    public void lanzarInicio()
    {
        Intent intent = new Intent(context,PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void lanzaAcerca() {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    public void lanzaConfiguracion() {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }
}
