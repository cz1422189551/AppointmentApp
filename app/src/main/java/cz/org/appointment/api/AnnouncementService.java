package cz.org.appointment.api;

import java.util.Map;

import cz.org.appointment.entity.Announcement;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface AnnouncementService {

    @GET("/announcement/getList")
    Call<Result<Announcement>> getList(@QueryMap Map<String, Integer> map);

}
