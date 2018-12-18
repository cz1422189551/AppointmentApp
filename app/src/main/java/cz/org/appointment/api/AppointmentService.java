package cz.org.appointment.api;

import java.util.List;
import java.util.Map;

import cz.org.appointment.entity.Appointment;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface AppointmentService {

    @POST("/appointment/available")
    Call<List<Appointment>> findAvailableInfo(@QueryMap Map<String, String> map);

    @POST("/appointment/add")
    Call<Appointment> appointment(@QueryMap Map<String, String> map);

}
