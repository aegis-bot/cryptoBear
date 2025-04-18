package com.trade.cryptoBear.RequestObject;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentityRequest {
    @NotBlank
    private String username;  // obviously security flaw, use this in place of cookies

}
