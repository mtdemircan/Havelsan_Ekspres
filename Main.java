import java.util.*;

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
            trains.add(new Train(0,"HT-"+i+1,'A',true));
        }

        Station stationA=new Station('A');
        Station stationB=new Station('B');
        Station stationC=new Station('C');
        Station stationD=new Station('D');
        Station stationE=new Station('E');
        Station stationF=new Station('F');
        Station stationO=new Station('O');

        Graph graph = new Graph();
        stationA.addEdge(new Rail(stationB, 100)); //connect v1 v2
        stationB.addEdge(new Rail(stationA, 100));

        stationB.addEdge(new Rail(stationC, 75)); //connect v2 v3
        stationC.addEdge(new Rail(stationB, 75));

        stationC.addEdge(new Rail(stationD, 100)); //connect v2 v4
        stationD.addEdge(new Rail(stationC, 100));

        stationD.addEdge(new Rail(stationE, 75)); //connect v4 v5
        stationE.addEdge(new Rail(stationD, 75));

        stationE.addEdge(new Rail(stationF, 75)); //connect v4 v5
        stationF.addEdge(new Rail(stationE, 75));

        stationF.addEdge(new Rail(stationO, 25)); //connect v4 v5
        stationO.addEdge(new Rail(stationF, 25));

        graph.addVertex(stationA);
        graph.addVertex(stationB);
        graph.addVertex(stationC);
        graph.addVertex(stationD);
        graph.addVertex(stationE);
        graph.addVertex(stationF);
        graph.addVertex(stationO);

        for (Station st:graph.getVertices()
             ) {
            System.out.println(st.name);

            for (Rail edge : st.getEdges()) {
                System.out.println(edge.to.name);
                System.out.println(edge.weight);
            }
            System.out.println();

        }
        for (int i=0;i<gun*24*60;i++){
            for (Train train:trains) {

                    if(train.mode==2 ){

                        if (train.maintenance_remaining_time<=0 && graph.getStation(train.waiting_station_name).current_train==null) {
                            train.working = true;
                            //graph.getStation(train.waiting_station_name).current_train = train;
                            int index;
                            train.mode=0;


                        }
                        else {
                            train.maintenance_remaining_time=train.maintenance_remaining_time-1;
                            System.out.println("mainbekleme"+train.maintenance_remaining_time);

                        }
                    } else if (train.mode==1) {
                        if (train.service_remaining_time<=0){//yola çıkıcak
                            train.working = true;
                            graph.getStation(train.waiting_station_name).current_train = null;
                            int index;
                            char next_station_name = 0;
                            switch (train.type){
                                case 0:
                                    index=ROUTE_HIZ_TRENI.indexOf(train.waiting_station_name);
                                    if ((index==0 && !train.direction) || (index==ROUTE_HIZ_TRENI.length()-1 && train.direction)){
                                        train.maintenance_remaining_time=120;
                                        train.mode=2;
                                        train.direction=!train.direction;
                                        System.out.println("mode2");

                                        next_station_name=ROUTE_HIZ_TRENI.charAt(train.direction?index+1:index-1);
                                        System.out.println(next_station_name);
                                    }

                                    else{
                                        System.out.println("char"+ROUTE_HIZ_TRENI.charAt(train.direction?index+1:index-1));
                                        next_station_name=ROUTE_HIZ_TRENI.charAt(train.direction?index+1:index-1);
                                        train.mode=0;
                                    }
                                    break;
                                case 1:
                                    index=ROUTE_ANAHAT_TRENI.indexOf(train.waiting_station_name);
                                    if ((index==0 && !train.direction) || (index==ROUTE_ANAHAT_TRENI.length()-1 && train.direction)) {
                                        train.maintenance_remaining_time = 240;
                                        train.mode = 2;
                                        train.direction=!train.direction;

                                        next_station_name=ROUTE_ANAHAT_TRENI.charAt(train.direction?index+1:index-1);
                                        System.out.println(next_station_name);                                    }
                                    else{
                                            System.out.println("char"+ROUTE_ANAHAT_TRENI.charAt(train.direction?index+1:index-1));
                                            next_station_name=ROUTE_ANAHAT_TRENI.charAt(train.direction?index+1:index-1);
                                        train.mode=0;
                                        }
                                    break;

                                case 2:
                                    index=ROUTE_YUK_TRENI.indexOf(train.waiting_station_name);
                                    if ((index==0 && !train.direction) || (index==ROUTE_YUK_TRENI.length()-1 && train.direction)) {
                                        train.maintenance_remaining_time=180;
                                        train.mode=2;
                                        train.direction=!train.direction;

                                        next_station_name=ROUTE_YUK_TRENI.charAt(train.direction?index+1:index-1);
                                        System.out.println(next_station_name);
                                    }
                                    else{
                                        System.out.println("char"+ROUTE_YUK_TRENI.charAt(train.direction?index+1:index-1));
                                        next_station_name=ROUTE_YUK_TRENI.charAt(train.direction?index+1:index-1);
                                        train.mode=0;
                                    }
                                    break;

                            }
                            for (Iterator<Rail> it = graph.getStation(train.waiting_station_name).edges.iterator(); it.hasNext(); ) {
                                Rail rail = it.next();
                                //System.out.println(train.name+graph.getStation(train.waiting_station_name).name+""+rail.to.name+""+next_station_name);
                                if (rail.to.name==next_station_name) {
                                    System.out.println("foo found");
                                    train.remaining_rail = rail.weight;
                                    train.rail=rail;
                                    System.out.println(train.rail.to.name+" to");


                                    //train.waiting_station_name = 'X';
                                }
                            }
                        }
                        else{

                            train.service_remaining_time=train.service_remaining_time-1;
                            System.out.println("bekleme"+train.service_remaining_time);
                        }
                    }

                    else {//yolda


                            switch (train.type){
                                case 0:
                                    train.remaining_rail=train.remaining_rail-(MAX_SPEED_HIZ_TRENI/60.0);
                                    break;

                                case 1:
                                    train.remaining_rail=train.remaining_rail-(MAX_SPEED_ANAHAT_TRENI/60.0);
                                    break;

                                case 2:
                                    train.remaining_rail=train.remaining_rail-(MAX_SPEED_YUK_TRENI/60.0);
                                    break;


                            }
                        System.out.println(train.rail.to.name+" "+train.remaining_rail);
                        if(train.remaining_rail<=0){
                            if(train.rail.to.current_train==null){
                                train.rail.to.current_train=train;
                                train.waiting_station_name=train.rail.to.name;
                                train.rail=null;
                                train.mode=1;
                                train.working = false;
                                switch (train.type){
                                    case 0:
                                        train.service_remaining_time=15;
                                        break;
                                    case 1:
                                        train.service_remaining_time=20;
                                        break;

                                    case 2:
                                        train.service_remaining_time=0;
                                        break;

                                }

                            }
                            else {
                                ///////////
                            }
                        }
                        }

                    }


            if (i%60==0){
                System.out.println((i/60)+"-------------------------");
                for (Train train:trains) {
                    System.out.println(train.name);
                    System.out.println(train.waiting_station_name);
                    System.out.println(train.rail+"\n");
                }

            }

        }

    }

    static class Train{
        int type;//0hiz,1anahat,2yuk
        int mode;//0 yolda, 1 servis bekleme,2 bakım bekleme
        int service_remaining_time;//istasyondayken kalan zaman
        int maintenance_remaining_time;//bakımdayken kalan zaman
        boolean working;//bakımda mı değil mi
        boolean direction;//bastan sona true, sondan basa false
        String name;
        int distance;//bakıma kadar total mesafe
        char start_station_name;//başlangıc istasyonu
        char waiting_station_name;//suanda bulunugu istasyon
        double remaining_rail;//sonraki istasyona kalan yol
        Rail rail;//suan bulundugu yol
        Train(int type,String name,char start_station_name,boolean direction){
            this.type=type;
            this.name=name;
            rail=null;
            service_remaining_time=0;
            maintenance_remaining_time=0;
            remaining_rail=0;
            working=false;
            mode=1;
            this.start_station_name=start_station_name;
            this.distance=0;
            this.direction=direction;
            waiting_station_name=start_station_name;
        }

    }
    static class Station {
        Train current_train;
        char name;
        private Set<Rail> edges; //collection of edges to neighbors
        Station(char name){
            this.name=name;
            edges = new HashSet<>();
            current_train=null;
        }
        boolean addEdge(Rail edge){
            return edges.add(edge);
        }
        List<Rail> getEdges() {
            return new ArrayList<>(edges);
        }

        //todo override hashCode()
    }
    
    static class Rail {
        Station to;
        int weight;
        public Rail(Station to, int weight) {
            super();
            this.to = to;
            this.weight = weight;
        }
    }
    
    static class Graph{

        private Set<Station> vertices; //collection of all verices

        public Graph() {
            vertices = new HashSet<>();
        }

        List<Station> getVertices() {
            return new ArrayList<>(vertices);
        }
        Station getStation(char c){
            for (Station st:vertices
                 ) {
                if (st.name == c)
                    return st;
            }
            return null;
        }
        boolean addVertex(Station vertex){
            return vertices.add(vertex);
        }
    }
}
