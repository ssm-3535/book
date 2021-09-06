package com.ssm.book.api.v1.mapper;

import com.ssm.book.api.v1.dto.PublisherDTO;
import com.ssm.book.domain.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherMapper {
    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    PublisherDTO publisherToPublisherDTO(Publisher publisher);

    Publisher publisherDTOToPublisher(PublisherDTO publisherDTO);
}
