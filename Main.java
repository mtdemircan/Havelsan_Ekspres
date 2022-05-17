import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    static int gun;
    static int num_of_hiz_treni;
    static int num_of_yuk_treni;
    static int num_of_anahat_treni;

    static final int MAX_SPEED_HIZ_TRENI=200;
    static final int MAX_SPEED_ANAHAT_TRENI=100;
    static final int MAX_SPEED_YUK_TRENI=75;

    static final int GENERAL_MAINTENANCE_DISTANCE_HIZ_TRENI=6000;
    static final int GENERAL_MAINTENANCE_DISTANCE_ANAHAT_TRENI=2500;
    static final int GENERAL_MAINTENANCE_DISTANCE_YUK_TRENI=3000;

    static final int GENERAL_MAINTENANCE_TIME_HIZ_TRENI=12;
    static final int GENERAL_MAINTENANCE_TIME_ANAHAT_TRENI=24;
    static final int GENERAL_MAINTENANCE_TIME_YUK_TRENI=36;

    static final int SERVICE_MAINTENANCE_TIME_HIZ_TRENI=2;
    static final int SERVICE_MAINTENANCE_TIME_ANAHAT_TRENI=4;
    static final int SERVICE_MAINTENANCE_TIME_YUK_TRENI=3;

    static final int SERVICE_PROFIT_HIZ_TRENI=60000;
    static final int SERVICE_PROFIT_ANAHAT_TRENI=45000;
    static final int SERVICE_PROFIT_YUK_TRENI=50000;

    static final String ROUTE_HIZ_TRENI="ABCDEFO";
    static final String ROUTE_ANAHAT_TRENI="NKPRDSp";
    static final String ROUTE_YUK_TRENI="GHIFJKL";


    public static void main(String[] args) {

        LinkedList<Station> route=new LinkedList<Station>();
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
        ArrayList<Train> trains=new ArrayList<>();
        for(int i=0;i<num_of_hiz_treni;i++){
            trains.add(new Train(0,"HT-"+i,'A',true));
        }
        route.add(new Station('A',0,100));
        route.add(new Station('B',100,75));
        route.add(new Station('C',75,100));
        route.add(new Station('D',100,75));
        route.add(new Station('E',75,75));
        route.add(new Station('F',75,25));
        route.add(new Station('O',25,0));


    }

    static class Train{
        int type;

        boolean direction;
        String name;
        int distance;
        char start_station_name;

        Train(int type,String name,char start_station_name,boolean direction){
            this.type=type;
            this.name=name;
            this.start_station_name=start_station_name;
            this.distance=0;
            this.direction=direction;
        }

    }

    static class Station{
        Train current_train;
        char name;
        int prev_distance;
        int next_distance;

        Station(char name,int prev_distance, int next_distance){
            this.name=name;
            this.prev_distance=prev_distance;
            this.next_distance=next_distance;
            current_train=null;
        }

    }

}
