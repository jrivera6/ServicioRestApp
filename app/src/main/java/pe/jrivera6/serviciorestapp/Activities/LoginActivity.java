package pe.jrivera6.serviciorestapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pe.jrivera6.serviciorestapp.R;
import pe.jrivera6.serviciorestapp.Services.ApiService;
import pe.jrivera6.serviciorestapp.Services.ApiServiceGenerator;
import pe.jrivera6.serviciorestapp.models.ResponseMessage;
import pe.jrivera6.serviciorestapp.models.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private SharedPreferences preferences;
    private Button btnLogin, btnRegistro;
    private EditText txtEmail, txtPass;
    Usuario user = null;
    String nombre = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegistro = findViewById(R.id.btn_registro);
        btnLogin = findViewById(R.id.btn_login);
        txtEmail = findViewById(R.id.txt_email);
        txtPass = findViewById(R.id.txt_password);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String  correo = preferences.getString("correo",null);
        if (correo!=null){
            txtEmail.setText(correo);
            txtPass.requestFocus();
        }

        if(preferences.getBoolean("islogged", false)) {
            // Go to Dashboard
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.putExtra("nombre",nombre);
            startActivity(i);
            finish();

        }



            btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String correo = txtEmail.getText().toString();
                String contraseña = txtPass.getText().toString();

                ApiService service = ApiServiceGenerator.createService(ApiService.class);
                Call<Usuario> call = null;
                call = service.findUSer(correo,contraseña);
                call.request().body();

                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                        try {
                            int statusCode = response.code();
                            Log.d(TAG, "HTTP status code: " + statusCode);

                            if (response.isSuccessful()) {

                                user = response.body();
                                nombre = user.getNombres();


                                SharedPreferences.Editor editor = preferences.edit();
                                boolean success = editor
                                                .putString("correo",user.getCorreo())
                                                .putBoolean("islogged",true)
                                                .commit();


                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.putExtra("nombre",nombre);
                                startActivity(i);
                                Log.d(TAG, "Response Message Positivo" + user);

                                finish();

                            } else {
                                Log.e(TAG, "onError: " + response.errorBody().string());
                                throw new Exception("Error en el servicio");
                            }
                        }catch(Throwable t){
                            try {
                                Log.e(TAG, "onThrowable Mensaje Negativo: " + t.toString(), t);
                                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            } catch (Throwable x) {
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {

                        Log.e(TAG, "onFailure: " + t.toString());
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();


                    }
                });

            }
        });

    }
}
