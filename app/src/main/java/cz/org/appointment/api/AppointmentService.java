package cz.org.appointment.api;

import java.util.List;
import java.util.Map;


import cz.org.appointment.entity.Appointment;
import cz.org.appointment.entity.Laboratory;
import cz.org.appointment.entity.ResponseEntity;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface AppointmentService {

    //查询实验室座位数量
    @POST("/appointment/available")
    Call<List<Appointment>> findAvailableInfo(@QueryMap Map<String, String> map);

    //提交预约
    @POST("/appointment/add")
    Call<ResponseEntity<Appointment>> appointment(@QueryMap Map<String, String> map);

    //取消预约
    @POST("/appointment/cancel")
    Call<ResponseEntity> cancelAppointment(@QueryMap Map<String, String> map);


    //获取预约列表
    @GET("/appointment/getList")
    Call<Result<Appointment>> appointmentList(@QueryMap Map<String, Integer> map);

    //模糊查询,按日期 ,名称
    @GET("appointment/getListByName")
    Call<Result<Appointment>> findInfo(@QueryMap Map<String, String> map);

}
