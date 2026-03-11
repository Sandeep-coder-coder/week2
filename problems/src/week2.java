import java.util.*;
public class week2 {
    static class TokenBucket {
        int tokens;
        int maxTokens;
        long lastRefillTime;
        int refillRate;
        TokenBucket(int maxTokens, int refillRate) {
            this.maxTokens = maxTokens;
            this.tokens = maxTokens;
            this.refillRate = refillRate;
            this.lastRefillTime = System.currentTimeMillis();
        }
        void refill() {
            long now = System.currentTimeMillis();
            long elapsed = (now - lastRefillTime) / 1000;
            if (elapsed > 0) {
                int refillTokens = (int) (elapsed * refillRate);
                tokens = Math.min(maxTokens, tokens + refillTokens);
                lastRefillTime = now;
            }
        }
        boolean allowRequest() {
            refill();
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }
        int getRemaining() {
            refill();
            return tokens;
        }
    }
    static HashMap<String, TokenBucket> clients = new HashMap<>();
    static final int LIMIT = 1000;
    static final int REFILL_RATE = 1000 / 3600;
    public static String checkRateLimit(String clientId) {
        clients.putIfAbsent(clientId, new TokenBucket(LIMIT, REFILL_RATE));
        TokenBucket bucket = clients.get(clientId);
        if (bucket.allowRequest()) {
            return "Allowed (" + bucket.getRemaining() + " requests remaining)";
        } else {
            return "Denied (Rate limit exceeded)";
        }
    }
    public static String getRateLimitStatus(String clientId) {
        TokenBucket bucket = clients.get(clientId);
        if (bucket == null) {
            return "No usage yet";
        }
        int used = LIMIT - bucket.getRemaining();
        return "{used: " + used + ", limit: " + LIMIT + "}";
    }
    public static void main(String[] args) {
        String client = "abc123";
        System.out.println(checkRateLimit(client));
        System.out.println(checkRateLimit(client));
        System.out.println(checkRateLimit(client));
        System.out.println(getRateLimitStatus(client));
    }
}