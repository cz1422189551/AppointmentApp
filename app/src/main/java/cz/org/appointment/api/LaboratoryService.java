package cz.org.appointment.api;

import java.util.List;

import cz.org.appointment.entity.LaboratorySeat;
import cz.org.appointment.entity.core.LaboratoryTypeEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LaboratoryService {

    @GET("laboratory/seat/one/{id}")
    Call<List<LaboratorySeat>> laboratorySeatList(@Path("id") String id);


    //获取所有实验室类型
    @GET("laboratory/type/getAll")
    Call<List<LaboratoryTypeEntity>> laboratoryAllType();

}
