package mx.uv.varappmiento.controllers;

import mx.uv.varappmiento.views.BaseActivity;

/**
 * Created by willo on 27/07/2016.
 */
public class Controller {
    BaseActivity activity = null;




    public void setView(BaseActivity activity)
    {
        this.activity = activity;
    }

    public BaseActivity getView()
    {
        return activity;
    }
}
