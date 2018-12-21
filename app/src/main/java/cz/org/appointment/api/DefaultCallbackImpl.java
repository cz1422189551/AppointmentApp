package cz.org.appointment.api;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class DefaultCallbackImpl<T> implements Callback<T> {

    private Context context;

    public DefaultCallbackImpl(Context context) {
        this.context = context;
    }


    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();
    }
}
