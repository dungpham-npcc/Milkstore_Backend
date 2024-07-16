//package com.cookswp.milkstore.pojo.dtos.UserModel;
//
//import com.cookswp.milkstore.repositories.RoleRepository.RoleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class RoleService {
//    private final RoleRepository roleRepository;
//
//    @Autowired
//    public RoleService(RoleRepository roleRepository){
//        this.roleRepository = roleRepository;
//    }
//
//    public List<Role> getRoles(){
//        return roleRepository.findAll();
//    }
//
//    public Role getRoleByRoleName(String roleName){
//        return roleRepository.findByRoleName(roleName);
//    }
//}
