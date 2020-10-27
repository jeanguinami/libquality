package com.jeanfrias.libquality.repository;

import com.jeanfrias.libquality.bean.ejb.GitHubRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GitHubRepoRepository extends JpaRepository<GitHubRepo, Long> {

    @Query(value = "SELECT R FROM GitHubRepo R WHERE R.repoName = ?1")
    public Optional<GitHubRepo> findByName(String repoName);
}
