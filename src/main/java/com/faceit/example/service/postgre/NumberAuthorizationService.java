package com.faceit.example.service.postgre;

import com.faceit.example.tables.records.NumberAuthorizationsRecord;

public interface NumberAuthorizationService {

    NumberAuthorizationsRecord getById(long id);

    NumberAuthorizationsRecord save(NumberAuthorizationsRecord numberAuthorization);

    NumberAuthorizationsRecord updateById(NumberAuthorizationsRecord numberAuthorization);
}
