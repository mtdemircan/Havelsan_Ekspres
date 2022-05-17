import java.math.BigDecimal;
import java.math.RoundingMode;
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
    static final String ROUTE_ANAHAT_TRENI="NKPRDSX";
    static final String ROUTE_YUK_TRENI="GHIFJKL";
    static Graph graph;
    static Station[] intersections;
    static Map<Station,Train> last_train_at_intersection;
    static ArrayList<Train> trains;
    static int time=0;
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
        trains=new ArrayList<>();
        ArrayList<Train> trains_yuk=new ArrayList<>();
        ArrayList<Train> trains_ana=new ArrayList<>();
        ArrayList<Train> trains_hiz=new ArrayList<>();
        for(int i=0;i<num_of_hiz_treni;i++){
            trains_hiz.add(new Train(0,"HT-"+(i+1),'A',true));
        }
        if (trains_hiz.size()>1){
            for (int i=1;i<trains_hiz.size()-1;i++){
                trains_hiz.get(i).prev=trains_hiz.get(i+1);
                trains_hiz.get(i).next=trains_hiz.get(i-1);
            }
            trains_hiz.get(0).prev=trains_hiz.get(1);
            trains_hiz.get(0).next=trains_hiz.get(trains_hiz.size()-1);
            trains_hiz.get(trains_hiz.size()-1).prev=trains_hiz.get(0);
            trains_hiz.get(trains_hiz.size()-1).next=trains_hiz.get(trains_hiz.size()-2);
        }
            else if (trains_hiz.size()==1){
                trains_hiz.get(0).prev=trains_hiz.get(0);
                trains_hiz.get(0).next=trains_hiz.get(0);
        }


        for(int i=0;i<num_of_anahat_treni;i++){
            trains_ana.add(new Train(1,"AT-"+(i+1),'N',true));
        }
        if (trains_ana.size()>1){
            for (int i=1;i<trains_ana.size()-1;i++){
                trains_ana.get(i).prev=trains_ana.get(i+1);
                trains_ana.get(i).next=trains_ana.get(i-1);
            }
            trains_ana.get(0).prev=trains_ana.get(1);
            trains_ana.get(0).next=trains_ana.get(trains_ana.size()-1);
            trains_ana.get(trains_ana.size()-1).prev=trains_ana.get(0);
            trains_ana.get(trains_ana.size()-1).next=trains_ana.get(trains_ana.size()-2);
        }
        else if (trains_ana.size()==1){
            trains_ana.get(0).prev=trains_ana.get(0);
            trains_ana.get(0).next=trains_ana.get(0);
        }


        for(int i=0;i<num_of_yuk_treni;i++){
            trains_yuk.add(new Train(2,"YT-"+(i+1),'G',true));
        }
        if (trains_yuk.size()>1){
            for (int i=1;i<trains_yuk.size()-1;i++){
                trains_yuk.get(i).prev=trains_yuk.get(i+1);
                trains_yuk.get(i).next=trains_yuk.get(i-1);
            }
            trains_yuk.get(0).prev=trains_yuk.get(1);
            trains_yuk.get(0).next=trains_yuk.get(trains_yuk.size()-1);
            trains_yuk.get(trains_yuk.size()-1).prev=trains_yuk.get(0);
            trains_yuk.get(trains_yuk.size()-1).next=trains_yuk.get(trains_yuk.size()-2);
        }
        else if (trains_yuk.size()==1){
            trains_yuk.get(0).prev=trains_yuk.get(0);
            trains_yuk.get(0).next=trains_yuk.get(0);
        }

        trains.addAll(trains_hiz);
        trains.addAll(trains_ana);
        trains.addAll(trains_yuk);
        createGraph();


        for (Station st:graph.getVertices()
             ) {
            System.out.println(st.name);
            for (Rail edge : st.getEdges()) {
                System.out.println(edge.to.name);
                System.out.println(edge.weight);
            }
            System.out.println();
        }
        for (time=0;time<gun*24*60;time++){
            for (Train train:trains) {
                    if(train.mode==2 ){
                        mode2(train);
                    } else if (train.mode==1) {
                        mode1(train);
                    }

                    else {//yolda
                            mode0(train);
                        }
                    }
            /*if (time%60==0){
                System.out.println((time/60)+"-------------------------");
                for (Train train:trains) {
                    System.out.println(train.name);
                    System.out.println(train.waiting_station_name);
                    System.out.println(train.rail+"\n");
                }

            }*/
            for (Station st:intersections) {
                if(st.intersection_wait!=0)
                    st.intersection_wait=st.intersection_wait-1;
            }
        }

    }



    static void mode1(Train train){
        if (train.service_remaining_time<=0){//yola çıkıcak
            train.working = true;
            graph.getStation(train.waiting_station_name).current_train = null;
            if (train.waiting_station_name == 'P' || train.waiting_station_name == 'X') {
                graph.getStation('P').current_train = null;
                graph.getStation('X').current_train = null;
            }
            int index;
            char next_station_name = 0;
            switch (train.type){
                case 0:
                    index=ROUTE_HIZ_TRENI.indexOf(train.waiting_station_name);
                    if ((index==0 && !train.direction) || (index==ROUTE_HIZ_TRENI.length()-1 && train.direction)){
                        train.maintenance_remaining_time=120;
                        train.mode=2;
                        train.direction=!train.direction;
                        //System.out.println("mode2");
                        next_station_name=ROUTE_HIZ_TRENI.charAt(train.direction?index+1:index-1);
                        //System.out.println(next_station_name);
                    }

                    else{
                        //System.out.println("char"+ROUTE_HIZ_TRENI.charAt(train.direction?index+1:index-1));
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
                        //System.out.println(next_station_name);
                        }
                    else{
                        //System.out.println("char"+ROUTE_ANAHAT_TRENI.charAt(train.direction?index+1:index-1));
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
                        //System.out.println(next_station_name);
                    }
                    else{
                        //System.out.println("char"+ROUTE_YUK_TRENI.charAt(train.direction?index+1:index-1));
                        next_station_name=ROUTE_YUK_TRENI.charAt(train.direction?index+1:index-1);
                        train.mode=0;
                    }
                    break;
            }
            for (Iterator<Rail> it = graph.getStation(train.waiting_station_name).edges.iterator(); it.hasNext(); ) {
                Rail rail = it.next();
                //System.out.println(train.name+graph.getStation(train.waiting_station_name).name+""+rail.to.name+""+next_station_name);
                if (rail.to.name==next_station_name) {
                    //System.out.println("foo found");
                    train.remaining_rail = rail.weight;
                    train.rail=rail;
                    //System.out.println(train.rail.to.name+" to");
                }
            }
        }
        else{
            train.service_remaining_time=train.service_remaining_time-1;
            //System.out.println("bekleme"+train.service_remaining_time);
        }
        switch (train.mode){
            case 0:
                if ("DFKPX".indexOf(graph.getStation(train.waiting_station_name).name)!=-1){
                    graph.getStation(train.waiting_station_name).intersection_wait=5;
                    last_train_at_intersection.put(graph.getStation(train.waiting_station_name),train);
                    if(train.waiting_station_name=='P' || train.waiting_station_name=='X'){
                        graph.getStation('P').intersection_wait=5;
                        graph.getStation('X').intersection_wait=5;
                        last_train_at_intersection.put(graph.getStation('P'),train);
                        last_train_at_intersection.put(graph.getStation('X'),train);

                    }
                }
                mode0(train);
                break;
            case 2:
                mode2(train);
                break;
        }
    }
    static void mode2(Train train){
        if (train.maintenance_remaining_time<=0 && graph.getStation(train.waiting_station_name).current_train==null) {
            train.working = true;
            //graph.getStation(train.waiting_station_name).current_train = train;
            int index;
            train.mode=0;
        }
        else {
            train.maintenance_remaining_time=train.maintenance_remaining_time-1;
            //System.out.println("mainbekleme"+train.maintenance_remaining_time);

        }
        switch (train.mode){
            case 0:
                if ("DFKPX".indexOf(graph.getStation(train.waiting_station_name).name)!=-1){
                    graph.getStation(train.waiting_station_name).intersection_wait=5;
                    last_train_at_intersection.put(graph.getStation(train.waiting_station_name),train);
                    if(train.waiting_station_name=='P' || train.waiting_station_name=='X'){
                        graph.getStation('P').intersection_wait=5;
                        graph.getStation('X').intersection_wait=5;
                        last_train_at_intersection.put(graph.getStation('P'),train);
                        last_train_at_intersection.put(graph.getStation('X'),train);

                    }
                }
                mode0(train);
                break;
            case 1:
                mode1(train);
                break;
        }
    }
    static void mode0(Train train){
        double max_speed=MAX_SPEED_HIZ_TRENI;

        switch (train.type){
            case 0:
                max_speed=MAX_SPEED_HIZ_TRENI/60.0;
                    //train.remaining_rail=train.remaining_rail-(MAX_SPEED_HIZ_TRENI/60.0);

                break;
            case 1:
                max_speed=MAX_SPEED_ANAHAT_TRENI/60.0;

                //train.remaining_rail=train.remaining_rail-(MAX_SPEED_ANAHAT_TRENI/60.0);
                break;
            case 2:
                max_speed=MAX_SPEED_YUK_TRENI/60.0;

                //train.remaining_rail=train.remaining_rail-(MAX_SPEED_YUK_TRENI/60.0);
                break;
        }
        if(train.rail.to.current_train==null){
            if( train.rail.to.intersection_wait!=0){
                double temp_speed=train.remaining_rail/train.rail.to.intersection_wait;
                max_speed= Math.min(max_speed, temp_speed);
                //System.out.println(max_speed+" inter");
            }
        }
        else {
            double temp_speed=Math.min(train.remaining_rail, train.rail.to.current_train.prev.remaining_rail)/train.rail.to.current_train.service_remaining_time;
            max_speed= Math.min(max_speed, temp_speed);
            /*System.out.println(max_speed+" 1");
            System.out.println(train.name);
            System.out.println(train.rail.to.current_train.name);
            System.out.println(train.rail.to.current_train.prev.name);

            System.out.println(train.remaining_rail);
            System.out.println(train.rail.to.current_train.prev.remaining_rail);

            System.out.println(Math.min(train.remaining_rail, train.rail.to.current_train.prev.remaining_rail));
            System.out.println(train.rail.to.current_train.service_remaining_time);*/

            double length=112;
            Train closest=train;
            for (Train tr:trains) {

                if (tr.rail!=null && tr.direction== train.direction && tr.rail.to.name == train.rail.to.name && tr.remaining_rail<length && BigDecimal.valueOf(tr.remaining_rail).setScale(3, RoundingMode.HALF_UP).doubleValue()>0){
                    length=tr.remaining_rail;
                    closest=tr;
                }
            }
            temp_speed=closest.remaining_rail/train.rail.to.current_train.service_remaining_time;
            //System.out.println(closest.name);

            max_speed= Math.min(max_speed, temp_speed);
            //System.out.println(max_speed+" 2");


        }
        /*if (train.next.rail!=null && train.next.rail.to.name == train.rail.to.name && train.remaining_rail-train.next.remaining_rail<1 ){
            train.remaining_rail=train.next.remaining_rail+1;
        }
        else {
            train.remaining_rail=train.remaining_rail-(max_speed);
        }*/
        //System.out.println(train.rail.to.name+" "+train.remaining_rail);
        if (train.rail.to.current_train!=null &&train.remaining_rail<=1)
            train.remaining_rail=1;
        else
            train.remaining_rail=train.remaining_rail-(max_speed);
        /*System.out.println(train.name+" speed "+max_speed);
        System.out.println(train.name+" remain "+train.remaining_rail);*/

        if(BigDecimal.valueOf(train.remaining_rail).setScale(3, RoundingMode.HALF_UP).doubleValue()<=0){
            if(train.rail.to.current_train==null && train.rail.to.intersection_wait==0){
                train.rail.to.current_train=train;
                System.out.println(train.name+" "+train.rail.to.name+" "+time/1440+" "+(time%1440)/60+":"+(time%1440)%60);
                if (train.rail.to.name == 'P' || train.rail.to.name == 'X') {
                    graph.getStation('P').current_train = train;
                    graph.getStation('X').current_train = train;
                }
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
        /*switch (train.mode){
            case 1:
                mode1(train);
                break;
            case 2:
                mode2(train);
                break;
        }*/
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
        Train prev;
        Train next;
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
        int intersection_wait;
        private Set<Rail> edges; //collection of edges to neighbors
        Station(char name){
            intersection_wait=0;
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
    private static void createGraph() {
        Station stationA=new Station('A');
        Station stationB=new Station('B');
        Station stationC=new Station('C');
        Station stationD=new Station('D');
        Station stationE=new Station('E');
        Station stationF=new Station('F');
        Station stationO=new Station('O');

        Station stationN=new Station('N');
        Station stationK=new Station('K');
        Station stationP=new Station('P');
        Station stationR=new Station('R');
        Station stationS=new Station('S');

        Station stationG=new Station('G');
        Station stationH=new Station('H');
        Station stationI=new Station('I');
        Station stationJ=new Station('J');
        Station stationL=new Station('L');
        Station stationX=new Station('X');


        graph = new Graph();
        stationA.addEdge(new Rail(stationB, 100));
        stationB.addEdge(new Rail(stationA, 100));

        stationB.addEdge(new Rail(stationC, 75));
        stationC.addEdge(new Rail(stationB, 75));

        stationC.addEdge(new Rail(stationD, 100));
        stationD.addEdge(new Rail(stationC, 100));

        stationD.addEdge(new Rail(stationE, 75));
        stationE.addEdge(new Rail(stationD, 75));

        stationE.addEdge(new Rail(stationF, 75));
        stationF.addEdge(new Rail(stationE, 75));

        stationF.addEdge(new Rail(stationO, 25));
        stationO.addEdge(new Rail(stationF, 25));


        stationN.addEdge(new Rail(stationK, 100));
        stationK.addEdge(new Rail(stationN, 100));

        stationK.addEdge(new Rail(stationP, 100));
        stationP.addEdge(new Rail(stationK, 100));

        stationP.addEdge(new Rail(stationR, 75));
        stationR.addEdge(new Rail(stationP, 75));

        stationD.addEdge(new Rail(stationR, 50));
        stationR.addEdge(new Rail(stationD, 50));

        stationD.addEdge(new Rail(stationS, 50));
        stationS.addEdge(new Rail(stationD, 50));

        stationS.addEdge(new Rail(stationX, 75));
        stationX.addEdge(new Rail(stationS, 75));



        stationG.addEdge(new Rail(stationH, 77));
        stationH.addEdge(new Rail(stationG, 77));

        stationH.addEdge(new Rail(stationI, 82));
        stationI.addEdge(new Rail(stationH, 82));

        stationI.addEdge(new Rail(stationF, 50));
        stationF.addEdge(new Rail(stationI, 50));

        stationF.addEdge(new Rail(stationJ, 97));
        stationJ.addEdge(new Rail(stationF, 97));

        stationJ.addEdge(new Rail(stationK, 100));
        stationK.addEdge(new Rail(stationJ, 100));

        stationK.addEdge(new Rail(stationL, 112));
        stationL.addEdge(new Rail(stationK, 112));

        graph.addVertex(stationA);
        graph.addVertex(stationB);
        graph.addVertex(stationC);
        graph.addVertex(stationD);
        graph.addVertex(stationE);
        graph.addVertex(stationF);
        graph.addVertex(stationO);

        graph.addVertex(stationN);
        graph.addVertex(stationK);
        graph.addVertex(stationP);
        graph.addVertex(stationR);
        graph.addVertex(stationS);

        graph.addVertex(stationG);
        graph.addVertex(stationH);
        graph.addVertex(stationI);
        graph.addVertex(stationJ);
        graph.addVertex(stationK);
        graph.addVertex(stationL);
        graph.addVertex(stationX);

        intersections=new Station[]{stationD,stationF,stationK,stationP};
        last_train_at_intersection=new HashMap<>();
        last_train_at_intersection.put(stationD,null);
        last_train_at_intersection.put(stationF,null);
        last_train_at_intersection.put(stationK,null);
        last_train_at_intersection.put(stationP,null);
    }
}
