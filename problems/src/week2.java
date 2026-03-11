import java.util.*;
public class week2 {
    static class TrieNode {
        HashMap<Character, TrieNode> children = new HashMap<>();
        boolean isEnd = false;
    }
    static TrieNode root = new TrieNode();
    static HashMap<String,Integer> frequency = new HashMap<>();
    public static void addQuery(String query) {
        TrieNode node = root;
        for(char c : query.toCharArray()) {
            node.children.putIfAbsent(c,new TrieNode());
            node = node.children.get(c);
        }
        node.isEnd = true;
        frequency.put(query,frequency.getOrDefault(query,0)+1);
    }
    public static void collectQueries(TrieNode node,String prefix,List<String> results) {
        if(node.isEnd) results.add(prefix);
        for(char c : node.children.keySet()) {
            collectQueries(node.children.get(c),prefix+c,results);
        }
    }
    public static List<String> search(String prefix) {
        TrieNode node = root;
        for(char c : prefix.toCharArray()) {
            if(!node.children.containsKey(c)) return new ArrayList<>();
            node = node.children.get(c);
        }
        List<String> results = new ArrayList<>();
        collectQueries(node,prefix,results);
        results.sort((a,b)->frequency.get(b)-frequency.get(a));
        if(results.size()>10) return results.subList(0,10);
        return results;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of queries to store: ");
        int n = sc.nextInt();
        sc.nextLine();
        for(int i=0;i<n;i++) {
            System.out.print("Enter query: ");
            String q = sc.nextLine();
            addQuery(q);
        }
        System.out.print("Enter prefix to search: ");
        String prefix = sc.nextLine();
        List<String> suggestions = search(prefix);
        System.out.println("Suggestions:");
        for(String s : suggestions) {
            System.out.println(s + " (" + frequency.get(s) + " searches)");
        }
    }
}