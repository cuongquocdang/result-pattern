package io.github.example.resultpattern.service;

import io.github.example.resultpattern.dto.ShippingDTO;
import io.github.example.resultpattern.shared.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class TestValidation extends Validator<ShippingDTO> {

    public TestValidation() {
        ruleFor(ShippingDTO::getShippingId)
                .notEmpty("shippingId must not be empty");
        ruleFor(ShippingDTO::getAddress)
                .notNull("address must not be null");
        ruleFor(shippingDTO -> shippingDTO.getAddress().getCountry())
                .notEmpty("country must not be empty")
                .must(o -> o.equalsIgnoreCase("vn"), "country must be vn");
        ruleFor(ShippingDTO::getAddress)
                .must(addressDTO -> addressDTO.getCountry().equals("CN"), "country must be CN");
    }
}
