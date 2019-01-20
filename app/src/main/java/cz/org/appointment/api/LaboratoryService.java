package cz.org.appointment.api;

import java.util.List;
import java.util.Map;

import cz.org.appointment.entity.Laboratory;
import cz.org.appointment.entity.LaboratoryType;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface LaboratoryService {



    //获取所有实验室类型
    @GET("laboratory/type/getAll")
    Call<List<LaboratoryType>> laboratoryAllType(@QueryMap Map<String, Integer> map);


    //分页获取实验室信息
    @GET("laboratory/getList")
    Call<Result<Laboratory>> getLaboratoryList(@QueryMap Map<String, Integer> map);


}
