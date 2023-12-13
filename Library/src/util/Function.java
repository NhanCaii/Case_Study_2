package util;

import model.Book;
import model.OrderDetail;
import service.BookService;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class Function {
    static Scanner sc = new Scanner(System.in);
    private static BookService bookService= new BookService();

    public static int getNumber(String str) throws IndexOutOfBoundsException {
        System.out.println(str);
        int num;
        try {
            num = Integer.parseInt(sc.nextLine());
            return num;
        } catch (Exception e) {
            System.out.println("Khong dung dinh dang");
            return getNumber(str);
        }
    }
    public static String getString(String str) throws IndexOutOfBoundsException{
        System.out.println(str);
        return sc.nextLine();
    }

    public static int getNumberOfBook(int idBook) {
        Book product = bookService.findById(idBook);
        boolean check=true;
        int numofBo=0;
        while(check) {
            System.out.println("Nhap so luong sach muon muon");
            numofBo= Integer.parseInt(sc.nextLine());
            if (product.getQuantity() < numofBo) {
                System.out.println("Không đủ sách để cho muượn, chỉ có " + product.getQuantity() + " sách, nhập lại");
            } else {
                System.out.println("Đã nhập đủ số lượng sách");
                check=false;
            }
        }
        return numofBo;
    }
}
