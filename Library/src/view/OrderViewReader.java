package view;

import model.BorrowStatus;
import model.Order;
import model.OrderDetail;
import service.BookService;
import service.OrderService;

import java.util.ArrayList;
import java.util.List;

import static util.CalendarTransfer.calculateDateDifference;
import static util.CalendarTransfer.calendarToString;
import static util.FormatNumberWithCommas.formatNumberWithCommas;
import static util.Function.getNumber;
import static util.Function.getNumberOfBook;

public class OrderViewReader {
    static OrderService orderService = new OrderService();

    public static void printOrderMenu(){
        while (true){
            System.out.println("Menu:");
            System.out.println("1. Hien thi Don hang");
            System.out.println("2. Hien thi don hang co ten sach");
            System.out.println("3. Tao moi don hang");
            System.out.println("4. Sua don hang");
            System.out.println("5. Xoa don hang");
            System.out.println("6. Dang xuat");
            System.out.println("7. Thoat");
            int choice = getNumber("Nhap vao lua chon");
            switch (choice) {
                case 1 : printOrders(); break;
                case 2 : printInfoIdOrder(); break;
                case 3 : createOrder(); break;
                case 4 : printOrders(); OrderService.modifyOrder(); break;

                case 5 : printOrders(); OrderService.deleteOrderById(); break;

                case 6 : LoginView.printMenu(); break;
                case 7 : System.exit(0);
                default:
                    System.out.println("Sai cu phap, nhap lai: "); printOrderMenu();
            }
        }
    }

    private static void printOrders(){
        System.out.printf("%10s | %10s | %25s | %25s | %15s | %10s\n","Id Order","Id User","Start Date","End Date","Total","Status");
        for (Order order: orderService.getOrderByUser()) {

            System.out.printf("%10s | %10s | %25s | %25s | %15s | %10s\n",
                    order.getIdOrder(),
                    order.getIdUser(),
                    calendarToString(order.getDateStart()),
                    calendarToString(order.getDateEnd()),
                    formatNumberWithCommas(order.getTotalPrice()*calculateDateDifference(order.getDateStart(),order.getDateEnd())),
                    order.getStatus());
        }

        boolean check=true;
        for (Order order: orderService.getOrderByUser()) {
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
        for (OrderDetail order: orderService.getOrderDetailByUser()) {

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
    static void createOrder(){
        List<OrderDetail> orderDetailList=new ArrayList<>();
        while (true){
            BookView.printBookList();
            int productId = getNumber("Nhap id sach muon muon");
//            int quantity = getNumber("nhap so luong sach muon muon");
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setIdBook(productId);

            int correctQuantity=getNumberOfBook(productId);
            orderDetail.setQuantity(correctQuantity);
            BookService.addOrder(productId,correctQuantity);

            orderDetailList.add(orderDetail);
            System.out.println("1. Chon tiep sach ?");
            System.out.println("2. Xong");
            int choice = getNumber("Nhap 1, 2");
            if(choice == 2) break;
        }
        int numOfBorrowDay=getNumber("Nhap so ngay muon sach");

        OrderService.createOrder(orderDetailList,numOfBorrowDay);

    }
}
