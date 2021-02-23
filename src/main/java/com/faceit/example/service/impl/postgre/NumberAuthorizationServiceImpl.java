package com.faceit.example.service.impl.postgre;

import com.faceit.example.repository.postgre.NumberAuthorizationRepository;
import com.faceit.example.service.postgre.NumberAuthorizationService;
import com.faceit.example.tables.records.NumberAuthorizationsRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NumberAuthorizationServiceImpl implements NumberAuthorizationService {

    private final NumberAuthorizationRepository numberAuthorizationRepository;

    @Override
    public NumberAuthorizationsRecord getById(long id) {
        return numberAuthorizationRepository.getById(id);
    }

    @Override
    public NumberAuthorizationsRecord save(NumberAuthorizationsRecord numberAuthorization) {
        return numberAuthorizationRepository.save(numberAuthorization);
    }

    @Override
    public NumberAuthorizationsRecord updateById(NumberAuthorizationsRecord numberAuthorization) {
        return numberAuthorizationRepository.updateById(numberAuthorization);
    }
}
