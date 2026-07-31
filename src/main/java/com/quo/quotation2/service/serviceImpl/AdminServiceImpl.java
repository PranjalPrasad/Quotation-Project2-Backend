package com.quo.quotation2.service.serviceImpl;


import com.quo.quotation2.dto.requestdto.ChangePasswordRequestDto;
import com.quo.quotation2.dto.requestdto.LoginRequestDto;
import com.quo.quotation2.dto.responsedto.AdminProfileResponseDto;
import com.quo.quotation2.dto.responsedto.LoginResponseDto;
import com.quo.quotation2.entity.Admin;
import com.quo.quotation2.exception.InvalidCredentialsException;
import com.quo.quotation2.exception.InvalidTokenException;
import com.quo.quotation2.repository.AdminRepository;
import com.quo.quotation2.service.AdminService;
import com.quo.quotation2.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        if (requestDto.getEmail() == null || requestDto.getPassword() == null) {
            throw new InvalidCredentialsException("Email and password are required");
        }

        Admin admin = adminRepository.findByEmail(requestDto.getEmail().trim())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(requestDto.getPassword(), admin.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(admin.getId(), admin.getEmail());

        return new LoginResponseDto(admin.getId(), admin.getEmail(), admin.getName(), token, "Bearer");
    }

    @Override
    public AdminProfileResponseDto getProfile(String token) {
        Admin admin = resolveAdminFromToken(token);
        return new AdminProfileResponseDto(admin.getId(), admin.getEmail(), admin.getName(), admin.getCreatedAt());
    }

    @Override
    public void changePassword(String token, ChangePasswordRequestDto requestDto) {
        Admin admin = resolveAdminFromToken(token);

        if (!passwordEncoder.matches(requestDto.getOldPassword(), admin.getPassword())) {
            throw new InvalidCredentialsException("Old password is incorrect");
        }

        admin.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        admin.setUpdatedAt(LocalDateTime.now());
        adminRepository.save(admin);
    }

    private Admin resolveAdminFromToken(String token) {
        String rawToken = stripBearer(token);

        if (rawToken == null || !jwtUtil.isTokenValid(rawToken)) {
            throw new InvalidTokenException("Invalid or expired token");
        }

        String email = jwtUtil.extractEmail(rawToken);

        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidTokenException("Admin not found for this token"));
    }

    private String stripBearer(String token) {
        if (token == null) {
            return null;
        }
        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }
}

