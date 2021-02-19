package com.faceit.example.mapper.postgre;

import com.faceit.example.dto.request.postgre.BookRequest;
import com.faceit.example.dto.response.postgre.BookResponse;
import com.faceit.example.tables.records.BooksRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookResponse bookRecordToBookResponse(BooksRecord book);

    @Mapping(target = "id", ignore = true)
    BooksRecord bookRequestToBookRecord(BookRequest bookRequest);

    List<BookResponse> booksRecordToBooksResponse(List<BooksRecord> books);

    @Mapping(target = "id", ignore = true)
    BooksRecord updateBookRecordFromBookRequest(BookRequest bookRequest, @MappingTarget BooksRecord book);
}
