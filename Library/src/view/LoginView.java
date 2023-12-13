package view;

import model.RoleLogin;
import service.LoginService;


import static util.Function.getNumber;
import static util.Function.getString;

public class LoginView {
    private static LoginService loginService= new LoginService();
    public static void printMenu(){
        System.out.println("1. Dang Nhap");
        System.out.println("2. Dang ky");
        int choice= getNumber("Nhap lua chon");
        if (choice==1){
            String username=getString("Nhap user:");
            String password=getString("Nhap pass:");
            RoleLogin role=loginService.login(username,password);
            if (role==null){
                System.out.println("tai khoan hoac mat khau khong dung");
                printMenu();
            } else if (loginService.checkBan(username,password)){
                System.out.println("Tai khoan bi khoa !");
                printMenu();
            } else if (role==RoleLogin.USER){ //USER
                OrderViewReader.printOrderMenu();

            } else {
                while(true){ //ADMIN
                    System.out.println("1. Product View");
                    System.out.println("2. Order view");
                    int choice1=getNumber("Nhap lua chon: ");
                    if (choice1==1){
                        BookView.printBookMenu();
                    } else if (choice1==2) {
                        OrderViewAdmin.printOrderMenuAdmin();
                    } else  {
                        System.out.println("Sai cu phap, quay tro lai dang nhap");
                        printMenu();
                    }

                }
            }


        } else if (choice==2){ //Dang ki
            String username = getString("Nhap username");
            String password = getString("Nhap password");
            boolean check=loginService.register(username, password);
            if (check){
                System.out.println("Dang ky thanh cong");
            } else {
                System.out.println("fail ?");
            }

            printMenu();
        } else {
            System.out.println("sai cu phap, nhap lai");
            printMenu();
        }

    }
}
