package com.monitor.vessel.Client;

import com.monitor.vessel.Model.Dto.AisPositionDto;
import com.monitor.vessel.Service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class BarentswatchClient {

    private final RestClient restClient;
    private final TokenService tokenService;

    public AisPositionDto[] fetchLatestPositions() {
        String token = tokenService.getAccessToken();

        return restClient.get()
                .uri("https://live.ais.barentswatch.no/v1/latest/ais")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(AisPositionDto[].class);
    }
}