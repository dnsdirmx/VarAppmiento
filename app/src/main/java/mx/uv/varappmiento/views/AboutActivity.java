package mx.uv.varappmiento.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mx.uv.varappmiento.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle("Acerca de VarAppmiento");
    }
}
