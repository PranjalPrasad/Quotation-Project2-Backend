package com.quo.quotation2.dto.requestdto;


public class StatusUpdateRequestDto {
    private String status;

    public StatusUpdateRequestDto() {}

    public StatusUpdateRequestDto(String status) {
        this.status = status;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
