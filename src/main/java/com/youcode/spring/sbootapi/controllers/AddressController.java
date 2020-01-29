package com.youcode.spring.sbootapi.controllers;

import com.youcode.spring.sbootapi.dtos.response.addresses.AddressListDto;
import com.youcode.spring.sbootapi.errors.exceptions.PermissionDeniedException;
import com.youcode.spring.sbootapi.models.Address;
import com.youcode.spring.sbootapi.models.User;
import com.youcode.spring.sbootapi.services.auth.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddressController {

    @Autowired
    UsersService usersService;

    @GetMapping({"my_addresses", "addresses"})
    public AddressListDto myAddresses() {
        User user = usersService.getCurrentLoggedInUser();
        if (user == null)
            throw new PermissionDeniedException("You are not logged In");
        List<Address> addresses = user.getAddresses();
        return AddressListDto.build(addresses);
    }
}
