package com.example.marketplace.util;


import com.example.marketplace.model.Company;
import com.example.marketplace.model.User;
import com.example.marketplace.payload.CreateCompanyInput;
import com.example.marketplace.payload.CreateUserInput;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    public User createInputToModel(CreateUserInput createInput) {

        return new User(
                createInput.name(),
                createInput.username(),
                createInput.email(),
                createInput.password()
        );
    }

    public Company createInputToModel(CreateCompanyInput createInput) {
        return new Company(
                createInput.name(),
                createInput.address()
        );
    }

    public User createInputToUser(CreateCompanyInput createInput) {
        return new User(
                createInput.name(),
                createInput.username(),
                createInput.email(),
                createInput.password()
        );
    }
}
