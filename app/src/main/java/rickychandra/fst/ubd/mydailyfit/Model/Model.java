package rickychandra.fst.ubd.mydailyfit.Model;

/**
 * Created by Ricky on 12/30/2017.
 */

public class Model {
    private Integer user_id;
    private String name;
    private String username;
    private String password;

    public Model() {
    }

    public Model(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Model(Integer user_id, String name, String username, String password) {
        this.user_id = user_id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
