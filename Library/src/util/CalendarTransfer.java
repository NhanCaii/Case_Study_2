package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalendarTransfer {
    public static Calendar stringToCalendar(String dateString){
//        String dateString = "2023-12-01 21:34:12";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            // Chuyển đổi từ String sang Date
            Date date = dateFormat.parse(dateString);

            // Tạo một đối tượng Calendar và đặt giá trị từ Date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String calendarToString(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(calendar.getTime());

    }

    public static String addDaysToDateString(String inputDateString, int numberOfDaysToAdd) {
        try {
            // Chuyển chuỗi thành đối tượng Calendar
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date inputDate = dateFormat.parse(inputDateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inputDate);

            // Thêm số ngày vào Calendar
            calendar.add(Calendar.DAY_OF_MONTH, numberOfDaysToAdd);

            // Chuyển đối tượng Calendar thành chuỗi
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int calculateDateDifference(String dateString1, String dateString2) {
        try {
            // Định dạng ngày giờ
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Chuyển đổi chuỗi thành đối tượng Date
            Date date1 = dateFormat.parse(dateString1);
            Date date2 = dateFormat.parse(dateString2);

            // Chuyển đối tượng Date thành Calendar
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar1.setTime(date1);
            calendar2.setTime(date2);

            // Tính khoảng cách giữa hai ngày
            long differenceInMillis = calendar2.getTimeInMillis() - calendar1.getTimeInMillis();
            return (int) TimeUnit.MILLISECONDS.toDays(differenceInMillis);
        } catch (ParseException e) {
            e.printStackTrace();
            // Trong trường hợp lỗi, trả về một giá trị âm để chỉ rõ là có lỗi
            return -1;
        }
    }
    public static int calculateDateDifference(Calendar calendar1, Calendar calendar2) {
        try {
            // Tính khoảng cách giữa hai ngày
            long differenceInMillis = calendar2.getTimeInMillis() - calendar1.getTimeInMillis();

            // Chuyển đổi từ long sang int
            return (int) TimeUnit.MILLISECONDS.toDays(differenceInMillis);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }



//    public static void main(String[] args) {
//        // Tạo một đối tượng Calendar với thời gian hiện tại
//        Calendar calendar = Calendar.getInstance();
//
//        // In thời gian được chuyển đổi
//        System.out.println(calendarToString(calendar));
//    }

}
