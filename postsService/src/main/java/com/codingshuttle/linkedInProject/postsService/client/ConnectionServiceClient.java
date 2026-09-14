package com.codingshuttle.linkedInProject.postsService.client;

import com.codingshuttle.linkedInProject.postsService.dto.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name="connections-service",path = "/connections")
public interface ConnectionServiceClient {


    @GetMapping("core/{userId}/first-degree")
    List<PersonDto> getFirstDegreeConnections(@PathVariable Long userId);

}
