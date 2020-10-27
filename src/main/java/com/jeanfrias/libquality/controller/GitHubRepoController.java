package com.jeanfrias.libquality.controller;

import com.jeanfrias.libquality.bean.request.GitHubRepoRequest;
import com.jeanfrias.libquality.bean.response.GitHubRepoResponse;
import com.jeanfrias.libquality.service.GitHubRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/repositories")
public class GitHubRepoController {

     @Autowired
     private GitHubRepoService repositoryService;

    @GetMapping
    public ArrayList<GitHubRepoResponse> getRepositories(){

        return repositoryService.getRepositories();
    }

    @GetMapping("/name/{n}")
    public GitHubRepoResponse getRepositoryByName(@PathVariable("n") String repoName) {
       return repositoryService.getRepository(repoName);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createRepository(@RequestBody GitHubRepoRequest gitHubRepo) {
        repositoryService.createRepository(gitHubRepo);
    }
}
