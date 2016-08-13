package mx.uv.varappmiento.controllers;

import android.content.Context;

import com.orm.SugarContext;

import mx.uv.varappmiento.views.BaseActivity;

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
}
