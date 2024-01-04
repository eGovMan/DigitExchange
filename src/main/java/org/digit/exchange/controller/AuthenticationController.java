package org.digit.exchange.controller;

import org.digit.exchange.model.messages.Pin;
import org.digit.exchange.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


// ...
@Controller
@RequestMapping("/exchange/v1")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping(value = "/public/authenticate", method=RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Pin pin) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(pin.getUserId(), pin.getPin()));

            final String jwt = JwtUtil.generateToken(pin.getUserId());
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error during authentication");
        }
    }

    @RequestMapping(value = "/refresh", method=RequestMethod.POST)
    public ResponseEntity<?> refreshJwtToken(@RequestBody String refreshToken) {
        try {
            // Validate the refresh token
            if (!jwtUtil.validateToken(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
            }

            // Extract username from the token
            String username = jwtUtil.extractUsername(refreshToken);

            // Generate a new token with the same username
            String newToken = JwtUtil.generateToken(username);

            // Return the new token
            return ResponseEntity.ok(newToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not refresh token");
        }
    }
}
