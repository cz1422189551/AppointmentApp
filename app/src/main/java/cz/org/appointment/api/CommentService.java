package cz.org.appointment.api;

import java.util.Map;


import cz.org.appointment.entity.Comment;
import cz.org.appointment.entity.ResponseEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface CommentService {

    //我的评论列表
    @GET("/comment/getList")
    Call<Result<Comment>> getList(@QueryMap Map<String, String> map);

    //实验室的评论
    @GET("/comment/getList/laboratory")
    Call<Result<Comment>> getListInLaboratory(@QueryMap Map<String, String> map);

    //发表评论
    @GET("/comment/add")
    Call<ResponseEntity<Comment>> comment(@QueryMap Map<String, String> map);
}
