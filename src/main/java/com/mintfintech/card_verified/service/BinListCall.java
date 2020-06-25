package com.mintfintech.card_verified.service;



import com.mintfintech.card_verified.dto.RemoteResponse;
import com.mintfintech.card_verified.exception.BadGatewayException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.Map;

@Service
public class BinListCall {

    @Autowired
    OkHttpClient restClient;

    Logger logger  = LoggerFactory.getLogger(getClass());


    public RemoteResponse getCall(String url, Map headers) throws BadGatewayException{
        RemoteResponse remoteResponse = new RemoteResponse();
        HttpUrl route = HttpUrl.parse(url);
        Request request = new Request.Builder()
                .url(route)
                .headers(Headers.of(headers))
                .get()
                .build();
        Call call = restClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            remoteResponse = new RemoteResponse(response.code(),response.body().string());
        } catch (Exception e) {
            logger.info("Error {}", e.getMessage());
            throw new BadGatewayException(e.getMessage());
        }
        return remoteResponse;
    }
}
