package com.example.gymapp.entity.criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnumFilter<TrackingType> {

    private TrackingType value;

    public EnumFilter(TrackingType value) {
        this.value = value;
    }
}
