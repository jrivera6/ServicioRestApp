package pe.jrivera6.serviciorestapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import pe.jrivera6.serviciorestapp.R;

public class MainActivity extends AppCompatActivity {

    private TextView msgBienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extra = getIntent().getExtras();
        String nombre = extra.getString("nombre");

        msgBienvenida = findViewById(R.id.msg_bienvenido);

        msgBienvenida.setText("BIENVENIDO! "+nombre);

    }
}
