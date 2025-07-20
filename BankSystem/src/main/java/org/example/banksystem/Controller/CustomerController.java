package org.example.banksystem.Controller;

import org.example.banksystem.Model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/bank")
public class CustomerController {

    ArrayList<Customer> customers = new ArrayList<>();

    @GetMapping("/get")
    public ResponseEntity<?> getCustomers(){
        if(customers.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer List Empty");
        }
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer){
        customers.add(customer);
        return ResponseEntity.status(HttpStatus.OK).body("Customer Added Successfully");
    }

    @PutMapping("/update/{index}")
    public ResponseEntity<?> updateCustomer(@PathVariable int index,@RequestBody Customer customer){
        if(customers.isEmpty() || index < 0 || index >= customers.size()){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Index Out Of Bound");

        }

        customers.set(index , customer);
        return ResponseEntity.status(HttpStatus.OK).body("Customer Updated!");
    }


    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String username){
        for(Customer c : customers){
            if(c.getUsername().equalsIgnoreCase(username)){
                customers.remove(c);
                return ResponseEntity.status(HttpStatus.OK).body("Customer with Username: " + c.getUsername() + " removed");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Not Found With username: "+ username);
    }


    @PostMapping("/deposit/{username}/{amount}")
    public ResponseEntity<?> depositMoney(@PathVariable double amount, @PathVariable String username){

        for(Customer c : customers){

            if(c.getUsername().equalsIgnoreCase(username)){
                if(amount < 0)
                    return ResponseEntity.badRequest().body("Amount shouldn't be negative!");

                c.setBalance(c.getBalance() + amount);
                return ResponseEntity.ok("Amount added to balance!");
            }
        }
        return ResponseEntity.badRequest().body("Customer With Given username not found.");
    }

    @PostMapping("/withdraw/{username}/{amount}")
    public ResponseEntity<?> withdrawMoney(@PathVariable double amount, @PathVariable String username){

        for(Customer c : customers){
            if(c.getUsername().equalsIgnoreCase(username)){
                if(amount < 0)
                    return ResponseEntity.badRequest().body("Amount shouldn't be negative!");
                if(c.getBalance() < amount)
                    return ResponseEntity.badRequest().body("Insufficient Balance.");

                c.setBalance(c.getBalance() - amount);
                return ResponseEntity.ok("Withdrawal Successful, new balance: " + c.getBalance());
            }
        }
        return ResponseEntity.badRequest().body("Customer With Given username not found.");
    }




}
