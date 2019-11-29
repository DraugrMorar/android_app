package network;

import java.util.Date;
import java.util.List;

//import network.req.UserLoginReq;
//import network.res.UserModelRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsersApi {

    @GET("users/login/{name}/{password}")
    Call<Users> getUser(@Path("name") String name, @Path("password") String password);

    @GET("list/Items_U/{user_id}")
    Call<List<ToDoList>> getListForUser(@Path("user_id") int user_id);

    @GET("users/add/{name}/{password}")
    Call<Users> addUser(@Path("name") String login, @Path("password") String password);

    @GET("list/corr_item/{list_id}/{title}/{description}/{date}/{status}/{priority}/{item_id}")
    Call<List<ToDoList>> editTask(@Path("list_id") int list_id,
                                  @Path("title") String title,
                                  @Path("description") String description,
                                  @Path("date") String date,
                                  @Path("status") int status,
                                  @Path("priority") int priority,
                                  @Path("item_id") int item_id);

    @GET("list/one_item/{item_id}")
    Call<List<ToDoList>> getOneTask(@Path("item_id") int item_id);


    @GET("list/add_by_U/{users_id}/{title}/{description}/{date}/{status}/{priority}")
    Call<List<ToDoList>> addTask(@Path("users_id") int users_id,
                                 @Path("title") String title,
                                 @Path("description") String description,
                                 @Path("date") String date,
                                 @Path("status") int status,
                                 @Path("priority") int priority);

    @GET("list/del_item/{list_id}/{item_id}")
    Call<List<ToDoList>> deleteTask(@Path("list_id") int list_id,
                                    @Path("item_id") int item_id);

    @GET("users/change/{name}/{password}/{new_password}")
    Call<List<Users>> changePass(@Path("name") String name, @Path("password") String password, @Path("new_password") String newPass);

    @GET("list/Items_Name/{user_id}")
    Call<List<ToDoList>> getListForUserbyTitle(@Path("user_id") int user_id);

    @GET("list/Items_Priority/{user_id}")
    Call<List<ToDoList>> getListForUserbyPriority(@Path("user_id") int user_id);

    @GET("list/Items_Status/{user_id}")
    Call<List<ToDoList>> getListForUserbyStatus(@Path("user_id") int user_id);

}
