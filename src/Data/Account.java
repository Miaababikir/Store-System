package Data;

/**
 * Created by Mosab on 3/27/2017.
 */
public abstract class Account {

    private int ID;
    private String username;
    private String password;

    public abstract int getID();

    public abstract void setID(int ID);

    public abstract String getUsername();

    public abstract void setUsername(String username);

    public abstract String getPassword();

    public abstract void setPassword(String password);

    public abstract void login();

}
