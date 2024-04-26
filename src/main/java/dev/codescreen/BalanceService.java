package dev.codescreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@SpringBootApplication
public class BalanceService {

    private List<Object> eventStore = new ArrayList<>();
    private Map<Integer, Double> balances = new HashMap<>();
    private Map<Integer, List<Transaction>> transactionHistory = new HashMap<>(); // Transaction history for each user

    public static void main(String[] args) {
        SpringApplication.run(BalanceService.class, args);
    }


    @GetMapping("/transaction-history")
    public List<Transaction> getTransactionHistory(@RequestParam int userId) {
        return transactionHistory.getOrDefault(userId, new ArrayList<>());
    }
    
    
    @PutMapping("/loads")
    public double processLoad(@RequestBody TransactionRequest request) {
        double amount = request.getAmount();
        if (amount < 0) {
            // Do not allow loading negative amounts
            return -1; // Return -1 to indicate error
        }
        FundsLoadedEvent event = new FundsLoadedEvent(request.getUserId(), amount);
        eventStore.add(event);
        applyEvent(event);
        return balances.get(request.getUserId());
    }




    


    @PutMapping("/authorizations")
    public ResponseEntity<Double> processAuthorization(@RequestBody TransactionRequest request) {
        int userId = request.getUserId();
        double amount = request.getAmount();

        System.out.println("Authorization request received for User ID: " + userId + ", Amount: " + amount);

        if (!balances.containsKey(userId) || balances.get(userId) < amount) {
            // Add failed transaction to transaction history
            addToTransactionHistory(userId, amount, "Authorization", LocalDateTime.now(), "Failed");

            // Return 400 status for insufficient balance
            return ResponseEntity.badRequest().body(null);
        }

        TransactionAuthorizedEvent event = new TransactionAuthorizedEvent(userId, amount);
        eventStore.add(event);
        applyEvent(event);

        double newBalance = balances.get(userId);
        System.out.println("New balance after authorization: " + newBalance);

        // If the transaction was successful, return 200 status with the new balance
        return ResponseEntity.ok(newBalance);
    }
    

    @GetMapping("/total-balance")
    public ResponseEntity<Double> getTotalBalance(@RequestParam int userId) {
        List<Transaction> transactions = transactionHistory.getOrDefault(userId, Collections.emptyList());
        double totalBalance = calculateTotalBalance(transactions);
        return ResponseEntity.ok(totalBalance);
    }

    private double calculateTotalBalance(List<Transaction> transactions) {
        double totalBalance = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("Load")) {
                totalBalance += transaction.getAmount();
            } else if (transaction.getType().equals("Authorization") && transaction.getStatus().equals("Success")) {
                totalBalance -= transaction.getAmount();
            }
        }
        return totalBalance;
    }


    private void applyEvent(Object event) {
        if (event instanceof FundsLoadedEvent) {
            FundsLoadedEvent loadEvent = (FundsLoadedEvent) event;
            balances.put(loadEvent.getUserId(), balances.getOrDefault(loadEvent.getUserId(), 0.0) + loadEvent.getAmount());
            addToTransactionHistory(loadEvent.getUserId(), loadEvent.getAmount(), "Load", LocalDateTime.now(), "Success");
        } else if (event instanceof TransactionAuthorizedEvent) {
            TransactionAuthorizedEvent authEvent = (TransactionAuthorizedEvent) event;
            int userId = authEvent.getUserId();
            double amount = authEvent.getAmount();
            if (!balances.containsKey(userId) || balances.get(userId) < amount) {
                addToTransactionHistory(userId, amount, "Authorization", LocalDateTime.now(), "Failed");
            } else {
                balances.put(userId, balances.get(userId) - amount);
                addToTransactionHistory(userId, amount, "Authorization", LocalDateTime.now(), "Success");
            }
        }
    }




    private void addToTransactionHistory(int userId, double amount, String type, LocalDateTime timestamp, String status) {
        List<Transaction> userTransactions = transactionHistory.getOrDefault(userId, new ArrayList<>());

        // Check if the transaction is an authorization and it failed
        if (type.equals("Authorization") && status.equals("Failed")) {
            // Add the failed authorization only to the transaction history of the corresponding user ID
            userTransactions.add(new Transaction(amount, type, timestamp, status));
        } else {
            // For other types of transactions or successful authorizations, proceed as usual
            userTransactions.add(new Transaction(amount, type, timestamp, status));
        }

        transactionHistory.put(userId, userTransactions);
    }


    static class FundsLoadedEvent {
        private int userId;
        private double amount;

        FundsLoadedEvent(int userId, double amount) {
            this.userId = userId;
            this.amount = amount;
        }

        public int getUserId() {
            return userId;
        }

        public double getAmount() {
            return amount;
        }
    }

    static class TransactionAuthorizedEvent {
        private int userId;
        private double amount;

        TransactionAuthorizedEvent(int userId, double amount) {
            this.userId = userId;
            this.amount = amount;
        }

        public int getUserId() {
            return userId;
        }

        public double getAmount() {
            return amount;
        }
    }

    static class TransactionRequest {
        private int userId;
        private double amount;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

    static class Transaction implements Serializable {
        private double amount;
        private String type;
        private LocalDateTime timestamp;
        private String status;

        public Transaction(double amount, String type, LocalDateTime timestamp, String status) {
            this.amount = amount;
            this.type = type;
            this.timestamp = timestamp;
            this.status = status;
        }

        // Getters and setters for Jackson serialization
        public double getAmount() {
            return amount;
        }

        public String getType() {
            return type;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
    }
}























//placeholder





















//package dev.codescreen;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class BalanceService {
//
//  @GetMapping("/dummy-value")
//  public ResponseEntity<Integer> bar() {
//      int value = 1; 
//      return ResponseEntity.ok(value);
//  }
//}
//package dev.codescreen;
//
///**
//* Dummy class with a dummy method.
//* Rename this class and method to a name that is more appropriate to your coding test.
//*/
//public class Foo {
//
///**
// *
// *
// * @return
// */
//public static int bar() {
//  return 1;
//}
//}