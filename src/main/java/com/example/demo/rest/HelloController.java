package com.example.demo.rest;

import com.google.cloud.iam.credentials.v1.GenerateAccessTokenResponse;
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.IamCredentialsSettings;
import com.google.protobuf.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @Value("${message}")
  String message;

  @GetMapping(path = "/demo")
  public ResponseEntity<String> displayHelloMessage() {
    return ResponseEntity.ok(message);
  }

  @GetMapping(path = "/testing")
  public ResponseEntity<String> displayToken() throws IOException {

    IamCredentialsClient iamCredentialsClient = IamCredentialsClient
        .create(IamCredentialsSettings.newBuilder().build());

    String serviceAccountEmail = "johan-436@spring-boot-trial.iam.gserviceaccount.com";
    String testingName = "projects/spring-boot-trial/serviceAccounts/" + serviceAccountEmail;

    List<String> scopes = Arrays.asList("https://www.googleapis.com/auth/cloud-platform");
    List<String> delegates = new ArrayList<>();

    GenerateAccessTokenResponse response = iamCredentialsClient.generateAccessToken(testingName, delegates, scopes, Duration.newBuilder().build());

    return ResponseEntity.ok(response.getAccessToken());
  }
}
