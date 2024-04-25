



























//package dev.codescreen;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//
//@SpringBootApplication
//public class BalanceService {
//
//  // Event store (can be a database table or any persistent storage)
//  private List<Object> eventStore = new ArrayList<>();
//
//  // In-memory database to store current balances
//  private Map<Integer, Double> balances = new HashMap<>();
//
//  public static void main(String[] args) {
//      SpringApplication.run(BalanceService.class, args);
//  }
//
//  @PutMapping("/loads")
//  public double processLoad(@RequestBody TransactionRequest request) {
//      FundsLoadedEvent event = new FundsLoadedEvent(request.getUserId(), request.getAmount());
//      eventStore.add(event);
//      applyEvent(event); // Apply event to update balances
//      return balances.get(request.getUserId());
//  }
//
//  @PutMapping("/authorizations")
//  public Object processAuthorization(@RequestBody TransactionRequest request) {
//      int userId = request.getUserId();
//      double amount = request.getAmount();
//      // Check if balance is sufficient
//
//      TransactionAuthorizedEvent event = new TransactionAuthorizedEvent(userId, amount);
//      eventStore.add(event);
//      applyEvent(event); // Apply event to update balances
//      return new HashMap<String, Double>() {{
//          put("balance", balances.get(userId));
//      }};
//  }
//
//  // Apply event to update balances
//  private void applyEvent(Object event) {
//      if (event instanceof FundsLoadedEvent) {
//          FundsLoadedEvent loadEvent = (FundsLoadedEvent) event;
//          balances.put(loadEvent.getUserId(), balances.getOrDefault(loadEvent.getUserId(), 0.0) + loadEvent.getAmount());
//      } else if (event instanceof TransactionAuthorizedEvent) {
//          TransactionAuthorizedEvent authEvent = (TransactionAuthorizedEvent) event;
//          int userId = authEvent.getUserId();
//          double amount = authEvent.getAmount();
//          if (!balances.containsKey(userId) || balances.get(userId) < amount) {
//              // Handle insufficient balance
//          } else {
//              balances.put(userId, balances.get(userId) - amount);
//          }
//      }
//  }
//
//  // Define events
//  static class FundsLoadedEvent {
//      private int userId;
//      private double amount;
//
//      FundsLoadedEvent(int userId, double amount) {
//          this.userId = userId;
//          this.amount = amount;
//      }
//
//      public int getUserId() {
//          return userId;
//      }
//
//      public double getAmount() {
//          return amount;
//      }
//  }
//
//  static class TransactionAuthorizedEvent {
//      private int userId;
//      private double amount;
//
//      TransactionAuthorizedEvent(int userId, double amount) {
//          this.userId = userId;
//          this.amount = amount;
//      }
//
//      public int getUserId() {
//          return userId;
//      }
//
//      public double getAmount() {
//          return amount;
//      }
//  }
//
//  static class TransactionRequest {
//      private int userId;
//      private double amount;
//
//      public int getUserId() {
//          return userId;
//      }
//
//      public void setUserId(int userId) {
//          this.userId = userId;
//      }
//
//      public double getAmount() {
//          return amount;
//      }
//
//      public void setAmount(double amount) {
//          this.amount = amount;
//      }
//  }
//}
//



//package dev.codescreen;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//@SpringBootApplication
//public class BalanceService {
//
//  // In-memory database to store balances
//  private Map<Integer, Double> balances = new HashMap<>();
//
//  public static void main(String[] args) {
//      SpringApplication.run(BalanceService.class, args);
//  }
//
//  @PutMapping("/loads")
//  public double processLoad(@RequestBody TransactionRequest request) {
//      int userId = request.getUserId();
//      double amount = request.getAmount();
//
//      // Update balance
//      balances.put(userId, balances.getOrDefault(userId, 0.0) + amount);
//      return balances.get(userId);
//  }
//
//  @PutMapping("/authorizations")
//  public Object processAuthorization(@RequestBody TransactionRequest request) {
//      int userId = request.getUserId();
//      double amount = request.getAmount();
//
//      // Check if balance is sufficient
//      if (!balances.containsKey(userId) || balances.get(userId) < amount) {
//          return new HashMap<String, String>() {{
//              put("message", "Authorization declined");
//          }};
//      }
//
//      // Update balance
//      balances.put(userId, balances.get(userId) - amount);
//      return new HashMap<String, Double>() {{
//          put("balance", balances.get(userId));
//      }};
//  }
//
//  static class TransactionRequest {
//      private int userId;
//      private double amount;
//
//      // Getters and setters
//      public int getUserId() { 
//      	return userId; 
//      	}
//      public void setUserId(int userId) { 
//      	this.userId = userId; 
//      	}
//      public double getAmount() { 
//      	return amount; 
//      	}
//      public void setAmount(double amount) { 
//      	this.amount = amount; 
//      	}
//  }
//}
