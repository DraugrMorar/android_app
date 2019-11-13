package network.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModelRes {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("password")
    @Expose
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
