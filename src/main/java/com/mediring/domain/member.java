package com.mediring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class member {
    private String id;
    private String pw;
    private String name;
    private String email;
    private String tel;
    private String addr1;
    private String addr2;
    private String postcode;
    private String regdate;
    private int pt;
    private int visited;
}
