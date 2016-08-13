package mx.uv.varappmiento.views.Informante;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.controllers.InformantesController;
import mx.uv.varappmiento.helpers.Callbacks.VarAppiCallback;
import mx.uv.varappmiento.models.Informante;
import mx.uv.varappmiento.models.Pojo;

public class UpdateInformanteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_informante);


        Button btnUpdateInformante = (Button) findViewById(R.id.btnUpdateInformante);

        btnUpdateInformante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInformante();
            }
        });
    }

    private void updateInformante() {
        if(!isValid())
        {
            Toast.makeText(this,"Existen errores en el formulario",Toast.LENGTH_SHORT);
            return;
        }

        EditText txtNombre = (EditText) findViewById(R.id.txtNombre);
        EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        EditText txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        EditText txtConfPassword = (EditText) findViewById(R.id.txtConfPassword);
        EditText txtCurrentPasssword = (EditText) findViewById(R.id.txtCurrentPassword);
        InformantesController.getInstance().updateInformante(txtNombre.getText().toString(),
                                                            txtEmail.getText().toString(),
                                                            txtTelefono.getText().toString(),
                                                            txtPassword.getText().toString(),
                                                            txtCurrentPasssword.getText().toString(),
                new VarAppiCallback() {
                    @Override
                    public void onResult(int status, Pojo object) {

                    }

                    @Override
                    public void onFailure(int status, String message) {

                    }
                });
    }

    public boolean isValid()
    {
        boolean estado = true;
        EditText txtNombre = (EditText) findViewById(R.id.txtNombre);
        EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        EditText txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        EditText txtConfPassword = (EditText) findViewById(R.id.txtConfPassword);
        EditText txtCurrentPasssword = (EditText) findViewById(R.id.txtCurrentPassword);

        String regNumeros = "[0-9]+";
        String regEmail =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        Pattern pattern = Pattern.compile(regEmail,Pattern.CASE_INSENSITIVE);

        if(!(txtNombre.getText() != null && txtNombre.getText().length() > 0))
        {
            txtNombre.setError(getString(R.string.nombre_invalido));
            estado = false;
        }
        if(!(txtEmail.getText() != null && txtEmail.getText().length() > 0 && txtEmail.getText().toString().matches(regEmail)))
        {
            txtEmail.setError(getString(R.string.error_invalid_email));
            estado = false;
        }
        if(!(txtTelefono.getText() != null &&
                txtTelefono.getText().length() > 0 &&
                txtTelefono.getText().toString().matches(regNumeros) &&
                txtTelefono.getText().toString().length() == Informante.MAXLEN_PHONE_NUMBER))
        {
            txtTelefono.setError(getString(R.string.telefono_invalido));
            estado = false;
        }
        if(!(txtPassword != null && txtPassword.length() > Informante.MINLEN))
        {
            txtPassword.setError(getString(R.string.error_invalid_password));
            estado = false;
        }
        if(!(txtConfPassword.getText() != null && txtConfPassword.getText().length() > Informante.MINLEN ))
        {
            txtConfPassword.setError(getString(R.string.error_invalid_password));
            estado = false;
        }
        if(!(txtPassword.getText().toString().compareTo(txtConfPassword.getText().toString())== 0))
        {
            txtConfPassword.setError(getString(R.string.error_password_no_coincide));
            estado = false;
        }
        if(!(txtCurrentPasssword != null && txtCurrentPasssword.length() > Informante.MINLEN))
        {
            txtCurrentPasssword.setError(getString(R.string.error_invalid_password));
            estado = false;
        }
        if(!InformantesController.getInstance().isValidPassword(txtCurrentPasssword.getText().toString()))
        {
            txtCurrentPasssword.setError(getString(R.string.error_error_password));
        }
        return estado;
    }
}
