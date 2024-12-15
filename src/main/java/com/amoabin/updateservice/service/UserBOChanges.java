package com.amoabin.updateservice.service;


import com.amoabin.updateservice.service.util.Indicator;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class UserBOChanges implements Diffable<UserBOChanges> {
    private Long userId;
    private String name;
    private String surname;
    private String email;
    private String indicators;
    @Override
    public boolean hasChanges(UserBOChanges other) {

        if (!Objects.equals(this.name, other.name)) {
            return true;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return true;
        }
        if (!Objects.equals(this.email, other.email)) {
            return true;
        }
        return !Objects.equals(this.indicators, other.indicators);
    }
}
