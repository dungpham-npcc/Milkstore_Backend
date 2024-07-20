package com.cookswp.milkstore.service.address;

import com.cookswp.milkstore.pojo.entities.UserAddress;
import com.cookswp.milkstore.repository.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {
    @Autowired
    private final AddressRepository addressRepository;

    public List<UserAddress> getAllAddressesOfUser(int userId){
        return addressRepository.findByUserId(userId);
    }

    public UserAddress getAnAddress(int addressId){
        UserAddress currentAddress = addressRepository.findById(addressId);
        if (currentAddress == null) throw new IllegalArgumentException("Address not found");
        return currentAddress;
    }

    public UserAddress createAddress(UserAddress address){
        return addressRepository.save(address);
    }

    public UserAddress updateAddress(int id, UserAddress address){
        UserAddress currentAddress = addressRepository.findById(id);
        if (currentAddress == null) throw new IllegalArgumentException("Address not found");

        if (!address.getAddressAlias().equals(currentAddress.getAddressAlias()))
            currentAddress.setAddressAlias(address.getAddressAlias());

        if (!address.getAddressLine().equals(currentAddress.getAddressLine()))
            currentAddress.setAddressLine(address.getAddressLine());

        if (!address.getDistrict().equals(currentAddress.getDistrict()))
            currentAddress.setDistrict(address.getDistrict());

        return addressRepository.save(currentAddress);
    }

    public void deleteAddress(int id){
        addressRepository.deleteById(id);
    }

    public void setDefaultAddress(int id){
        UserAddress currentAddress = addressRepository.findById(id);
        if (currentAddress == null) throw new IllegalArgumentException("Address not found");

        currentAddress.setDefault(true);
        addressRepository.save(currentAddress);
    }

    public void unsetDefaultAddress(int id){
        UserAddress currentAddress = addressRepository.findById(id);
        if (currentAddress == null) throw new IllegalArgumentException("Address not found");

        currentAddress.setDefault(false);
        addressRepository.save(currentAddress);
    }

}
