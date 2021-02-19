package com.faceit.example.service.postgre;

import com.faceit.example.tables.records.NumberAuthorizationsRecord;

public interface NumberAuthorizationService {

    NumberAuthorizationsRecord getNumberAuthorizationById(long id);

    NumberAuthorizationsRecord updateNumberAuthorizationById(NumberAuthorizationsRecord numberAuthorization);
}
