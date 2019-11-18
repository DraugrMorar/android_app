package network;

import java.util.List;

//import network.req.UserLoginReq;
//import network.res.UserModelRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsersApi {
    @GET("all")
    Call<List<Users>> getAll();

    @GET("Items_U/{user_id}")
    Call<List<ToDoList>> getListForUser(@Path("user_id") Integer user_id);

//    @POST("/add/{name}/{password}")
//    Call<List<Users>> addUser(@Path("name") String login, @Path("password") String password);
}
