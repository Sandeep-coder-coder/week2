import java.util.*;
public class week2 {
    static int L1_SIZE = 10000;
    static int L2_SIZE = 100000;
    static LinkedHashMap<String,String> L1 = new LinkedHashMap<String,String>(L1_SIZE,0.75f,true){
        protected boolean removeEldestEntry(Map.Entry<String,String> e){
            return size()>L1_SIZE;
        }
    };
    static LinkedHashMap<String,String> L2 = new LinkedHashMap<String,String>(L2_SIZE,0.75f,true){
        protected boolean removeEldestEntry(Map.Entry<String,String> e){
            return size()>L2_SIZE;
        }
    };
    static HashMap<String,String> database = new HashMap<>();
    static HashMap<String,Integer> accessCount = new HashMap<>();
    static int L1hits=0,L2hits=0,L3hits=0;
    public static String getVideo(String id){
        if(L1.containsKey(id)){
            L1hits++;
            accessCount.put(id,accessCount.getOrDefault(id,0)+1);
            System.out.println("L1 Cache HIT (0.5ms)");
            return L1.get(id);
        }
        if(L2.containsKey(id)){
            L2hits++;
            accessCount.put(id,accessCount.getOrDefault(id,0)+1);
            System.out.println("L1 MISS → L2 HIT (5ms)");
            String data=L2.get(id);
            if(accessCount.get(id)>2){
                L1.put(id,data);
                System.out.println("Promoted to L1");
            }
            return data;
        }
        if(database.containsKey(id)){
            L3hits++;
            System.out.println("L1 MISS → L2 MISS → L3 Database HIT (150ms)");
            String data=database.get(id);
            L2.put(id,data);
            accessCount.put(id,1);
            return data;
        }
        System.out.println("Video not found");
        return null;
    }
    public static void addVideo(String id,String data){
        database.put(id,data);
    }
    public static void getStatistics(){
        int total=L1hits+L2hits+L3hits;
        double l1rate=(total==0)?0:(L1hits*100.0)/total;
        double l2rate=(total==0)?0:(L2hits*100.0)/total;
        double l3rate=(total==0)?0:(L3hits*100.0)/total;
        System.out.println("L1 Hit Rate: "+l1rate+"%");
        System.out.println("L2 Hit Rate: "+l2rate+"%");
        System.out.println("L3 Hit Rate: "+l3rate+"%");
        System.out.println("Total Requests: "+total);
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter number of videos in database: ");
        int n=sc.nextInt();
        sc.nextLine();
        for(int i=0;i<n;i++){
            System.out.print("Video ID: ");
            String id=sc.nextLine();
            System.out.print("Video Data: ");
            String data=sc.nextLine();
            addVideo(id,data);
        }
        while(true){
            System.out.println("\n1.Get Video");
            System.out.println("2.Cache Statistics");
            System.out.println("3.Exit");
            System.out.print("Choice: ");
            int ch=sc.nextInt();
            sc.nextLine();
            if(ch==1){
                System.out.print("Enter Video ID: ");
                String id=sc.nextLine();
                getVideo(id);
            }
            else if(ch==2){
                getStatistics();
            }
            else{
                break;
            }
        }
    }
}