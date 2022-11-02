package com.exchange.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;


@Api("Authorization handler. Not secure. You can invoke it and get token in answer")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    JwtEncoder encoder;



    @ApiOperation("Handler for login operation. Use basic authorization")
    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login() {
        Instant now = Instant.now();
        long expiry = 36000L;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // @formatter:off
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        // @formatter:on

        String tokenValue = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + tokenValue)
                .body(tokenValue);
    }
}
