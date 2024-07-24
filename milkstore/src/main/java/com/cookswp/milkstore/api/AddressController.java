package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.AddressModel.AddressDTO;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.pojo.entities.UserAddress;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.address.AddressService;
import com.cookswp.milkstore.service.role.RoleService;
import com.cookswp.milkstore.service.user.UserService;
import net.minidev.json.JSONObject;
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

    private final UserService userService;

    @Autowired
    public AddressController(AddressService addressService, ModelMapper mapper, UserService userService){
        this.addressService = addressService;
        this.mapper = mapper;
        this.userService = userService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<AddressDTO>> getAllAddressesOfUser(){
        return new ResponseData<>(HttpStatus.OK.value(),
                "Addresses retrieved successfully!",
                addressService.getAllAddressesOfUser(userService.getCurrentUser().getUserId()).stream()
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
    public ResponseData<AddressDTO> addAddress(@RequestBody AddressDTO address){
        if (address.getDistrict() == null ||
        address.getAddressLine() == null)
            throw new IllegalArgumentException("Địa chỉ và quận huyện không được để trống");
        address.setUserId(userService.getCurrentUser().getUserId());
        UserAddress addressToAdd = mapper.map(address, UserAddress.class);
        addressService.createAddress(addressToAdd);
        return new ResponseData<>(HttpStatus.OK.value(), "Address added successfully!", address);
    }

    @PutMapping("/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<AddressDTO> updateAddress(@PathVariable int addressId, @RequestBody AddressDTO address){
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
        User user = userService.getCurrentUser();
        List<UserAddress> addresses = addressService
                .getAllAddressesOfUser(user.getUserId());
        for (var item:
             addresses) {
            if (item.isDefault()) {
                addressService.unsetDefaultAddress(item.getId());
                break;
            }
        }
        addressService.setDefaultAddress(addressId);
        return new ResponseData<>(HttpStatus.OK.value(), "Address set to default!", null);
    }


}
