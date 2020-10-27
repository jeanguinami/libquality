package com.jeanfrias.libquality.bean.ejb;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ISSUES_QUALITY")
@NamedQuery(name="IssuesQuality.findAll", query="SELECT i FROM IssuesQuality i")
@AllArgsConstructor
@NoArgsConstructor

public class IssuesQuality implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ISSUES_QUALITY_ID")
    private Long id;

    @Column(name = "QUANTITY")
    private String quantity;

    @Column(name = "REPOSITORY_URL")
    private String repoUrl;

    @Column(name = "ISSUE_AVG_AGE")
    private String avgAge;

    @Column(name = "ISSUE_STD_AGE")
    private String stdAge;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getAvgAge() {
        return avgAge;
    }

    public void setAvgAge(String avgAge) {
        this.avgAge = avgAge;
    }

    public String getStdAge() {
        return stdAge;
    }

    public void setStdAge(String stdAge) {
        this.stdAge = stdAge;
    }
}
