package com.example.demo.restApi.controller;

import com.example.demo.restApi.model.CreateUserRequestModel;
import com.example.demo.restApi.model.CreateUserResponseModel;
import com.example.demo.restApi.model.LoginRequestModel;
import com.example.demo.restApi.service.UserService;
import com.example.demo.DAO.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @GetMapping("/status/check")
    public String status(){
        return "it working on port " + env.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody CreateUserRequestModel userDetails){
        // TODO implementation is here

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser =  userService.createUser(userDto);

        CreateUserResponseModel returnUser = modelMapper.map(createdUser,CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnUser);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestModel loginRequestModel){
        return "Login Successful!";
    }
}
