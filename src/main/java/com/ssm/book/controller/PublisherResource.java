package com.ssm.book.controller;

import com.ssm.book.api.v1.dto.PublisherDTO;
import com.ssm.book.api.v1.dto.PublisherDTOList;
import com.ssm.book.api.v1.dto.ResponseDTO;
import com.ssm.book.service.PublisherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PublisherResource.PUBLISHER_URL)
public class PublisherResource {
    public static final String PUBLISHER_URL = "/api/v1/publishers";
    private final PublisherService publisherService;

    public PublisherResource(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public PublisherDTOList getPublisherDTOs(){
        return new PublisherDTOList(publisherService.getPublisherDTOList());
    }

    @PostMapping
    public ResponseDTO registerPublisherDTO(@RequestBody PublisherDTO publisherDTO){
        return publisherService.savePublisherDTO(publisherDTO);
    }

    @PutMapping("{id}")
    public PublisherDTO putPublisherDTO(@PathVariable Long id, @RequestBody PublisherDTO publisherDTO){
        return publisherService.putPublisherDTO(id, publisherDTO);
    }

    @PatchMapping("{id}")
    public PublisherDTO patchPublisherDTO(@PathVariable Long id, @RequestBody PublisherDTO publisherDTO){
        return publisherService.patchPublisherDTO(id, publisherDTO);
    }

    @DeleteMapping("{id}")
    public void deletePublisherDTO(@PathVariable Long id){
        publisherService.deletePublisherDTOById(id);
    }
}
