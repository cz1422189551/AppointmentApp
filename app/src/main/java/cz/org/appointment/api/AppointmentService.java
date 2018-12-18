package cz.org.appointment.api;

import java.util.List;
import java.util.Map;

import cz.org.appointment.entity.Appointment;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface AppointmentService {

    @POST("/appointment/available")
    Call<List<Appointment>> findAvailableInfo(@QueryMap Map<String,String> map);

}
