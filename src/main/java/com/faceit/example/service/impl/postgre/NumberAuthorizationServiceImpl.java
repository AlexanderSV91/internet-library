package com.faceit.example.service.impl.postgre;

import com.faceit.example.service.postgre.NumberAuthorizationService;
import com.faceit.example.tables.records.NumberAuthorizationsRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NumberAuthorizationServiceImpl implements NumberAuthorizationService {

    @Override
    public NumberAuthorizationsRecord getNumberAuthorizationById(long id) {
        return null;
    }

    @Override
    public NumberAuthorizationsRecord updateNumberAuthorizationById(NumberAuthorizationsRecord numberAuthorization) {
        return null;
    }
}
