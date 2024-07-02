package com.example.gymapp.entity.criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnumFilter<GiftStatus> {

    private GiftStatus value;

    public EnumFilter(GiftStatus value) {
        this.value = value;
    }
}
