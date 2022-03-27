package io.oopty.downbit.balance.controller;

import io.oopty.downbit.balance.BalanceService;
import io.oopty.downbit.balance.controller.CurrencyResponse;
import io.oopty.downbit.currency.CurrencyService;
import io.oopty.downbit.currency.CurrencyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/v1/balances")
@RequiredArgsConstructor
public class BalanceController {
    private final CurrencyService currencyService;
    private final BalanceService balanceService;

    @PostMapping("/{marketCode}")
    public CurrencyResponse getBalance(HttpServletRequest request, @PathVariable String marketCode) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        CurrencyVO currencyVO = currencyService.getCurrencyByCode(marketCode);

        return balanceService.getBalanceFor(userId, currencyVO.getId());
    }
}
