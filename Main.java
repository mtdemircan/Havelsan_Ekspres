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
            trains.add(new Train(0,"HT-"+i,'A',true));
        }

        Station stationA=new Station('A');
        Station stationB=new Station('B');
        Station stationC=new Station('C');
        Station stationD=new Station('D');
        Station stationE=new Station('E');
        Station stationF=new Station('F');
        Station stationO=new Station('O');

        Graph Graph = new Graph();
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

        Graph.addVertex(stationA);
        Graph.addVertex(stationB);
        Graph.addVertex(stationC);
        Graph.addVertex(stationD);
        Graph.addVertex(stationE);
        Graph.addVertex(stationF);
        Graph.addVertex(stationO);

        for (Station st:Graph.getVertices()
             ) {
            System.out.println(st.name);

            for (Rail edge : st.getEdges()) {
                System.out.println(edge.to.name);
                System.out.println(edge.weight);
            }
            System.out.println();

        }

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

        boolean addVertex(Station vertex){
            return vertices.add(vertex);
        }
    }
}
