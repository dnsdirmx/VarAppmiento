package mx.uv.varappmiento.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.views.Reporte.PhotographActivity;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar actionBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(actionBar);

    }

    public void createReport(View view)
    {
        Intent newReport = new Intent(this, PhotographActivity.class);
        startActivity(newReport);
    }
}
