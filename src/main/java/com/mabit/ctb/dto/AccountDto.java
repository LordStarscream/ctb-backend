package com.mabit.ctb.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String name;
    private String referenceCurrency;
    private String information;
    private String type;
}
