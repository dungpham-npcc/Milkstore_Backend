package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.AddressModel.AddressDTO;
import com.cookswp.milkstore.pojo.entities.UserAddress;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.address.AddressService;
import com.cookswp.milkstore.service.role.RoleService;
import com.cookswp.milkstore.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    private final ModelMapper mapper;

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService, ModelMapper mapper){
        this.addressService = addressService;
        this.mapper = mapper;
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<AddressDTO>> getAllAddressesOfUser(@PathVariable int userId){
        return new ResponseData<>(HttpStatus.OK.value(),
                "Addresses retrieved successfully!",
                addressService.getAllAddressesOfUser(userId).stream()
                        .map(address ->
                                mapper.map(address, AddressDTO.class)
                        )
                        .toList());
    }

    @GetMapping("/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<AddressDTO> getAnAddress(@PathVariable int addressId){
        return new ResponseData<>(HttpStatus.OK.value(), "Address retrieved successfully!",
                mapper.map(addressService.getAnAddress(addressId), AddressDTO.class));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<AddressDTO> addAddress(AddressDTO address){
        if (address.getDistrict() == null ||
        address.getAddressLine() == null)
            throw new IllegalArgumentException("Address line and district ");

        UserAddress addressToAdd = mapper.map(address, UserAddress.class);
        addressService.createAddress(addressToAdd);
        return new ResponseData<>(HttpStatus.OK.value(), "Address added successfully!", address);
    }

    @PutMapping("/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<AddressDTO> updateAddress(@PathVariable int addressId, AddressDTO address){
        if (address.getDistrict() == null ||
                address.getAddressLine() == null)
            throw new IllegalArgumentException("Address line and district ");

        UserAddress addressToUpdate = mapper.map(address, UserAddress.class);

        addressService.updateAddress(addressId, addressToUpdate);
        return new ResponseData<>(HttpStatus.OK.value(), "Address updated successfully!", null);
    }

    @DeleteMapping("/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<AddressDTO> deleteAddress(@PathVariable int addressId){
        addressService.deleteAddress(addressId);
        return new ResponseData<>(HttpStatus.OK.value(), "Address deleted successfully!", null);
    }

    @PatchMapping("/set-default/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<AddressDTO> setDefaultAddress(@PathVariable int addressId){
        addressService.setDefaultAddress(addressId);
        return new ResponseData<>(HttpStatus.OK.value(), "Address set to default!", null);
    }


}
