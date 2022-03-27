package io.oopty.downbit.currency.repository;

import io.oopty.downbit.currency.Currency;
import io.oopty.downbit.currency.CurrencyVO;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    CurrencyVO findByCode(String code);
}
