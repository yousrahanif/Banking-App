package dev.codescreen;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController 
@SpringBootApplication
public class BalanceService {

    private List<Object> eventStore = new ArrayList<>();

    private Map<Integer, Double> balances = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(BalanceService.class, args);
    }

    @PutMapping("/loads")
    public double processLoad(@RequestBody TransactionRequest request) {
        FundsLoadedEvent event = new FundsLoadedEvent(request.getUserId(), request.getAmount());
        eventStore.add(event);
        applyEvent(event); 
        return balances.get(request.getUserId());
    }

    @PutMapping("/authorizations")
    public Object processAuthorization(@RequestBody TransactionRequest request) {
        int userId = request.getUserId();
        double amount = request.getAmount();

        TransactionAuthorizedEvent event = new TransactionAuthorizedEvent(userId, amount);
        eventStore.add(event);
        applyEvent(event); 
        return new HashMap<String, Double>() {{
            put("balance", balances.get(userId));
        }};
    }

    private void applyEvent(Object event) {
        if (event instanceof FundsLoadedEvent) {
            FundsLoadedEvent loadEvent = (FundsLoadedEvent) event;
            balances.put(loadEvent.getUserId(), balances.getOrDefault(loadEvent.getUserId(), 0.0) + loadEvent.getAmount());
        } else if (event instanceof TransactionAuthorizedEvent) {
            TransactionAuthorizedEvent authEvent = (TransactionAuthorizedEvent) event;
            int userId = authEvent.getUserId();
            double amount = authEvent.getAmount();
            if (!balances.containsKey(userId) || balances.get(userId) < amount) {
            } else {
                balances.put(userId, balances.get(userId) - amount);
            }
        }
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
}


































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
//      int value = 1; // You can modify this method to return any value you want.
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