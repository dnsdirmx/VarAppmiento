package mx.uv.varappmiento.helpers.Callbacks;

import mx.uv.varappmiento.models.Informante;
import mx.uv.varappmiento.models.Pojo;

/**
 * Created by willo on 30/07/2016.
 */
public interface VarAppiCallback {
    public void onResult(int status, Pojo object);
    public void onFailure(int status, String message);
}
