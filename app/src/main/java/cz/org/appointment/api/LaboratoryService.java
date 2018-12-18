package cz.org.appointment.api;

import java.util.List;

import cz.org.appointment.entity.Laboratory;
import cz.org.appointment.entity.LaboratoryType;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LaboratoryService {

    @GET("laboratory/one/{id}")
    Call<Laboratory> laboratorySeatList(@Path("id") String id);


    //获取所有实验室类型
    @GET("laboratory/type/getAll")
    Call<List<LaboratoryType>> laboratoryAllType();

}
