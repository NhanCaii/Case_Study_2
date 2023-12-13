package view;

import model.Book;
import service.BookService;

import java.util.List;
import java.util.Scanner;

import static util.FormatNumberWithCommas.formatNumberWithCommas;

public class BookView {
    static Scanner sc=new Scanner(System.in);

    public static void printBookMenu() {
        while (true){
            System.out.println("Menu:");
            System.out.println("1. In danh sach");
            System.out.println("2. Them sach");
            System.out.println("3. Xoa sach");
            System.out.println("4. Chinh sua sach");
            System.out.println("5. Sap xep sach");
            System.out.println("6. Tim kiem sach");
            System.out.println("7. Dang xuat");
            System.out.println("8. Thoat");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1: printBookList(); break;
                case 2: BookService.createBook(); break;
                case 3: BookService.deleteBook(); break;
                case 4: BookService.updateBook(); break;
                case 5: {
                    System.out.println("1. Sap xep ten a-z");
                    System.out.println("2. Sap xep so luong thap den cao");
                    System.out.println("3. Sap xep gia tien tu thap den cao");
                    System.out.println("Lua chon cach sap xep");
//                    int choo = sc.nextInt();
                    int choo=Integer.parseInt(sc.nextLine());
                    switch (choo){

                        case 1:
                            List<Book> bookInName= BookService.sortName();
                            System.out.printf("%5s | %20s | %10s | %15s | %20s | %15s\n", "ID", "Name", "Quantity", "Category", "Condition","Price");

                            for (Book book:bookInName) {
                                System.out.printf("%5s | %20s | %10s | %15s | %20s | %15s\n", book.getId(), book.getName(), book.getQuantity(),book.getCategory(), book.getCondition(),formatNumberWithCommas(book.getPrice()));
                            }
                            break;
                        case 2:
                            List<Book> bookInQuan= BookService.sortQuantity();
                            System.out.printf("%5s | %20s | %10s | %15s | %20s | %15s\n", "ID", "Name", "Quantity", "Category", "Condition","Price");

                            for (Book book:bookInQuan) {
                                System.out.printf("%5s | %20s | %10s | %15s | %20s | %15s\n", book.getId(), book.getName(), book.getQuantity(),book.getCategory(), book.getCondition(),formatNumberWithCommas(book.getPrice()));
                            }
                            break;
                        case 3:
                            List<Book> bookInPrice= BookService.sortPrice();
                            System.out.printf("%5s | %20s | %10s | %15s | %20s | %15s\n", "ID", "Name", "Quantity", "Category", "Condition","Price");

                            for (Book book:bookInPrice) {
                                System.out.printf("%5s | %20s | %10s | %15s | %20s | %15s\n", book.getId(), book.getName(), book.getQuantity(),book.getCategory(), book.getCondition(),formatNumberWithCommas(book.getPrice()));
                            }
                            break;
                    }
                } break;
                case 6: BookService.searchName(); break;
                case 7: LoginView.printMenu(); break;
                case 8:  System.exit(0);
                default:
                    System.out.println("Sai cu phap, nhap lai: "); printBookMenu();
            }
        }


    }

     static void printBookList(){
        System.out.printf("%5s | %20s | %10s | %15s | %20s | %15s\n", "ID", "Name", "Quantity", "Category", "Condition","Price");
        for (Book book: BookService.getBookList()){
            System.out.printf("%5s | %20s | %10s | %15s | %20s | %15s\n", book.getId(), book.getName(), book.getQuantity(),book.getCategory(), book.getCondition(),formatNumberWithCommas(book.getPrice()));
        }
    }
}
