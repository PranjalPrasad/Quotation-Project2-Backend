package com.quo.quotation2.dto.requestdto;

public class StatusUpdateRequestDto {
    private String status;
    private String notes;

    public StatusUpdateRequestDto() {}

    public StatusUpdateRequestDto(String status) {
        this.status = status;
    }

    public StatusUpdateRequestDto(String status, String notes) {
        this.status = status;
        this.notes = notes;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}