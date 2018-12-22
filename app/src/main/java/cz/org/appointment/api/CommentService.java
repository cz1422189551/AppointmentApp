package cz.org.appointment.api;

import java.util.Map;


import cz.org.appointment.entity.Comment;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface CommentService {

    @GET("/comment/getList")
    Call<Result<Comment>> getList(@QueryMap Map<String, String> map);

    @GET("/comment/getList/laboratory")
    Call<Result<Comment>> getListInLaboratory(@QueryMap Map<String, String> map);
}
