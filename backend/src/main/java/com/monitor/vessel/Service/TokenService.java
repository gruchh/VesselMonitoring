package com.monitor.vessel.Service;

import com.monitor.vessel.Model.Dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final RestClient restClient;
    private String cachedToken;
    private Instant expiresAt;

    @Value("${barentswatch.auth.client-id}")
    private String clientId;

    @Value("${barentswatch.auth.client-secret}")
    private String clientSecret;

    @Value("${barentswatch.auth.scope}")
    private String scope;

    @Value("${barentswatch.auth.grant-type}")
    private String grantType;

    @Value("${barentswatch.auth.token-uri}")
    private String tokenUri;

    public String getAccessToken() {
        if (cachedToken != null && Instant.now().isBefore(expiresAt)) {
            return cachedToken;
        }
        return fetchNewToken();
    }

    private String fetchNewToken() {
        String body = "client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&scope=" + scope +
                "&grant_type=" + grantType;

        TokenResponse response = restClient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .body(TokenResponse.class);

        cachedToken = response.accessToken();
        expiresAt = Instant.now().plusSeconds(response.expiresIn() - 60);
        log.info("Fetched new token, expires at {}", expiresAt);
        return cachedToken;
    }
}