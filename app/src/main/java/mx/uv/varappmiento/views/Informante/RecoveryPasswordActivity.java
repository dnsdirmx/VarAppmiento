package mx.uv.varappmiento.views.Informante;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.controllers.InformantesController;
import mx.uv.varappmiento.controllers.MainController;
import mx.uv.varappmiento.helpers.Callbacks.VarAppiCallback;
import mx.uv.varappmiento.models.Informante;
import mx.uv.varappmiento.models.Pojo;
import mx.uv.varappmiento.views.BaseActivity;

public class RecoveryPasswordActivity extends BaseActivity {
    ProgressDialog pd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);

        Button btnRecovery = (Button) findViewById(R.id.btnRecovery);
        btnRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviaEmail();
            }
        });
    }

    private void enviaEmail() {


        String regEmail =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        Pattern pattern = Pattern.compile(regEmail,Pattern.CASE_INSENSITIVE);

        TextView txtEmailRecovery = (TextView) findViewById(R.id.txtEmailRecovery);
        if(!(txtEmailRecovery.getText() != null && txtEmailRecovery.getText().length() > 0 && txtEmailRecovery.getText().toString().matches(regEmail)))
        {
            txtEmailRecovery.setError(getString(R.string.error_invalid_email));
            return;
        }
        pd = ProgressDialog.show(this,"Informante","Enviando petición",false);
        InformantesController.getInstance().forgotPassword(txtEmailRecovery.getText().toString(), new VarAppiCallback() {
            @Override
            public void onResult(int status, Pojo object) {
                pd.dismiss();
                Toast.makeText(MainController.getInstance().getContext(),"Se ha enviado a tu email tu password",Toast.LENGTH_SHORT).show();
                MainController.getInstance().lanzarInicio();
            }

            @Override
            public void onFailure(int status, String message) {
                pd.dismiss();
                Toast.makeText(MainController.getInstance().getContext(),"Error: " + message,Toast.LENGTH_SHORT ).show();
            }
        });
    }
}
