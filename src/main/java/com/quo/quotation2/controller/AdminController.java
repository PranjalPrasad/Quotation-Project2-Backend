package com.quo.quotation2.controller;


import com.quo.quotation2.dto.requestdto.ChangePasswordRequestDto;
import com.quo.quotation2.dto.requestdto.LoginRequestDto;
import com.quo.quotation2.dto.responsedto.AdminProfileResponseDto;
import com.quo.quotation2.dto.responsedto.ApiResponseDto;
import com.quo.quotation2.dto.responsedto.LoginResponseDto;
import com.quo.quotation2.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // POST /api/admin/login
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = adminService.login(requestDto);
        return ResponseEntity.ok(ApiResponseDto.success("Login successful", response));
    }

    // GET /api/admin/profile  (Header: Authorization: Bearer <token>)
    @GetMapping("/profile")
    public ResponseEntity<ApiResponseDto<AdminProfileResponseDto>> getProfile(
            @RequestHeader("Authorization") String authHeader) {
        AdminProfileResponseDto profile = adminService.getProfile(authHeader);
        return ResponseEntity.ok(ApiResponseDto.success("Profile fetched successfully", profile));
    }

    // POST /api/admin/change-password  (Header: Authorization: Bearer <token>)
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponseDto<Object>> changePassword(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ChangePasswordRequestDto requestDto) {
        adminService.changePassword(authHeader, requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseDto.success("Password changed successfully", null));
    }

    // GET /api/admin/validate-token  (Header: Authorization: Bearer <token>)
    @GetMapping("/validate-token")
    public ResponseEntity<ApiResponseDto<Object>> validateToken(
            @RequestHeader("Authorization") String authHeader) {
        // Reuses getProfile logic to confirm token maps to a real admin
        AdminProfileResponseDto profile = adminService.getProfile(authHeader);
        return ResponseEntity.ok(ApiResponseDto.success("Token is valid", profile));
    }
}

