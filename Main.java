import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Main {
    static int xx=0;
    static int total_profit=0;
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

    static final int GENERAL_MAINTENANCE_TIME_HIZ_TRENI=720;
    static final int GENERAL_MAINTENANCE_TIME_ANAHAT_TRENI=1440;
    static final int GENERAL_MAINTENANCE_TIME_YUK_TRENI=2160;

    static final int SERVICE_MAINTENANCE_TIME_HIZ_TRENI=120;
    static final int SERVICE_MAINTENANCE_TIME_ANAHAT_TRENI=240;
    static final int SERVICE_MAINTENANCE_TIME_YUK_TRENI=180;

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
    static HashMap<Train,ArrayList<State>> trains_states;
    static int time=0;
    static int time_cikis;

    public static void main(String[] args) {
        trains_states=new HashMap<>();
        LinkedList<Station> route=new LinkedList<Station>();
        Scanner scanner=new Scanner(System.in);
        try{
            System.out.print("Hiz Treni Adedi Giriniz:");
            num_of_hiz_treni=Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.print("Not a number.");
            System.exit(0);
        }
        try{
            System.out.print("Yuk Treni Adedi Giriniz:");
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
            System.out.print("Kac Gunluk Sefer Planlamasi Istiyorsunuz:");
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
            Train temptrain;
            if (i<num_of_hiz_treni/2)
                temptrain=new Train(0,"HT-"+(i+1),'A',true);
            else
                temptrain=new Train(0,"HT-"+(i+1),'O',false);
            trains_hiz.add(temptrain);
            trains_states.put(temptrain,new ArrayList<>());
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
            Train temptrain;
            if (i<num_of_hiz_treni/2)
                temptrain=new Train(1,"AT-"+(i+1),'N',true);
            else
                temptrain=new Train(1,"AT-"+(i+1),'X',false);

            trains_ana.add(temptrain);
            trains_states.put(temptrain,new ArrayList<>());
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
            Train temptrain;
            if (i<num_of_hiz_treni/2)
                temptrain=new Train(2,"YT-"+(i+1),'G',true);
            else
                temptrain=new Train(2,"YT-"+(i+1),'L',false);

            trains_yuk.add(temptrain);
            trains_states.put(temptrain,new ArrayList<>());
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



        int max_num=Math.max(num_of_anahat_treni,Math.max(num_of_hiz_treni,num_of_yuk_treni));
        for (time=0;time<max_num;time++){
            for (int j=0;j<time;j++) {
                try {
                    Train train=trains_hiz.get(j);
                    if(train.mode==2 ){
                        mode2(train);
                    } else if (train.mode==1) {
                        mode1(train);
                    }
                    else {//yolda
                        mode0(train);
                    }
                }catch (Exception e){

                }
                try {
                    Train train=trains_ana.get(j);
                    if(train.mode==2 ){
                        mode2(train);
                    } else if (train.mode==1) {
                        mode1(train);
                    }
                    else {//yolda
                        mode0(train);
                    }
                }catch (Exception e){

                }
                try {
                    Train train=trains_yuk.get(j);
                    if(train.mode==2 ){
                        mode2(train);
                    } else if (train.mode==1) {
                        mode1(train);
                    }
                    else {//yolda
                        mode0(train);
                    }
                }catch (Exception e){

                }



            }
            for (Station st:intersections) {
                if(st.intersection_wait!=0)
                    st.intersection_wait=st.intersection_wait-1;
            }
        }
        for (;time<gun*24*60;time++){
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
            for (Station st:intersections) {
                if(st.intersection_wait!=0)
                    st.intersection_wait=st.intersection_wait-1;
            }
        }

        System.out.println(xx);
        System.out.println(total_profit);

        Set entrySet = trains_states.entrySet();

        System.out.println("***************************");
        for (Station st:graph.vertices) {
            System.out.println(st.name);
            for (State state:st.states) {
                int varis_saat=(state.varis%1440)/60;
                int varis_dakika=(state.varis%1440)%60;
                int cikis_saat=(state.cikis%1440)/60;
                int cikis_dakika=(state.cikis%1440)%60;
                System.out.println(st.name+" "+(state.varis/1440)+1+" "+(varis_saat<10?"0"+varis_saat:varis_saat)+":"+(varis_dakika<10?"0"+varis_dakika:varis_dakika)+" "+(cikis_saat<10?"0"+cikis_saat:cikis_saat)+":"+(cikis_dakika<10?"0"+cikis_dakika:cikis_dakika)+" "+state.train.name+" "+state.direction_string);

            }
        }

        System.out.println("***************************");
        ArrayList<State> allStates=new ArrayList<>();

        for (Station st:graph.vertices) {
            allStates.addAll(st.states);
        }
        HashMap<Train,ArrayList<State>> table1=new HashMap<>();
        HashMap<Train,HashMap<Integer,ArrayList<State>>> table11=new HashMap<>();

        for (Train train:trains) {

            table1.put(train,new ArrayList<>());
            table11.put(train,new HashMap<>());
        }
        for (State st:allStates) {
            table1.get(st.train).add(st);
        }

        for (Iterator<Map.Entry<Train, ArrayList<State>>> it = table1.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Train, ArrayList<State>> entry= it.next();
            ArrayList<State> states =entry.getValue();
            for (int i=1;i<entry.getKey().sefer_no+1;i++){

                        table11.get(entry.getKey()).put(i,new ArrayList<>());

            }
            for (int i=1;i<entry.getKey().sefer_no+1;i++){
                for (State st:states) {
                    if (st.sefer_no==i){
                        table11.get(entry.getKey()).get(i).add(st);
                    }
                }
            }

        }
        for (Iterator<Map.Entry<Train, HashMap<Integer, ArrayList<State>>>> it = table11.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Train, HashMap<Integer, ArrayList<State>>> next=it.next();
            for (Iterator<Map.Entry<Integer, ArrayList<State>>> it2 = next.getValue().entrySet().iterator(); it2.hasNext();){
                Map.Entry<Integer, ArrayList<State>> entry= it2.next();
                ArrayList<State> states =entry.getValue();
                Collections.sort(states);
                for (State state:states) {
                    int varis_saat=(state.varis%1440)/60;
                    int varis_dakika=(state.varis%1440)%60;
                    int cikis_saat=(state.cikis%1440)/60;
                    int cikis_dakika=(state.cikis%1440)%60;
                    System.out.println(state.sefer_no+" " +state.station.name+" "+(state.varis/1440)+1+" "+(varis_saat<10?"0"+varis_saat:varis_saat)+":"+(varis_dakika<10?"0"+varis_dakika:varis_dakika)+" "+(cikis_saat<10?"0"+cikis_saat:cikis_saat)+":"+(cikis_dakika<10?"0"+cikis_dakika:cikis_dakika)+" "+state.train.name+" "+state.direction_string);

                }
            }


        }


    }



    static void mode1(Train train){
        if (train.service_remaining_time<=0){//yola çıkıcak
            train.working = true;
            graph.getStation(train.waiting_station_name).current_train=null;
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
                        train.maintenance_remaining_time=SERVICE_MAINTENANCE_TIME_HIZ_TRENI;
                        total_profit+=SERVICE_PROFIT_HIZ_TRENI;
                        train.mode=2;
                        train.direction=!train.direction;
                        train.sefer_no++;
                        next_station_name=ROUTE_HIZ_TRENI.charAt(train.direction?index+1:index-1);
                    }

                    else{
                        next_station_name=ROUTE_HIZ_TRENI.charAt(train.direction?index+1:index-1);

                        train.mode=0;
                    }
                    break;
                case 1:
                    index=ROUTE_ANAHAT_TRENI.indexOf(train.waiting_station_name);
                    if ((index==0 && !train.direction) || (index==ROUTE_ANAHAT_TRENI.length()-1 && train.direction)) {
                        train.maintenance_remaining_time = SERVICE_MAINTENANCE_TIME_ANAHAT_TRENI;
                        total_profit+=SERVICE_PROFIT_ANAHAT_TRENI;

                        train.mode = 2;
                        train.direction=!train.direction;
                        train.sefer_no++;
                        next_station_name=ROUTE_ANAHAT_TRENI.charAt(train.direction?index+1:index-1);
                        }
                    else{
                        next_station_name=ROUTE_ANAHAT_TRENI.charAt(train.direction?index+1:index-1);
                        train.mode=0;
                    }
                    break;

                case 2:
                    index=ROUTE_YUK_TRENI.indexOf(train.waiting_station_name);
                    if ((index==0 && !train.direction) || (index==ROUTE_YUK_TRENI.length()-1 && train.direction)) {
                        train.maintenance_remaining_time=SERVICE_MAINTENANCE_TIME_YUK_TRENI;
                        total_profit+=SERVICE_PROFIT_YUK_TRENI;

                        train.mode=2;
                        train.direction=!train.direction;
                        train.sefer_no++;
                        next_station_name=ROUTE_YUK_TRENI.charAt(train.direction?index+1:index-1);
                    }
                    else{
                        next_station_name=ROUTE_YUK_TRENI.charAt(train.direction?index+1:index-1);
                        train.mode=0;
                    }
                    break;
            }
            for (Iterator<Rail> it = graph.getStation(train.waiting_station_name).edges.iterator(); it.hasNext(); ) {
                Rail rail = it.next();
                if (rail.to.name==next_station_name) {
                    train.remaining_rail = rail.weight;
                    train.rail=rail;
                }
            }
            switch (train.type){
                case 0:
                    if (train.distance+train.rail.weight > GENERAL_MAINTENANCE_DISTANCE_HIZ_TRENI*80/100){
                        for (Train train2:trains) {
                            if (train2.type != train.type && train2.rail.to.name==train.rail.to.name){
                                train.distance=0;
                                train.mode=2;
                                train.maintenance_remaining_time+=GENERAL_MAINTENANCE_TIME_HIZ_TRENI;
                            }
                        }
                    }
                    else if (train.distance+train.rail.weight > GENERAL_MAINTENANCE_DISTANCE_HIZ_TRENI){
                        train.distance=0;
                        train.mode=2;
                        train.maintenance_remaining_time+=GENERAL_MAINTENANCE_TIME_HIZ_TRENI;
                    }
                case 1:
                    if (train.distance+train.rail.weight > GENERAL_MAINTENANCE_DISTANCE_HIZ_TRENI*80/100){
                        for (Train train2:trains) {
                            if (train2.type != train.type && train2.rail.to.name==train.rail.to.name){
                                train.distance=0;
                                train.mode=2;
                                train.maintenance_remaining_time+=GENERAL_MAINTENANCE_TIME_HIZ_TRENI;
                            }
                        }
                    }
                    else if (train.distance+train.rail.weight > GENERAL_MAINTENANCE_DISTANCE_ANAHAT_TRENI){
                        train.distance=0;
                        train.mode=2;
                        train.maintenance_remaining_time+=GENERAL_MAINTENANCE_TIME_ANAHAT_TRENI;
                    }
                case 2:
                    if (train.distance+train.rail.weight > GENERAL_MAINTENANCE_DISTANCE_HIZ_TRENI*80/100){
                        for (Train train2:trains) {
                            if (train2.type != train.type && train2.rail.to.name==train.rail.to.name){
                                train.distance=0;
                                train.mode=2;
                                train.maintenance_remaining_time+=GENERAL_MAINTENANCE_TIME_HIZ_TRENI;
                            }
                        }
                    }
                    else if (train.distance+train.rail.weight > GENERAL_MAINTENANCE_DISTANCE_YUK_TRENI){
                        train.distance=0;
                        train.mode=2;
                        train.maintenance_remaining_time+=GENERAL_MAINTENANCE_TIME_YUK_TRENI;
                    }
            }
        }
        else{
            train.service_remaining_time=train.service_remaining_time-1;
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

                graph.getStation(train.waiting_station_name).train_exited(train);
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
                graph.getStation(train.waiting_station_name).train_exited(train);

                time_cikis=time;
                trains_states.get(train).add(new State(train,train.entry_time,time,train.direction,train.sefer_no,graph.getStation(train.waiting_station_name)));

                mode0(train);
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
            }
        }
        else {

            double length=112;
            Train closest=train;
            for (Train tr:trains) {

                if (tr.rail!=null && tr.direction== train.direction && tr.rail.to.name == train.rail.to.name && tr.remaining_rail<length && BigDecimal.valueOf(tr.remaining_rail).setScale(3, RoundingMode.HALF_UP).doubleValue()>0){
                    length=tr.remaining_rail;
                    closest=tr;
                }
            }
            double temp_speed=closest.remaining_rail/train.rail.to.current_train.service_remaining_time;

            max_speed= Math.min(max_speed, temp_speed);


        }

        if (train.next.rail!=null && !train.next.name.equals(train.name) && train.next.rail.to.name == train.rail.to.name && train.remaining_rail-train.next.remaining_rail<1 && train.remaining_rail-train.next.remaining_rail>-1 ){
            train.remaining_rail=train.next.remaining_rail+1;

        }
        else if (train.rail.to.current_train!=null &&train.remaining_rail<=1){
            train.remaining_rail=1;

        }
        else {

            train.remaining_rail=train.remaining_rail-(max_speed);
        }

        if(BigDecimal.valueOf(train.remaining_rail).setScale(3, RoundingMode.HALF_UP).doubleValue()<=0){
            if(train.rail.to.current_train==null && train.rail.to.intersection_wait==0){
                train.rail.to.train_entered(train);
                train.distance+=train.rail.weight;
                train.entry_time=time;
                //trains_states.get(train).add(new State(train.name,time,time_cikis,train.direction,train.sefer_no));
                xx++;
                if (train.rail.to.name == 'P' || train.rail.to.name == 'X') {
                    graph.getStation('P').train_entered(train);
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
        int entry_time=0;
        int station_entry_time=0;
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

        int sefer_no=1;
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
        ArrayList<State> states;
        int intersection_wait;
        private Set<Rail> edges; //collection of edges to neighbors
        Station(char name){
            states=new ArrayList<>();
            intersection_wait=0;
            this.name=name;
            edges = new HashSet<>();
            current_train=null;
        }
        boolean addEdge(Rail edge){
            return edges.add(edge);
        }
        void train_entered(Train train){
            this.current_train=train;
            train.station_entry_time=time;

        }
        void train_exited(Train train){
            states.add(new State(train,train.station_entry_time,time, train.direction, train.sefer_no,this));
            current_train=null;
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

    static class State implements Comparable<State>{
        Train train;
        int varis;
        Station station;
        int cikis;
        boolean direction;

        String direction_string;

        int sefer_no;

        public State(Train train,int varis,int cikis,boolean direction,int sefer_no,Station station){
            this.station=station;
            this.train=train;
            this.varis=varis;
            this.cikis=cikis;
            this.direction=direction;
            this.sefer_no=sefer_no;
            if(train.name.charAt(0)=='H'){
                if(direction)
                    direction_string="A-O";
                else direction_string="O-A";
            }
            if(train.name.charAt(0)=='A'){
                if(direction)
                    direction_string="N-P";
                else direction_string="P-N";
            }
            if(train.name.charAt(0)=='Y'){
                if(direction)
                    direction_string="G-L";
                else direction_string="L-G";
            }
        }


        @Override
        public int compareTo(State o) {
            if(o == null) {
                return 1;
            } else if(train.name == null) {
                return 0;
            } else {
                if (cikis<o.cikis)
                    return 1;
                else
                    return -1;
            }
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
