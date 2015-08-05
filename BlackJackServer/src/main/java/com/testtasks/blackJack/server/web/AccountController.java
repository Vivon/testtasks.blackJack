package com.testtasks.blackJack.server.web;

import com.testtasks.blackJack.server.common.Consts;
import com.testtasks.blackJack.server.domain.account.AccountI;
import com.testtasks.blackJack.server.domain.replenish.ReplenishInfo;
import com.testtasks.blackJack.server.exception.ServiceException;
import com.testtasks.blackJack.server.service.accountService.AccountService;
import com.testtasks.blackJack.server.service.accountService.AccountServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountServiceI service;

    @RequestMapping(value="/createAccount", method = RequestMethod.PUT,
            produces = Consts.CONTENT, consumes = Consts.CONTENT)
    public AccountI createAccount() throws ServiceException {
        return service.createAccount();
    };

    @RequestMapping(value="/getAccount", method = RequestMethod.GET,
            produces = Consts.CONTENT, consumes = Consts.CONTENT)
    public AccountI getAccount(@RequestHeader Long accountId) throws ServiceException {
        return service.getAccount(accountId);
    };

    @RequestMapping(value="/replenishPurse", method = RequestMethod.POST,
            produces = Consts.CONTENT, consumes = Consts.CONTENT)
    public AccountI replenishPurse(@RequestHeader Long accountId,
                                   @RequestBody ReplenishInfo replenishInfo) throws ServiceException {

        return service.replenishPurse(accountId, replenishInfo);
    };

}
