package com.dineshwar.uniworks.controllers;

import com.dineshwar.uniworks.models.AppUser;
import com.dineshwar.uniworks.models.LoginDto;
import com.dineshwar.uniworks.models.RegisterDto;
import com.dineshwar.uniworks.repositories.AppUserRepository;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        if (appUserRepository.findByUsername(registerDto.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }

        if (appUserRepository.findByEmail(registerDto.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }

        AppUser appUser = new AppUser();
        appUser.setFirstName(registerDto.getFirstname());
        appUser.setLastName(registerDto.getLastname());
        appUser.setUsername(registerDto.getUsername());
        appUser.setEmail(registerDto.getEmail());
        appUser.setRole("Client");
        appUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        appUser.setCreatedAt(new Date());

        try {
            appUserRepository.save(appUser);
            String jwtToken = createJwtToken(appUser);

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwtToken);
            response.put("user", appUser);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Registration failed due to an internal error.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        AppUser appUser = appUserRepository.findByUsername(loginDto.getUsername());

        if (appUser == null || !passwordEncoder.matches(loginDto.getPassword(), appUser.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password.");
        }

        String jwtToken = createJwtToken(appUser);

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtToken);
        response.put("user", appUser);

        return ResponseEntity.ok(response);
    }

    private String createJwtToken(AppUser appUser) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(24 * 3600))
                .subject(appUser.getUsername())
                .claim("role", appUser.getRole())
                .build();

        SecretKeySpec secretKey = new SecretKeySpec(jwtSecretKey.getBytes(), "HmacSHA256");
        JwtEncoder encoder = new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        JwtEncoderParameters params = JwtEncoderParameters.from(jwsHeader, claims);

        return encoder.encode(params).getTokenValue();
    }
}
