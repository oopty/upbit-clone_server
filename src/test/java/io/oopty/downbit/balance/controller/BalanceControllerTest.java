package io.oopty.downbit.balance.controller;

import io.oopty.downbit.balance.BalanceService;
import io.oopty.downbit.currency.Currency;
import io.oopty.downbit.currency.CurrencyService;
import io.oopty.downbit.currency.CurrencyVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceControllerTest {
    public static final Long USER_ID = 58939L;
    public static final int CURRENCY_ID = 9538;
    @InjectMocks
    private BalanceController subject;

    @Mock
    private CurrencyService mockCurrencyService;
    @Mock
    private BalanceService mockBalanceService;

    @DisplayName("when user call api to /api/v1/balances/{marketCode}, return balacne for currency as response")
    @Test
    void whenUserCallApiToGetBalanceOfCurrency() {
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
        CurrencyVO currencyVO = CurrencyVO.builder()
                .id(CURRENCY_ID)
                .build();

        HttpSession mockSession = mock(HttpSession.class);
        when(mockHttpServletRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("userId")).thenReturn(USER_ID);
        when(mockCurrencyService.getCurrencyByCode("BTC-KRW")).thenReturn(currencyVO);
        when(mockBalanceService.getBalanceFor(USER_ID, CURRENCY_ID)).thenReturn(CurrencyResponse.builder()
                        .marketCode("BTC-KRW")
                        .balance(3000)
                        .availableBalance(1000)
                        .lockedBalance(2000)
                        .build());

        CurrencyResponse result = subject.getBalance(mockHttpServletRequest, "BTC-KRW");

        assertThat(result.getMarketCode(), is("BTC-KRW"));
        assertThat(result.getBalance(), closeTo(3000, 1e05));
        assertThat(result.getAvailableBalance(), closeTo(3000, 1e05));
        assertThat(result.getLockedBalance(), closeTo(3000, 1e05));
    }
}