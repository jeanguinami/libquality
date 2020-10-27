package com.jeanfrias.libquality.bean.ejb;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GITHUB_REPO")
@NamedQuery(name="GitHubRepo.findAll", query="SELECT g FROM GitHubRepo g")
@AllArgsConstructor
@NoArgsConstructor

public class GitHubRepo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "REPOSITORY_ID")
    private Long id;

    @Column(name = "REPOSITORY_NAME")
    private String repoName;

    @Column(name = "REPOSITORY_URL")
    private String repoUrl;

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="ISSUES_QUALITY_ID", referencedColumnName="ISSUES_QUALITY_ID")
    private IssuesQuality quality;

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

    public IssuesQuality getQuality() {
        return quality;
    }

    public void setQuality(IssuesQuality quality) {
        this.quality = quality;
    }
}
