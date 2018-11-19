package pe.jrivera6.serviciorestapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import retrofit2.Call;

import pe.jrivera6.serviciorestapp.R;
import pe.jrivera6.serviciorestapp.Services.ApiService;
import pe.jrivera6.serviciorestapp.Services.ApiServiceGenerator;
import pe.jrivera6.serviciorestapp.models.ResponseMessage;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private EditText txtNombre, txtCorreo, txtContraseña;
    private Button btnRegistrar;
    private RadioGroup rdGrupo;
    private RadioButton rdButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtNombre = findViewById(R.id.txt_nombre_completo);
        txtCorreo = findViewById(R.id.txt_correo);
        txtContraseña = findViewById(R.id.txt_contraseña);
        rdGrupo = findViewById(R.id.rd_group);
        btnRegistrar = findViewById(R.id.btn_registrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int idRadio = rdGrupo.getCheckedRadioButtonId();
                rdButton = findViewById(idRadio);

                String nombres = txtNombre.getText().toString();
                String correo = txtCorreo.getText().toString();
                String contraseña = txtContraseña.getText().toString();
                String tipoUsuario = rdButton.getText().toString();


                ApiService service = ApiServiceGenerator.createService(ApiService.class);
                Call<ResponseMessage> call = null;
                call = service.createUser(nombres,correo,contraseña,tipoUsuario);

                call.enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                        try {
                            int statusCode = response.code();
                            Log.d(TAG, "HTTP status code: " + statusCode);

                            if (response.isSuccessful()) {

                                ResponseMessage responseMessage = response.body();
                                Log.d(TAG, "Response Message Positivo" + responseMessage);
                                Toast.makeText(RegisterActivity.this, responseMessage.getMessage(), Toast.LENGTH_LONG).show();
                                finish();

                            } else {
                                Log.e(TAG, "onError: " + response.errorBody().string());
                                throw new Exception("Error en el servicio");
                            }
                        }catch(Throwable t){
                            try {
                                Log.e(TAG, "onThrowable Mensaje Negativo: " + t.toString(), t);
                                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            } catch (Throwable x) {
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {

                        Log.e(TAG, "onFailure: " + t.toString());
                        Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();


                    }
                });


            }
        });


    }
}
