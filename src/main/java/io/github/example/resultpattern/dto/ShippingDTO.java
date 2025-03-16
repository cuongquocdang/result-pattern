package io.github.example.resultpattern.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ShippingDTO {

    private String shippingId;

    private AddressDTO address;
}
