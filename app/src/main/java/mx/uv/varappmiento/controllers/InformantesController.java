package mx.uv.varappmiento.controllers;

import android.content.Intent;
import android.text.Editable;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.helpers.Callbacks.VarAppiCallback;
import mx.uv.varappmiento.helpers.EndPoints.InformanteEndpointInterface;
import mx.uv.varappmiento.helpers.VarAppiConsumer;
import mx.uv.varappmiento.models.Informante;
import mx.uv.varappmiento.models.Pojo;
import mx.uv.varappmiento.views.Informante.RecoveryPasswordActivity;
import mx.uv.varappmiento.views.Informante.SignInActivity;
import mx.uv.varappmiento.views.Informante.SignUpActivity;
import mx.uv.varappmiento.views.Informante.UpdateInformanteActivity;
import mx.uv.varappmiento.views.PrincipalActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by willo on 27/07/2016.
 */
public class InformantesController extends Controller {

    public static final int STATUS_NOT_EXIST = 0;
    public static final int STATUS_EXIST = 1;
    public static final int STATUS_LOGIN_SUCCESSFUL = 2;
    public static final int STATUS_LOGIN_ERROR = 3;
    public static final int STATUS_CREATED_ERROR = 4;
    public static final int STATUS_ERROR = 5;
    public static final int STATUS_CREATED = 6;
    public static final int STATUS_LOGOUT_SUCCESSFUL = 7;
    public static final int STATUS_LOGOUT_FAILED = 8;
    public static final int STATUS_PASSWORD_ERROR = 9;

    private static InformantesController ourInstance = new InformantesController();

    public static InformantesController getInstance() {
        return ourInstance;
    }

    public void SignIn(String email, String password,final VarAppiCallback viewCallback)
    {
        final Informante informante = new Informante();
        informante.setEmail(email);
        informante.setPassword(password);
        final VarAppiCallback loginCallback = new VarAppiCallback() {
            @Override
            public void onResult(int status, Pojo object) {
                if(status == InformantesController.STATUS_LOGIN_SUCCESSFUL)
                {
                    Informante informante = (Informante) object;
                    informante.save();
                    viewCallback.onResult(InformantesController.STATUS_LOGIN_SUCCESSFUL,null);
                    launchMainActivity();
                }
            }

            @Override
            public void onFailure(int status, String message) {
                viewCallback.onFailure(status,message);
            }
        };
        VarAppiCallback existCallback = new VarAppiCallback() {
            @Override
            public void onResult(int status, Pojo object) {
                switch (status) {
                    case InformantesController.STATUS_EXIST:
                        InformantesController.this.logueaInformante(informante,loginCallback);
                        break;
                    case InformantesController.STATUS_NOT_EXIST:
                        //llama metodo para crear recurso

                        viewCallback.onFailure(InformantesController.STATUS_NOT_EXIST, null);

                        break;
                    default:
                        viewCallback.onFailure(InformantesController.STATUS_ERROR, null);
                }
            }

            @Override
            public void onFailure(int status, String message) {
                viewCallback.onFailure(InformantesController.STATUS_ERROR, null);
            }
        };
        checkIfExist(informante,existCallback);
    }
    public  void startSignIn() {
        Intent intent = new Intent(activity,SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }
    public  void startSignUp()
    {
        Intent intent = new Intent(activity,SignUpActivity.class);
        activity.startActivity(intent);
    }

    public Informante getActive()
    {
        List<Informante> informantes = Informante.listAll(Informante.class);
        if(!informantes.isEmpty())
            return informantes.get(0);
        return null;
    }
    public boolean isLogin() {
        if(getActive()== null)
            return false;
        return true;
    }


    public void SignUp(String name,
                          String email,
                          String phone,
                          String password, final VarAppiCallback viewCallback) {
        final Informante informante = new Informante();
        informante.setNombre(name);
        informante.setEmail(email);
        informante.setTelefono(phone);
        informante.setPassword(password);
        final VarAppiCallback logueaCallback = new VarAppiCallback() {
            @Override
            public void onResult(int status, Pojo object) {
                if(status == InformantesController.STATUS_LOGIN_SUCCESSFUL)
                {
                    Informante informante = (Informante) object;
                    informante.save();
                    viewCallback.onResult(InformantesController.STATUS_LOGIN_SUCCESSFUL,null);
                    launchMainActivity();
                }
            }

            @Override
            public void onFailure(int status, String message) {
                viewCallback.onFailure(status,message);
            }
        };
        final VarAppiCallback guardaCallback = new VarAppiCallback() {
            @Override
            public void onResult(int status, Pojo object) {
                if(status == InformantesController.STATUS_CREATED) {
                    Informante informante = (Informante) object;
                    informante.save();
                    InformantesController.this.logueaInformante(informante,logueaCallback);
                }
            }

            @Override
            public void onFailure(int status, String message) {
                viewCallback.onFailure(status,message);
            }
        };
        VarAppiCallback existCallback = new VarAppiCallback() {
            @Override
            public void onResult(int status, Pojo object) {
                switch(status)
                {
                    case InformantesController.STATUS_EXIST:
                        viewCallback.onFailure(InformantesController.STATUS_EXIST,null);
                        break;
                    case InformantesController.STATUS_NOT_EXIST:
                        //llama metodo para crear recurso
                        InformantesController.this.createInformante(informante,guardaCallback);
                        break;
                    default:
                        viewCallback.onFailure(InformantesController.STATUS_ERROR,null);
                }
            }

            @Override
            public void onFailure(int status, String message) {
                viewCallback.onFailure(InformantesController.STATUS_ERROR,null);
            }
        };

        checkIfExist(informante, existCallback);




    }

    private void logueaInformante(Informante informante, final VarAppiCallback viewCallback) {
        VarAppiConsumer vapc = VarAppiConsumer.getInstance();
        InformanteEndpointInterface service = vapc.getRetrofit().create(InformanteEndpointInterface.class);
        Call<Informante> callLogin= service.loginInformante(informante);

        callLogin.enqueue(new Callback<Informante>() {
            @Override
            public void onResponse(Call<Informante> call, Response<Informante> response) {
                switch(response.code())
                {
                    case 200:
                        viewCallback.onResult(InformantesController.STATUS_LOGIN_SUCCESSFUL,response.body());
                        break;
                    case 400:
                        viewCallback.onFailure(InformantesController.STATUS_ERROR,response.message());
                        break;
                    case 404:
                        viewCallback.onFailure(InformantesController.STATUS_NOT_EXIST,response.message());
                        break;
                    default:
                }
            }

            @Override
            public void onFailure(Call<Informante> call, Throwable t) {
                viewCallback.onFailure(InformantesController.STATUS_ERROR,t.getMessage());
            }
        });
    }

    private void createInformante(final Informante informante, final VarAppiCallback viewCallback) {
        VarAppiConsumer vapc = VarAppiConsumer.getInstance();
        InformanteEndpointInterface service = vapc.getRetrofit().create(InformanteEndpointInterface.class);
        Call<Informante> callCrea = service.newInformante(informante);

        callCrea.enqueue(new Callback<Informante>() {
            @Override
            public void onResponse(Call<Informante> call, Response<Informante> response) {
                switch(response.code())
                {
                    case 201:
                        //metodo para logueo
                        viewCallback.onResult(InformantesController.STATUS_CREATED,response.body());
                        break;
                    default:
                        viewCallback.onFailure(InformantesController.STATUS_ERROR,response.message());
                }
            }

            @Override
            public void onFailure(Call<Informante> call, Throwable t) {

            }
        });
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this.getView(),PrincipalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK );
        this.getView().startActivity(intent);
        this.getView().finish();
    }


//VarAppiConsumer vapc = VarAppiConsumer.getInstance();
//InformanteEndpointInterface service = vapc.getRetrofit().create(InformanteEndpointInterface.class);
//Call<Informante> callExiste = service.existInformante(informante);
//callExiste.enqueue(new Callback<Informante>() {
    public void checkIfExist(Informante informante, final VarAppiCallback viewCallback)
    {
        VarAppiConsumer vapc = VarAppiConsumer.getInstance();
        InformanteEndpointInterface service = vapc.getRetrofit().create(InformanteEndpointInterface.class);
        Call<Informante> callExiste = service.existInformante(informante);

        callExiste.enqueue(new Callback<Informante>() {
            @Override
            public void onResponse(Call<Informante> call, Response<Informante> response) {
                switch(response.code())
                {
                    case 200:
                        viewCallback.onResult(InformantesController.STATUS_EXIST,response.body());
                        break;
                    case 404:
                        viewCallback.onResult(InformantesController.STATUS_NOT_EXIST,null);
                        break;
                    default:
                        viewCallback.onFailure(InformantesController.STATUS_ERROR,response.message());
                }

            }

            @Override
            public void onFailure(Call<Informante> call, Throwable t) {
                viewCallback.onFailure(InformantesController.STATUS_ERROR,t.getMessage());
            }
        });
    }

    public void logout(final VarAppiCallback viewCallback) {
        Informante informante = InformantesController.getInstance().getActive();
        VarAppiConsumer vapc = VarAppiConsumer.getInstance();
        InformanteEndpointInterface service = vapc.getRetrofit().create(InformanteEndpointInterface.class);
        Call<ResponseBody> callLogout = service.logoutInformante(informante.getApi_android_token());
        Log.d("mensaje","token " + informante.getApi_android_token());
        callLogout.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 204) //checa si no tiene  contenido
                {
                    viewCallback.onResult(InformantesController.STATUS_LOGOUT_SUCCESSFUL, null);
                    getActive().delete();
                    launchMainActivity();
                }
                else
                    viewCallback.onFailure(InformantesController.STATUS_LOGOUT_FAILED,response.message());
                Log.d("mensaje",response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("mensaje",t.getLocalizedMessage());
                viewCallback.onFailure(InformantesController.STATUS_LOGOUT_FAILED,t.getMessage());
            }
        });
    }

    public void startUpdateView()
    {
        Intent intent = new Intent(MainController.getInstance().getContext(), UpdateInformanteActivity.class);
        MainController.getInstance().getContext().startActivity(intent);
    }
    public boolean isValidPassword(String password) {
        if(this.getActive().getPassword().compareTo(password) == 0)
            return true;
        return false;
    }

    public void updateInformante(String nombre,
                                 String email,
                                 String telefono,
                                 String newPassword,
                                 String currentPassword,
                                 final VarAppiCallback viewCallback) {
        if(!getInstance().isValidPassword(currentPassword))
        {
            viewCallback.onFailure(InformantesController.STATUS_PASSWORD_ERROR,"Password inválido");
            return;
        }
        final Informante informante = getActive();

        informante.setNombre(nombre);
        informante.setEmail(email);
        informante.setTelefono(telefono);
        informante.setPassword(newPassword);

        VarAppiConsumer vapc = VarAppiConsumer.getInstance();
        InformanteEndpointInterface service = vapc.getRetrofit().create(InformanteEndpointInterface.class);
        Call<Informante> callUpdate = service.updateInformante(getActive().getApi_android_token(),getActive().getId().intValue(),informante);
        Log.d("mensaje","token " + informante.getApi_android_token());
        callUpdate.enqueue(new Callback<Informante>() {
            @Override
            public void onResponse(Call<Informante> call, Response<Informante> response) {
                if(response.code() ==  200)
                {
                    informante.save();//guarda el informante
                    viewCallback.onResult(InformantesController.STATUS_CREATED,informante);
                }
                else
                    viewCallback.onFailure(InformantesController.STATUS_ERROR,response.message());
            }
            @Override
            public void onFailure(Call<Informante> call, Throwable t) {
                viewCallback.onFailure(InformantesController.STATUS_ERROR,t.getMessage());
            }
        });
    }

    public void forgotPasswordView() {
        Intent intent = new Intent(MainController.getInstance().getContext(),RecoveryPasswordActivity.class);
        MainController.getInstance().getContext().startActivity(intent);
    }

    public void forgotPassword(String email, final VarAppiCallback viewCallback)
    {
        if(email.length() <= 0)
        {
            viewCallback.onFailure(InformantesController.STATUS_ERROR,"Email empty");
            return;
        }
        final Informante informante = new Informante();
        informante.setEmail(email);
        VarAppiConsumer vapc = VarAppiConsumer.getInstance();
        InformanteEndpointInterface service = vapc.getRetrofit().create(InformanteEndpointInterface.class);
        Call<ResponseBody> callRecovery = service.recoveryPassword(informante);
        callRecovery.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() != 200)
                {
                    viewCallback.onFailure(InformantesController.STATUS_ERROR,"El email no existe");
                    return;
                }
                Log.d(MainController.getInstance().getContext().getString(R.string.app_name),"Response:" + response.code() + " body:" + response.body());
                viewCallback.onResult(InformantesController.STATUS_CREATED,informante);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(MainController.getInstance().getContext().getString(R.string.app_name),"ResponseError: " + t.getMessage());

                viewCallback.onFailure(InformantesController.STATUS_ERROR,t.getMessage());
            }
        });
    }
}
