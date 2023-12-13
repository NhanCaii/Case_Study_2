package service;

import model.Book;
import model.BookCategory;
import util.CalendarTransfer;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static util.FormatNumberWithCommas.formatNumberWithCommas;
import static util.Function.getNumber;
import static util.Function.getString;

public class BookService {
    public static Scanner sc=new Scanner(System.in);
    public static int currentId=0;
    public static List<Book> bookList=new ArrayList<>();
    public static List<Book> getBookList(){return bookList;}
    static {readData();}

    public Book findById(int id){
        return bookList.stream().filter(e -> e.getId()==id).findFirst().orElse(new Book());
    }
    public static void createBook(){
        String name=getString("Nhap ten");
        int quantity=getNumber("Nhap so luong");

        System.out.print("Nhap: ");
        for (BookCategory BCate : BookCategory.values()) {
            System.out.printf("%s.%s ", BCate.getId(), BCate.getName());
        }

        int idBCate = Integer.parseInt(sc.nextLine());
        BookCategory BCategory = BookCategory.getBy(idBCate);
        String condition=getString("Nhap dieu kien");
        int price=getNumber("Nhap so tien");

        bookList.add(new Book(++currentId,name,quantity,BCategory,condition,price));
        writeData();

    }
    public static void deleteBook(){
        System.out.print("Nhap ID can xoa: ");
        int id=Integer.parseInt(sc.nextLine());
        try {
            bookList.remove(id - 1);
            System.out.println("Remove successfully");
            writeData();
        } catch ( Exception e){
            System.out.println("Remove fail");
        }
    }
    public static void updateBook(){
        int id=getNumber("Nhap Id can cap nhat");
        String name=getString("Nhap ten");
        int quantity=getNumber("Nhap so luong");

        System.out.print("Nhap: ");
        for (BookCategory BCate : BookCategory.values()) {
            System.out.printf("%s.%s ", BCate.getId(), BCate.getName());
        }

        int idBCate = Integer.parseInt(sc.nextLine());
        BookCategory BCategory = BookCategory.getBy(idBCate);
        String condition=getString("Nhap dieu kien");
        int price=getNumber("Nhap so tien");

        boolean check=false;
        for (Book book: bookList){
            if(book.getId()==id){
                book.setName(name);
                book.setQuantity(quantity);
                book.setCategory(BCategory);
                book.setCondition(condition);
                book.setPrice(price);
                check=true;
            }
        }
        if (check){
            System.out.println("Cap nhat thanh cong");
        } else{
            System.out.println("Cap nhat that bai");
        }

        writeData();
    }

    public static List<Book> sortName(){
        return bookList.stream()
                .sorted((Comparator.comparing(Book::getName)))
                .collect(Collectors.toList());
    }
    public static List<Book> sortQuantity(){
        return bookList.stream()
                .sorted((Comparator.comparing(Book::getQuantity)))
                .collect(Collectors.toList());
    }
    public static List<Book> sortPrice(){
        return bookList.stream()
                .sorted((Comparator.comparing(Book::getPrice)))
                .collect(Collectors.toList());
    }

    public static void searchName(){
        boolean title=true;
        System.out.println("Nhap ten can tim");
        String name=sc.nextLine();
        for (Book book:bookList){
            if(book.getName().contains(name)){
                if (title){
                    System.out.printf("%5s | %20s | %10s | %15s | %20s | %15s\n", "ID", "Name", "Quantity", "Category", "Condition","Price");
                    title=false;
                }
                System.out.printf("%5s | %20s | %10s | %15s | %20s | %15s\n", book.getId(), book.getName(), book.getQuantity(),book.getCategory(), book.getCondition(),formatNumberWithCommas(book.getPrice()));

            }
        }

    }
    public static void addOrder(int id, int newBook){ //xoa order, so luong sach tra ve kho
        for (Book book:bookList){
            if (book.getId()==id){
                int num=book.getQuantity();
                book.setQuantity(num-newBook);
            }
        }
        writeData();
    }
    public static void modifyOrder(int id, int newBook, int oldBook){ //chinh sua order
        for (Book book:bookList){
            if (book.getId()==id){
                int num=book.getQuantity();
                book.setQuantity(num+oldBook-newBook);
            }
        }
        writeData();
    }
    public static void deleteOrder(int id, int oldBook){ //xoa order, so luong sach tra ve kho
        for (Book book:bookList){
            if (book.getId()==id){
                int num=book.getQuantity();
                book.setQuantity(num+oldBook);
            }
        }
        writeData();
    }

    public static void readData(){
        try{
            BufferedReader bufferedReader=new BufferedReader(new FileReader("data/book.txt"));
            String line=bufferedReader.readLine();
            while (line != null && !line.isEmpty()){
                String[] data=line.split(",");
                currentId=Integer.parseInt(data[0]);
                Book bookTemp=new Book(
                        Integer.parseInt(data[0]),
                        data[1],
                        Integer.parseInt(data[2]),
                        BookCategory.valueOf(data[3]),
                        data[4],
                        Integer.parseInt(data[5])
                );
                bookList.add(bookTemp);
                line= bufferedReader.readLine();

            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void writeData(){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/book.txt"));
            for (Book product: bookList){
                writer.write(product.toString());
                writer.newLine();
            }

            writer.flush();
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}
