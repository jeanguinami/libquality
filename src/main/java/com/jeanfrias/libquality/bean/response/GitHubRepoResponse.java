package com.jeanfrias.libquality.bean.response;

import com.jeanfrias.libquality.bean.ejb.IssuesQuality;

public class GitHubRepoResponse {

    private String repoName;

    private String repoUrl;

    private IssuesQuality issuesQuality;

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public IssuesQuality getIssuesQuality() {
        return issuesQuality;
    }

    public void setIssuesQuality(IssuesQuality issuesQuality) {
        this.issuesQuality = issuesQuality;
    }
}
