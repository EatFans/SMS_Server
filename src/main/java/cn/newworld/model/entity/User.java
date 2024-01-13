//
package cn.newworld.model.entity;


public class User {
    private String ID;
    private String name;
    private String password;
    private String email;
    private String permission;
    private boolean isFirstLogin;

    public void setID(String ID){
        this.ID = ID;
    }
    public String getID(){
        return ID;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    public void setPermission(String permission){
        this.permission = permission;
    }
    public String getPermission(){
        return permission;
    }

    public void setFirstLogin(boolean flag){
        this.isFirstLogin = flag;
    }
    public boolean isFirstLogin(){
        return isFirstLogin;
    }
}
