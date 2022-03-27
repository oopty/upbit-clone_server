package io.oopty.downbit.currency;

import io.oopty.downbit.currency.CurrencyVO;
import io.oopty.downbit.currency.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService{

    private CurrencyRepository currencyRepository;

    public CurrencyVO getCurrencyByCode(String code) {
        return currencyRepository.findByCode(code);
    }
}
