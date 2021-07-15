package com.example.ssoserver;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Log4j2
@RestController
public class Controller {

    @GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getUserInfo(Model model, HttpServletRequest request) {
        Principal user = request.getUserPrincipal();
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
        AccessToken accessToken = token.getAccount().getKeycloakSecurityContext().getToken();

        KeycloakPrincipal principal = (KeycloakPrincipal)token.getPrincipal();

        String jwt = principal.getKeycloakSecurityContext().getTokenString();
        String[] chunks = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        HashMap<String, String> jwtMap = new HashMap<>();
        jwtMap.put("header", header);
        jwtMap.put("payload", payload);

        HashMap<String, String> accessTokenData = new HashMap<String, String>();
        accessTokenData.put("Preferred Username: ", accessToken.getPreferredUsername());
        accessTokenData.put("Email verified:", accessToken.getEmailVerified().toString());
        accessTokenData.put("Scope:", accessToken.getScope());
        accessTokenData.put("Roles:", accessToken.getRealmAccess().getRoles().toString());
        accessTokenData.put("Acr:", accessToken.getAcr());
        accessTokenData.put("SessionState:", accessToken.getSessionState());
        accessTokenData.put("User ID:", user.getName());
        accessTokenData.put("Type:", accessToken.getType());
        accessTokenData.put("Preferred Username: ", accessToken.getPreferredUsername());
        accessTokenData.put("Email:", accessToken.getEmail());
        accessTokenData.put("Family Name:", accessToken.getFamilyName());
        accessTokenData.put("Given Name:", accessToken.getGivenName());
        accessTokenData.put("Issuer:", accessToken.getIssuer());
        accessTokenData.put("JWT:", jwt);
        accessTokenData.put("JWT-parsed:", jwtMap.toString());

        return accessTokenData;
    }

}