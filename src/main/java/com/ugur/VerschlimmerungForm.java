package com.ugur;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class VerschlimmerungForm {
    private Integer id;
    private String description;
    private Integer problem_id;

    @Override
    public String toString() {
        return "VerschlimmerungForm{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", problem_id=" + problem_id +
                '}';
    }
}
