package com.example.marketplace.payload;


import lombok.Data;


public record CreateCompanyInput(String address, String name, String username, String password, String email) {

}
