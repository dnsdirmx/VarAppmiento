package mx.uv.varappmiento.views.Reporte;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;

import mx.uv.varappmiento.R;

public class PhotographActivity extends AppCompatActivity {

    private PhotographSurfaceView mPhotographSurfaceView;
    private FrameLayout cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photograph);

        cameraPreview = (FrameLayout)findViewById(R.id.cameraPreview);
        //cambiando la actividad
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mPhotographSurfaceView = new PhotographSurfaceView(this,cameraPreview);
        cameraPreview.addView(mPhotographSurfaceView);

   }
}
