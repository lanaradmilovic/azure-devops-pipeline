package rs.ac.bg.fon.taskservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rs.ac.bg.fon.taskservice.model.User;

@FeignClient(name = "userservice", url = "localhost:8081")
public interface UserServiceClient {
    @PostMapping
    ResponseEntity<User> createUser(@RequestBody User user);

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id);

    @GetMapping("/name/{name}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String name);
}
