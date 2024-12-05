package kr.co.xognstl.myrestfulservice.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import kr.co.xognstl.myrestfulservice.bean.Post;
import kr.co.xognstl.myrestfulservice.bean.User;
import kr.co.xognstl.myrestfulservice.exception.UserNotFoundException;
import kr.co.xognstl.myrestfulservice.repository.PostRepository;
import kr.co.xognstl.myrestfulservice.repository.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserJpaController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    //    @GetMapping("/users")
//    public List<User> retrieveAllUsers() {
//        return useRepository.findAll();
//    }

    @GetMapping("/users")
    public Map<String, Object> retrieveAllUsers() { //Count 추가
        List<User> users = userRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("count", users.size());
        return response;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity retrieveUsersById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {    // 존재 유무
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        EntityModel entityModel = EntityModel.of(user.get()); //user.get() optional 에서 가져옴

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User saveUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") // 경로
                .buildAndExpand(saveUser.getId()) // id 값에 들어갈 거
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("id-" + id);
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity createPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("id = " + id);
        }
        User user = userOptional.get();
        post.setUser(user);

        postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") // 경로
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
