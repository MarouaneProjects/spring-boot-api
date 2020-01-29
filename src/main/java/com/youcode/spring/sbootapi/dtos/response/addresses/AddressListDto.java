package com.youcode.spring.sbootapi.dtos.response.addresses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youcode.spring.sbootapi.dtos.response.base.SuccessResponse;
import com.youcode.spring.sbootapi.models.Address;
import com.youcode.spring.sbootapi.dtos.response.base.SuccessResponse;
import com.youcode.spring.sbootapi.models.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressListDto extends SuccessResponse {
    @JsonProperty("addresses")
    private final List<AddressExcludeUserDto> addressDtos;

    public AddressListDto(List<AddressExcludeUserDto> addressDtos) {
        this.addressDtos = addressDtos;
    }

    public static AddressListDto build(List<Address> addresses) {
        List<AddressExcludeUserDto> addressDtos = new ArrayList<>(addresses.size());
        addresses.forEach(a -> addressDtos.add(AddressExcludeUserDto.build(a)));
        return new AddressListDto(addressDtos);
    }

    public List<AddressExcludeUserDto> getAddressDtos() {
        return addressDtos;
    }
}
