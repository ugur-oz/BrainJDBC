package com.ugur;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LosungForm {
    private Integer id;
    private String description;
    private Integer worsening_id;

    @Override
    public String toString() {
        return "LosungForm{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", worsening_id=" + worsening_id +
                '}';
    }
}


