package com.example.ssoserver;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
public class Controller {

    @GetMapping(path="/user")
    public String getResource(){
        return "protected resource";
    }

    @GetMapping(path="/test")
    public String getTest(){
        return "test";
    }

    @GetMapping(path="/admin")
    public String getAdmin(){
        return "admin";
    }


    @GetMapping(path = "/user/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getUserInfo(Model model, HttpServletRequest request) {
        Principal user = request.getUserPrincipal();
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
        AccessToken accessToken = token.getAccount().getKeycloakSecurityContext().getToken();

        KeycloakPrincipal principal=(KeycloakPrincipal)token.getPrincipal();

        HashMap<String, String> accessTokenData = new HashMap<String, String>();;
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
        accessTokenData.put("JWT:", principal.getKeycloakSecurityContext().getTokenString());

        return accessTokenData;
    }

}