package com.quo.quotation2.dto.responsedto;

public class LoginResponseDto {

    private Long id;
    private String email;
    private String name;
    private String token;
    private String tokenType;

    public LoginResponseDto() {
    }

    public LoginResponseDto(Long id, String email, String name, String token, String tokenType) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.token = token;
        this.tokenType = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}

