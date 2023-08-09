package dev.odane.capstoneproject.mapper;

import dev.odane.capstoneproject.DTOs.BookDTO;
import dev.odane.capstoneproject.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDTO bookToBookDTO(Book book);

    Book bookDTOToBook(BookDTO book);
}
