package com.jeanfrias.libquality.service;

import com.jeanfrias.libquality.bean.ejb.GitHubRepo;
import com.jeanfrias.libquality.bean.ejb.IssuesQuality;
import com.jeanfrias.libquality.bean.request.GitHubRepoRequest;
import com.jeanfrias.libquality.bean.response.GitHubRepoResponse;
import com.jeanfrias.libquality.bean.response.client.GitHubAPIResponse;
import com.jeanfrias.libquality.repository.GitHubRepoRepository;
import com.jeanfrias.libquality.service.client.GitHubAPIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.*;

@Service
public class GitHubRepoService {

    @Autowired
    GitHubRepoRepository gitHubRepoRepository;

    public ArrayList<GitHubRepoResponse> getRepositories() {


        ArrayList<GitHubRepoResponse> gitHubRepoResponseList = new ArrayList<>();
        List<GitHubRepo> repositories = gitHubRepoRepository.findAll();
        repositories.forEach((r) ->
                gitHubRepoResponseList.add(buildGitHubResponse(r)));

        return gitHubRepoResponseList;
    }

    public GitHubRepoResponse getRepository(String name) {
        GitHubRepo repository = gitHubRepoRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Repository isn't on the database."));

        return buildGitHubResponse(repository);
    }

    public void createRepository(GitHubRepoRequest gitHubRepoReq) {

        gitHubRepoRepository.save(buildGitHubRepo(gitHubRepoReq));

    }

    private Double roundAge(Double value, Integer decimals) {
        if (decimals < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(decimals, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private Double calculateAvgOrSdtAge(ArrayList<LocalDate> issuesCreationDate, String type) {

        Double sum = 0.0;
        Long length = (long) issuesCreationDate.size();
        LocalDate today = LocalDate.now();
        ArrayList<Long> diff = new ArrayList<>();

        for (LocalDate creationDate :
                issuesCreationDate) {

            Long diffInDays = Math.abs(ChronoUnit.DAYS.between(creationDate, today));
            diff.add(diffInDays);
            sum += diffInDays;
        }

        Double mean = sum / length;

        if (type.equals("AVG")) {
            return roundAge(mean, 2);
        } else if (type.equals("STD")) {
            Double standardDeviation = 0.0;
            for (Long num : diff) {
                standardDeviation += Math.pow(num - mean, 2);
            }
            return roundAge(Math.sqrt(standardDeviation / length), 2);
        } else {
            throw new RuntimeException("Invalid operation type!");
        }
    }

    private GitHubRepo buildGitHubRepo(GitHubRepoRequest gitHubRepoReq) {
        GitHubRepo repository = new GitHubRepo();
        repository.setRepoUrl(gitHubRepoReq.getRepoUrl());
        repository.setRepoName(gitHubRepoReq.getRepoName());
        repository.setQuality(buildIssuesQuality(gitHubRepoReq.getRepoUrl()));

        return repository;
    }

    private GitHubRepoResponse buildGitHubResponse(GitHubRepo gitHubRepo) {

        GitHubRepoResponse repository = new GitHubRepoResponse();

        repository.setRepoName(gitHubRepo.getRepoName());
        repository.setRepoUrl(gitHubRepo.getRepoUrl());
        repository.setIssuesQuality(buildIssuesQuality(gitHubRepo.getRepoUrl()));

        return repository;
    }

    private IssuesQuality buildIssuesQuality(String repoUrl) {
        ArrayList<LocalDate> issuesCreationDate = new ArrayList<>();

        for (GitHubAPIResponse issue :
                GitHubAPIClient.getIssues(repoUrl)) {
            issuesCreationDate.add(issue.getCreationDate());
        }

        IssuesQuality issuesQuality = new IssuesQuality();
        issuesQuality.setRepoUrl(repoUrl);
        issuesQuality.setAvgAge(String.valueOf(calculateAvgOrSdtAge(issuesCreationDate, "AVG")));
        issuesQuality.setStdAge(String.valueOf(calculateAvgOrSdtAge(issuesCreationDate, "STD")));
        issuesQuality.setQuantity(String.valueOf(issuesCreationDate.stream().count()));

        return issuesQuality;
    }


}
