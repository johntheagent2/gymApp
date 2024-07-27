package com.example.gymapp.entity.criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnumFilter<ProgramType> {

    private ProgramType value;

    public EnumFilter(ProgramType value) {
        this.value = value;
    }
}
