package com.tiba.invoice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {

    /** Indicates whether the user must change their password upon their next login. */
    private boolean passwordChangeRequired;
    
    private String access_token;

    private String token_type;

    private long expires_in;
}
