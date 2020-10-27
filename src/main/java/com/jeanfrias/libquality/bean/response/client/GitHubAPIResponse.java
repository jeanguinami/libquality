package com.jeanfrias.libquality.bean.response.client;

import java.time.LocalDate;

public class GitHubAPIResponse {

    private String repoUrl;
    private LocalDate creationDate;

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
