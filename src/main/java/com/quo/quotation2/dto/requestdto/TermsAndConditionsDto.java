package com.quo.quotation2.dto.requestdto;

import java.util.List;

public class TermsAndConditionsDto {
    private String templateVersion;
    private List<String> categoriesApplied;

    public TermsAndConditionsDto() {}

    public TermsAndConditionsDto(String templateVersion, List<String> categoriesApplied) {
        this.templateVersion = templateVersion;
        this.categoriesApplied = categoriesApplied;
    }

    // Getters and Setters
    public String getTemplateVersion() { return templateVersion; }
    public void setTemplateVersion(String templateVersion) { this.templateVersion = templateVersion; }

    public List<String> getCategoriesApplied() { return categoriesApplied; }
    public void setCategoriesApplied(List<String> categoriesApplied) { this.categoriesApplied = categoriesApplied; }
}