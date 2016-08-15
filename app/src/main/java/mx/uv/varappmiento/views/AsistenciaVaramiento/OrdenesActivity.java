package mx.uv.varappmiento.views.AsistenciaVaramiento;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import mx.uv.varappmiento.R;
import mx.uv.varappmiento.views.BaseActivity;

public class OrdenesActivity extends BaseActivity {

    private RecyclerView ordenesRecView;
    private RecyclerView.Adapter ordenesAdapter;
    private RecyclerView.LayoutManager ordenesLManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);

        ordenesRecView = (RecyclerView) findViewById(R.id.ordenes_recview);
        ordenesRecView.setHasFixedSize(true);
        ordenesLManager = new LinearLayoutManager(this);
        ordenesRecView.setLayoutManager(ordenesLManager);
        ordenesAdapter = new OrdenesAdapter();
        ordenesRecView.setAdapter(ordenesAdapter);
    }
}
