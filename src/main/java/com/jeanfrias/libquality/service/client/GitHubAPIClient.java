package com.jeanfrias.libquality.service.client;

import com.jeanfrias.libquality.bean.response.client.GitHubAPIResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.ArrayList;

public class GitHubAPIClient {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36";
    private static final String METHOD = "GET";
    private static final String URI_TEMPLATE = "https://api.github.com/repos/{repoUrl}/issues?&per_page=100&page={page}&state=open";

    private static final String INVALID_PARAMETERS = "URL do repositório não pode ser vazio.";

    @Value("${login.username}")
    private static String username;
    @Value("${login.password}")
    private static String password;

    public static ArrayList<GitHubAPIResponse> getIssues(String repoUrl) {

        ArrayList<GitHubAPIResponse> listGitHubAPI = new ArrayList<>();
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        if (repoUrl.isBlank()) {
            throw new RuntimeException(INVALID_PARAMETERS);
        }
        Boolean isLastPage = false;
        Integer page = 0;
        while (!isLastPage) {
            page++;
            try {
                URL url = new URL(getUri(repoUrl, page));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(METHOD);
                conn.setRequestProperty("user-agent", USER_AGENT);
                conn.setRequestProperty("Authorization", authHeaderValue);

                if (conn.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    StringBuilder stringBuilder = new StringBuilder();
                    String output;

                    while ((output = reader.readLine()) != null) {
                        stringBuilder.append(output);
                    }

                    JSONArray jsonArray = new JSONArray(stringBuilder.toString());

                    if (jsonArray.length() < 100) {
                        isLastPage = true;
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = (JSONObject) jsonArray.get(i);
                        GitHubAPIResponse issue = new GitHubAPIResponse();

                        Instant instant = Instant.parse(json.getString("created_at"));
                        LocalDateTime creationDate = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));

                        issue.setRepoUrl(json.getString("repository_url"));
                        issue.setCreationDate(creationDate.toLocalDate());

                        listGitHubAPI.add(issue);
                    }
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();

            }
        }

        return listGitHubAPI;
    }

    private static String getUri(String repoUrl, Integer page) {

        return URI_TEMPLATE.replace("{repoUrl}", repoUrl)
                .replace("{page}", page.toString());
    }


}
