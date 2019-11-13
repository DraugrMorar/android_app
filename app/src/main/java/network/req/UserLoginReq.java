package network.req;


public class UserLoginReq {
    private int id;
    public String name;
    private String password;


    public UserLoginReq(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
