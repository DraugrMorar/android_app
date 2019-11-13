package network;

import network.req.UserLoginReq;
import network.res.UserModelRes;
import retrofit2.Call;

public class DataManager {
    private UsersApi uApi;
    public DataManager(){
        this.uApi = ServiceGenerator.createService(UsersApi.class);
    }
    public Call<UserModelRes> loginUser(UserLoginReq userLoginReq{
        return uApi.loginUser(userLoginReq);
    }
}
