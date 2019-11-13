package network;

import java.util.List;

import network.req.UserLoginReq;
import network.res.UserModelRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UsersApi {
//    @GET("all")
//    Call<List<Users>> getAll();

    @POST()
    Call<UserModelRes> loginUser(@Body UserLoginReq req);

}
