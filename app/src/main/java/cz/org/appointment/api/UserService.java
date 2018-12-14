package cz.org.appointment.api;

import java.util.Map;

import cz.org.appointment.entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface UserService {

    @POST("/app/login")
    Call<User> login(@QueryMap Map<String, String> map);

}