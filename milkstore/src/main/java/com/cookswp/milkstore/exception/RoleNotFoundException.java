package com.cookswp.milkstore.exception;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(){
        super("Role not found in the system!");
    }
}
