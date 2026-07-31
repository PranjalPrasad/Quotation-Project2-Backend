package com.quo.quotation2.service;

import com.quo.quotation2.dto.requestdto.ChangePasswordRequestDto;
import com.quo.quotation2.dto.requestdto.LoginRequestDto;
import com.quo.quotation2.dto.responsedto.AdminProfileResponseDto;
import com.quo.quotation2.dto.responsedto.LoginResponseDto;

public interface AdminService {

    LoginResponseDto login(LoginRequestDto requestDto);

    AdminProfileResponseDto getProfile(String token);

    void changePassword(String token, ChangePasswordRequestDto requestDto);
}
