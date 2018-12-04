package examples.my.android.retrofitsample.api;

import examples.my.android.retrofitsample.pojo.AnekdotModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UmoriliApi {

    @GET("api/get")
    Call<List<AnekdotModel>> getData(@Query("site") String siteName, @Query("name") String resourceName, @Query("num") int count);
}
