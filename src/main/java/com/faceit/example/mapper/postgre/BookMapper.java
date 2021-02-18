package com.faceit.example.mapper.postgre;

import com.faceit.example.dto.request.postgre.BookRequest;
import com.faceit.example.dto.response.postgre.BookResponse;
import com.faceit.example.model.postgre.BookModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookResponse bookModelToBookResponse(BookModel book);

    @Mapping(target = "id", ignore = true)
    BookModel bookRequestToBookModel(BookRequest bookRequest);

    List<BookResponse> booksModelToBooksResponse(List<BookModel> books);

    @Mapping(target = "id", ignore = true)
    BookModel updateBookModelFromBookRequest(BookRequest bookRequest, @MappingTarget BookModel book);
}
