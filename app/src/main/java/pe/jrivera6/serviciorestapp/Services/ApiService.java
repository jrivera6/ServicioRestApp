package pe.jrivera6.serviciorestapp.Services;

import pe.jrivera6.serviciorestapp.models.ResponseMessage;
import pe.jrivera6.serviciorestapp.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    String API_BASE_URL = "https://proyecto-arduino-api-jrivera6.c9users.io";

    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<Usuario> findUSer(
            @Field("correo")String correo,
            @Field("contraseña")String contraseña
    );


    @FormUrlEncoded
    @POST("/api/v1/usuarios")
    Call<ResponseMessage> createUser(
            @Field("nombres")String nombres,
            @Field("correo")String correo,
            @Field("contraseña")String contraseña,
            @Field("tipoUsuario")String tipoUsuario
    );
}
