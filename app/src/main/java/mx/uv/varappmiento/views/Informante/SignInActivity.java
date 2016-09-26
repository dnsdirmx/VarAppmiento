package mx.uv.varappmiento.views.Informante;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.controllers.Controller;
import mx.uv.varappmiento.controllers.InformantesController;
import mx.uv.varappmiento.controllers.MainController;
import mx.uv.varappmiento.helpers.Callbacks.VarAppiCallback;
import mx.uv.varappmiento.models.Informante;
import mx.uv.varappmiento.models.Pojo;
import mx.uv.varappmiento.views.BaseActivity;

public class SignInActivity extends BaseActivity {

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });

        TextView lbForgotPassword = (TextView) findViewById(R.id.lbForgotPassword);
        lbForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InformantesController.getInstance().forgotPasswordView();
            }
        });

    }
    public boolean validaFormulario()
    {
        boolean estado = true;
        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
        TextView txtPassword = (TextView) findViewById(R.id.txtPassword);
        String regEmail =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        Pattern pattern = Pattern.compile(regEmail,Pattern.CASE_INSENSITIVE);
        if(!(txtEmail.getText() != null && txtEmail.getText().length() > 0 && txtEmail.getText().toString().matches(regEmail)))
        {
            txtEmail.setError(getString(R.string.error_invalid_email));
            estado = false;
        }
        if(!(txtPassword != null && txtPassword.length() > Informante.MINLEN))
        {
            txtPassword.setError(getString(R.string.error_invalid_password));
            estado = false;
        }
        return estado;
    }
    public void SignIn()
    {
        if(!validaFormulario())
        {
            Toast.makeText(this,"Error en el formulario",Toast.LENGTH_SHORT).show();
            return;
        }
        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
        TextView txtPassword = (TextView) findViewById(R.id.txtPassword);

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        final InformantesController controller = InformantesController.getInstance();
        pd = ProgressDialog.show(SignInActivity.this,"Iniciar sesion","validando credenciales",true);
        controller.setView(this);
        controller.SignIn(email,password, new VarAppiCallback() {
            @Override
            public void onResult(int status, Pojo object) {
                if(status == InformantesController.STATUS_LOGIN_SUCCESSFUL)
                {
                    pd.dismiss();
                    Toast.makeText(SignInActivity.this,"Iniciada la sesion",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int status, String message) {
                pd.dismiss();
                String msj = "";
                switch(status)
                {
                    case InformantesController.STATUS_NOT_EXIST:
                        msj = "Email o password invalido";
                        break;
                    default:
                        msj = "Error : " + status + " " + message;

                }
                Toast.makeText(SignInActivity.this,msj,Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void SignUp(View view)
    {
        InformantesController controller = InformantesController.getInstance();
        controller.setView(this);
        controller.startSignUp();
    }
}
