package com.app.Migration.dto;

public class MigrationRequest {

    private String sourceUrl;
    private String targetUrl;
    private String sourceRepoUrl;
    private String targetRepoUrl;
    private String sourceProject;
    private String targetProject;

    // Getters et Setters

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getSourceRepoUrl() {
        return sourceRepoUrl;
    }

    public void setSourceRepoUrl(String sourceRepoUrl) {
        this.sourceRepoUrl = sourceRepoUrl;
    }

    public String getTargetRepoUrl() {
        return targetRepoUrl;
    }

    public void setTargetRepoUrl(String targetRepoUrl) {
        this.targetRepoUrl = targetRepoUrl;
    }

    public String getSourceProject() {
        return sourceProject;
    }

    public void setSourceProject(String sourceProject) {
        this.sourceProject = sourceProject;
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }
}
