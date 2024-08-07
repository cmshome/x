package com.lxk.bean.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author LiXuekai on 2024/8/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwitchParts {
    private String name;
    private LocalDate time;
    private int value;
}
