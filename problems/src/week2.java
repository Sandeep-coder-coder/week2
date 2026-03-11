import java.util.*;
public class week2 {
    static class Transaction {
        int id;
        int amount;
        String merchant;
        int time;
        Transaction(int id,int amount,String merchant,int time){
            this.id=id;
            this.amount=amount;
            this.merchant=merchant;
            this.time=time;
        }
    }
    static List<Transaction> transactions=new ArrayList<>();
    public static void findTwoSum(int target){
        HashMap<Integer,Transaction> map=new HashMap<>();
        for(Transaction t:transactions){
            int complement=target-t.amount;
            if(map.containsKey(complement)){
                Transaction t2=map.get(complement);
                System.out.println("Pair Found: ("+t2.id+", "+t.id+")");
                return;
            }
            map.put(t.amount,t);
        }
        System.out.println("No pair found");
    }
    public static void findTwoSumTimeWindow(int target,int window){
        HashMap<Integer,Transaction> map=new HashMap<>();
        for(Transaction t:transactions){
            int complement=target-t.amount;
            if(map.containsKey(complement)){
                Transaction t2=map.get(complement);
                if(Math.abs(t.time-t2.time)<=window){
                    System.out.println("Pair within time window: ("+t2.id+", "+t.id+")");
                    return;
                }
            }
            map.put(t.amount,t);
        }
        System.out.println("No pair in time window");
    }
    public static void detectDuplicates(){
        HashMap<String,List<Transaction>> map=new HashMap<>();
        for(Transaction t:transactions){
            String key=t.amount+"-"+t.merchant;
            map.putIfAbsent(key,new ArrayList<>());
            map.get(key).add(t);
        }
        for(String key:map.keySet()){
            if(map.get(key).size()>1){
                System.out.println("Duplicate transactions: "+map.get(key).size()+" for "+key);
            }
        }
    }
    public static void findKSum(int k,int target){
        int n=transactions.size();
        int[] arr=new int[n];
        for(int i=0;i<n;i++) arr[i]=transactions.get(i).amount;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                for(int l=j+1;l<n;l++){
                    if(k==3 && arr[i]+arr[j]+arr[l]==target){
                        System.out.println("K-Sum Found: ("+
                                transactions.get(i).id+", "+
                                transactions.get(j).id+", "+
                                transactions.get(l).id+")");
                        return;
                    }
                }
            }
        }
        System.out.println("No K-Sum found");
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter number of transactions: ");
        int n=sc.nextInt();
        for(int i=0;i<n;i++){
            System.out.println("Transaction "+(i+1));
            System.out.print("ID: ");
            int id=sc.nextInt();
            System.out.print("Amount: ");
            int amount=sc.nextInt();
            sc.nextLine();
            System.out.print("Merchant: ");
            String merchant=sc.nextLine();
            System.out.print("Time (minutes): ");
            int time=sc.nextInt();
            transactions.add(new Transaction(id,amount,merchant,time));
        }
        System.out.print("Enter target for Two-Sum: ");
        int target=sc.nextInt();
        findTwoSum(target);
        System.out.print("Enter time window (minutes): ");
        int window=sc.nextInt();
        findTwoSumTimeWindow(target,window);
        detectDuplicates();
        System.out.print("Enter target for K-Sum: ");
        int ktarget=sc.nextInt();
        findKSum(3,ktarget);
    }
}