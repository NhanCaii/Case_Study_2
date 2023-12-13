package service;

import model.BanStatus;
import model.RoleLogin;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static util.Function.getNumber;

public class LoginService {
    private static List<User> users = new ArrayList<>();
    private static int currentId=0;
    private static User currentUserLogin = new User();
    static{
        readData();
        OrderService.checkOutdated7Days();
    }
    public static User currentUser(){
        return currentUserLogin;
    }




    public RoleLogin login(String username,String userpassword){
        currentUserLogin=users.stream().filter(e-> Objects.equals(e.getUsername(),username) &&
                Objects.equals(e.getPassword(),userpassword)).findFirst().orElse(new User());
        return currentUserLogin.getRole();
    }
    public boolean checkBan(String username,String userpassword){ //Tra ve true neu bi ban
        currentUserLogin=users.stream().filter(e-> Objects.equals(e.getUsername(),username) &&
                Objects.equals(e.getPassword(),userpassword)).findFirst().orElse(new User());
        return currentUserLogin.getBan().equals(BanStatus.TRUE);
    }

    public static int deleteAccount(){
        System.out.printf("%5s | %10s | %10s | %10s\n ","IdUser","UserName","Role","BanStatus");
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getRole()==RoleLogin.USER){
                System.out.printf("%5s | %10s | %10s | %10s\n ",users.get(i).getIdUser(),users.get(i).getUsername(),users.get(i).getRole(),users.get(i).getBan());
            }
        }

        int userId=getNumber("Nhap user id can xoa: ");

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getIdUser()==userId){
                users.remove(i);
                writeFile();
                return userId;
            }
        }
        return -1;
    }
    public static void applyBan(int id){
        for(User user:users){
            if (user.getIdUser()==id){
                user.setBan(BanStatus.TRUE);
            }
        }
        writeFile();
    }

    public boolean register(String username,String userpassword){
        if (users.stream().anyMatch(e->Objects.equals(e.getUsername(), username))){
            return false;
        }
        User user=new User();
        user.setIdUser(++currentId);
        user.setUsername(username);
        user.setPassword(userpassword);
        user.setRole(RoleLogin.USER);
        user.setBan(BanStatus.FALSE);
        users.add(user);
        writeFile();
        return true;
    }
    private static void writeFile(){
        try{
            BufferedWriter writer=new BufferedWriter(new FileWriter("data/user.txt"));
            for (User user:users){
                writer.write(user.toString());
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private static void readData(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(new File("data/user.txt")));
            String line= reader.readLine();
            while (line!=null && !line.equals("")){
                String[] data=line.split(",");
                currentId=Integer.parseInt(data[0]);
                User user=new User();
                user.setIdUser(currentId);
                user.setUsername(data[1]);
                user.setPassword(data[2]);
                user.setRole(RoleLogin.valueOf(data[3]));
                user.setBan(BanStatus.valueOf(data[4]));

                line=reader.readLine();
                users.add(user);
            }

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
