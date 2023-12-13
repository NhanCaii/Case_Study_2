package view;

import model.BookCategory;
import model.BorrowStatus;
import model.Order;
import model.OrderDetail;
import service.LoginService;
import service.OrderService;

import static util.CalendarTransfer.calculateDateDifference;
import static util.CalendarTransfer.calendarToString;
import static util.FormatNumberWithCommas.formatNumberWithCommas;
import static util.Function.getNumber;
import static util.Function.getString;

public class OrderViewAdmin {
    static OrderService orderService = new OrderService();

    public static void printOrderMenuAdmin(){
        while (true){
            System.out.println("Menu:");
            System.out.println("1. Hien thi tat ca don hang");
            System.out.println("2. Hien thi don hang co ten sach");
            System.out.println("3. Sua don hang");
            System.out.println("4. Xoa don hang");
            System.out.println("5. Chuyen doi trang thai");
            System.out.println("6. Xoa tai khoan");
            System.out.println("7. Dang xuat");
            System.out.println("8. Thoat");
            int choice = getNumber("Nhap vao lua chon");
            switch (choice) {
                case 1 : printOrders(); break;
                case 2 : printInfoIdOrder(); break;
                case 3 : printOrders(); OrderService.modifyOrder(); break;
                case 4 : printOrders(); OrderService.deleteOrderById(); break;
                case 5 : printOrders(); switchBorrowStatus(); break;
                case 6 : LoginService.deleteAccount(); break;
                case 7 : LoginView.printMenu(); break;
                case 8 : System.exit(0);
                default:
                    System.out.println("Sai cu phap, nhap lai: "); printOrderMenuAdmin();
            }
        }
    }

    private static void printOrders(){
        System.out.printf("%10s | %10s | %25s | %25s | %15s | %10s\n","Id Order","Id User","Start Date","End Date","Total","Status");
        for (Order order: orderService.getOrders()) {

            System.out.printf("%10s | %10s | %25s | %25s | %15s | %10s\n",
                    order.getIdOrder(),
                    order.getIdUser(),
                    calendarToString(order.getDateStart()),
                    calendarToString(order.getDateEnd()),
                    formatNumberWithCommas(order.getTotalPrice()*calculateDateDifference(order.getDateStart(),order.getDateEnd())),
                    order.getStatus());
        }

        boolean check=true;
        for (Order order: orderService.getOrders()) {
            if (order.getStatus().equals(BorrowStatus.OUTDATED)){
                System.out.println("Đơn hàng "+order.getIdOrder()+" đã quá hạn trả");
                check=false;
            }
        }
        if (check){
            System.out.println("Không co đơn hàng nào quá hạn trả");
        }
    }
    private static void printInfoIdOrder(){
        System.out.printf("%10s | %10s | %25s | %25s | %15s | %10s\n","Id Order","Id User","Id book","Name","Quantity","Price");
        for (OrderDetail order: orderService.getOrderDetail()) {

            System.out.printf("%10s | %10s | %25s | %25s | %15s | %10s\n",
                    order.getIdOrder(),
                    order.getIdUser(),
                    order.getIdBook(),
                    order.getName(),
                    order.getQuantity(),
                    formatNumberWithCommas(order.getPrice())
            );
        }
    }
    private static void switchBorrowStatus(){
        printOrders();
        int id=getNumber("Nhap id can chuyen doi");
        for (Order order: orderService.getOrders()) {
            if (order.getIdOrder()==id){
                if (order.getStatus().equals(BorrowStatus.BORROW)){
                    order.setStatus(BorrowStatus.RETURN);
                } else if (order.getStatus().equals(BorrowStatus.RETURN)){
                    order.setStatus(BorrowStatus.BORROW);
                }
            }
        }

    }
}
