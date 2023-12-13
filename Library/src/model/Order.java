package model;

import java.util.Calendar;
import java.util.List;

import static util.CalendarTransfer.calendarToString;

public class Order {
    private int idOrder;
    private int idUser;
    private Calendar dateStart;
    private Calendar dateEnd;
    private List<OrderDetail> orderDetails;
    private BorrowStatus status;

    public BorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowStatus status) {
        this.status = status;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Calendar getDateStart() {
        return dateStart;
    }

    public void setDateStart(Calendar dateStart) {
        this.dateStart = dateStart;
    }

    public Calendar getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }


    @Override
    public String toString(){
        return idOrder +","+idUser+","+calendarToString(dateStart)+","+ calendarToString(dateEnd) +","+ status;
    }
    public int getTotalPrice(){
        int total= 0;
        for (OrderDetail orderDetail: orderDetails){
            total=total+(orderDetail.getPrice()*orderDetail.getQuantity());
        }
        return total;
    }
}
