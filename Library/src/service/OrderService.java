package service;

import model.Book;
import model.BorrowStatus;
import model.Order;
import model.OrderDetail;
import util.CalendarTransfer;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static util.Function.getNumber;


public class OrderService {
    static Random rand= new Random();
    private static List<Order> orders=new ArrayList<>();
    private static List<OrderDetail> orderDetailList=new ArrayList<>();
    private static BookService bookService= new BookService();
    public List<Order> getOrders(){return orders;}
    public List<Order> getOrderByUser(){
        return orders.stream().filter(e -> e.getIdUser()==LoginService.currentUser().getIdUser()).collect(Collectors.toList());
    }
    public List<OrderDetail> getOrderDetail(){return orderDetailList;}
    public List<OrderDetail> getOrderDetailByUser(){
        return orderDetailList.stream().filter(e -> e.getIdUser()==LoginService.currentUser().getIdUser()).collect(Collectors.toList());
    }
    private static int currentIdOrderDetail=0;
    static {
        readDataOrderDetail();
        readDataOrder();
        checkOutdated();
    }

    public static int makeNewRdNumber(){
        int rdNum=rand.nextInt((999 - 100) + 1) + 100;
        for (Order orde: orders){
            if (orde.getIdOrder()==rdNum){
                makeNewRdNumber();
            }
        }
        return rdNum;
    }

    
    public static void checkOutdated(){

        for (Order order:orders){
            Calendar sevenDaysBeforeNow = Calendar.getInstance();
//            sevenDaysBeforeNow.add(Calendar.DAY_OF_MONTH, -7);

            boolean check=order.getDateEnd().before(sevenDaysBeforeNow);
            // TRUE->outDated
            // false ->ko outdated-> con han su dung
            if (check && order.getStatus().equals(BorrowStatus.BORROW)){
                order.setStatus(BorrowStatus.OUTDATED);
            }
        }
        writeData();
    }
    public static void checkOutdated7Days(){

        for (Order order:orders){
            Calendar sevenDaysBeforeNow = Calendar.getInstance();
            sevenDaysBeforeNow.add(Calendar.DAY_OF_MONTH, -14);

            boolean check=order.getDateEnd().before(sevenDaysBeforeNow);
            // TRUE->outDated
            // false ->ko outdated-> con han su dung
            if (check){
                LoginService.applyBan(order.getIdUser());
            }
        }
        writeData();
    }

    public static void createOrder(List<OrderDetail> orderDetails, int numOfBorrowDay){
        Order order = new Order();
        order.setDateStart(Calendar.getInstance());
        Calendar resultCalendar = (Calendar) Calendar.getInstance().clone();
        resultCalendar.add(Calendar.DAY_OF_MONTH, numOfBorrowDay);

        order.setDateEnd(resultCalendar);
        order.setStatus(BorrowStatus.BORROW);
        order.setIdOrder(makeNewRdNumber()); //IdOrder, data[0] order.txt

        order.setIdUser(LoginService.currentUser().getIdUser());


        for (OrderDetail orderDetail: orderDetails){
            Book product = bookService.findById(orderDetail.getIdBook());

            orderDetail.setIdOrder(order.getIdOrder());
            orderDetail.setIdUser(order.getIdUser());
            orderDetail.setIdBook(product.getId());

            orderDetail.setName(product.getName());
            orderDetail.setQuantity(orderDetail.getQuantity());
            orderDetail.setPrice(product.getPrice());

            orderDetailList.add(orderDetail);

        }
        order.setOrderDetails(orderDetails);
        orders.add(order);
        writeData();
    }
    public static void deleteOrderById(){
        boolean checked=false;
        int orderId=getNumber("Nhap OrderId can xoa: ");
        for (int i=0; i<orders.size();i++){
            if (orders.get(i).getIdOrder()==orderId){

                for (OrderDetail orderDetail:orderDetailList){
                    if (orderDetail.getIdOrder()==orderId){
                        BookService.deleteOrder(orderDetail.getIdBook(),orderDetail.getQuantity());
                    }
                }

                orders.remove(i);
                writeData();
                checked=true;
            }
        }
        for (int i = 0; i < orderDetailList.size(); i++) {
            if (orderDetailList.get(i).getIdOrder()==orderId){
                orderDetailList.remove(i);
            }
        }
        if (checked){
            System.out.println("Remove successfully");
        } else {
            System.out.println("Remove fail");
        }
    }

    public static void modifyOrder(){
        boolean checked=false;
        int OrderId=getNumber("Nhap OrderId can chinh sua: ");
        for (int i=0; i<orderDetailList.size();i++){
            if (orderDetailList.get(i).getIdOrder()==OrderId){
                System.out.println("Đang sửa số lượng sách "+orderDetailList.get(i).getName());
                int oldNumOfBook=orderDetailList.get(i).getQuantity();

                int newQuantity=getNumber("Nhap so luong sach moi");
                BookService.modifyOrder(orderDetailList.get(i).getIdBook(),newQuantity,oldNumOfBook);

                orderDetailList.get(i).setQuantity(newQuantity);
                checked=true;
            }
        }
        writeData();

        if (checked){
            System.out.println("Change successfully");
        } else {
            System.out.println("Change fail");
        }

    }


    private static void readDataOrderDetail(){
        try{
            File fileWriter = new File("data/order_detail.txt");
            FileReader fileReader=new FileReader(fileWriter);
            BufferedReader reader=new BufferedReader(fileReader);
            String line=reader.readLine();
            while (line!=null && !line.equals("")){
                String[] data = line.split(",");
                currentIdOrderDetail = Integer.parseInt(data[0]);
                OrderDetail orderDetail = new OrderDetail();

                orderDetail.setIdOrder(Integer.parseInt(data[0]));
//                orderDetail.setBuyDate(Date.valueOf(data[1]));
//                orderDetail.setBuyDate(Instant.parse(data[1]).atZone(ZoneId.of("Asia/Ho_Chi_Minh")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                orderDetail.setIdUser(Integer.parseInt(data[1]));
                orderDetail.setIdBook(Integer.parseInt(data[2]));
                orderDetail.setName(data[3]);
                orderDetail.setQuantity(Integer.parseInt(data[4]));
                orderDetail.setPrice(Integer.parseInt(data[5]));

                orderDetailList.add(orderDetail);
                line=reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void readDataOrder(){
        try{
            File fileWriter = new File("data/order.txt");
            FileReader fileReader = new FileReader(fileWriter);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while (line != null && !line.equals("")){
                String[] data = line.split(",");
                currentIdOrderDetail = Integer.parseInt(data[0]);
                Order order=new Order();
                order.setIdOrder(Integer.parseInt(data[0]));
//                order.setBuyDate(Date.valueOf(data[1]));
                order.setIdUser(Integer.parseInt(data[1]));

                order.setDateStart(CalendarTransfer.stringToCalendar(data[2]));
                order.setDateEnd(CalendarTransfer.stringToCalendar(data[3]));
                order.setStatus(BorrowStatus.valueOf(data[4]));

                order.setOrderDetails(
                        orderDetailList.stream().filter(e -> e.getIdOrder()==order.getIdOrder()).collect(Collectors.toList())
                );

                orders.add(order);
                line=reader.readLine();
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private static void writeData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/order.txt"));
            BufferedWriter writerOrderDetail = new BufferedWriter(new FileWriter("data/order_detail.txt"));
            for (Order order : orders) {
                writer.write(order.toString());
                writer.newLine();
                for (OrderDetail orderDetails : order.getOrderDetails()) {
                    writerOrderDetail.write(orderDetails.toString());
                    writerOrderDetail.newLine();
                }
            }

            writer.flush();
            writer.close();
            writerOrderDetail.flush();
            writerOrderDetail.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
