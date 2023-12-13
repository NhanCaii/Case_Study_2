package model;

public class User {
    private int idUser;
    private String username;
    private String password;
    private RoleLogin role;
    private BanStatus ban;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public RoleLogin getRole() {
        return role;
    }

    public void setRole(RoleLogin role) {
        this.role = role;
    }

    public BanStatus getBan() {
        return ban;
    }

    public void setBan(BanStatus ban) {
        this.ban = ban;
    }

    @Override
    public String toString(){
        return idUser+","+username+ "," + password + "," + role+","+ban;
    }
}
