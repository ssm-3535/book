package com.ssm.book.controller;

import com.ssm.book.command.BookCommand;
import com.ssm.book.command.PublisherCommand;
import com.ssm.book.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;

class PublisherControllerTest {

    @Mock
    PublisherService publisherService;

    PublisherController publisherController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        publisherController = new PublisherController(publisherService);
        mockMvc = MockMvcBuilders.standaloneSetup(publisherController).build();
    }

    @Test
    void displayPublisherForm() throws Exception {
        //given
        PublisherCommand publisherCommand = new PublisherCommand();

        //when
        when(publisherService.findPublisherCommandById(anyString())).thenReturn(publisherCommand);

        //then
        mockMvc.perform(get("/book/1/publisherupdate"))
        .andExpect(status().isOk())
        .andExpect(view().name("book/publisher/publisherform"))
        .andExpect(model().attributeExists("publisher"));

        verify(publisherService, times(1)).findPublisherCommandById(anyString());
    }

    @Test
    void savePublisher() throws Exception {
        //given
        BookCommand bookCommand = new BookCommand();
        bookCommand.setId(1L);
        //when
        when(publisherService.updatePublisher(anyString(), any())).thenReturn(bookCommand);

        //then
        mockMvc.perform(post("/publisher/1/save"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/show/1"))
                .andExpect(model().attributeExists("book"));

        verify(publisherService, times(1)).updatePublisher(anyString(), any());
    }

}