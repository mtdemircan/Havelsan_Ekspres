import java.util.Scanner;

public class Main {
    static int gun;
    static int num_of_hiz_treni;
    static int num_of_yuk_treni;
    static int num_of_anahat_treni;
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        try{
            System.out.print("Hız Treni Adedi Giriniz:");
            num_of_hiz_treni=Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.print("Not a number.");
            System.exit(0);
        }
        try{
            System.out.print("Yük Treni Adedi Giriniz:");
            num_of_yuk_treni=Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.print("Not a number.");
            System.exit(0);
        }
        try{
            System.out.print("Anahat Treni Adedi Giriniz:");
            num_of_anahat_treni=Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.print("Not a number.");
            System.exit(0);
        }
        try{
            System.out.print("Kaç Günlük Sefer Planlaması İstiyorsunuz:");
            gun=Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.print("Not a number.");
            System.exit(0);
        }
    }
}
