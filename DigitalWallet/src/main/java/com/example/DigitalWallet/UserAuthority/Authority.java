package com.example.DigitalWallet.UserAuthority;

import com.example.DigitalWallet.model.Authorities;
import com.example.DigitalWallet.model.Role;

import java.util.*;

public class Authority {
    public static Set<Authorities> getAuthority(Role role){
        return switch(role) {
            case USER ->
                   Set.of(
                           Authorities.READ,
                           Authorities.UPDATE,
                           Authorities.WRITE
                   );
            case ADMIN , CONTROLLER ->
              Set.of(
                      Authorities.READ,
                      Authorities.UPDATE,
                      Authorities.WRITE,
                      Authorities.DELETE
              );
        };
    }
}
