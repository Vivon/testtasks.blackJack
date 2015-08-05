package com.testtasks.blackJack.server.service.accountService;

import com.testtasks.blackJack.server.common.RequestAlias;
import com.testtasks.blackJack.server.dao.DaoException;
import com.testtasks.blackJack.server.dao.GameDAO;
import com.testtasks.blackJack.server.dao.GameDAOI;
import com.testtasks.blackJack.server.domain.account.AccountI;
import com.testtasks.blackJack.server.domain.replenish.ReplenishInfoI;
import com.testtasks.blackJack.server.exception.ServiceException;
import com.testtasks.blackJack.server.validator.LongParamValidator;
import com.testtasks.blackJack.server.validator.SumRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements AccountServiceI {

    @Autowired
    private GameDAOI serviceDAO;

    @Autowired
    private SumRequestValidator sumRequestValidator;

    @Autowired
    private LongParamValidator longParamValidator;

    @Override
    public AccountI createAccount() throws DaoException {
        return serviceDAO.createAccount();
    }

    @Override
    public AccountI getAccount(Long accountId) throws ServiceException {
        longParamValidator.validate(RequestAlias.ACCOUNT_ID_ALIAS, accountId);
        return serviceDAO.getAccount(accountId);
    };

    @Override
    public AccountI replenishPurse(Long accountId, ReplenishInfoI replenishInfo) throws ServiceException {
        sumRequestValidator.validate(accountId, replenishInfo.getSumToReplenish());
        return serviceDAO.replenishPurse(accountId, replenishInfo.getSumToReplenish());
    }

    void setServiceDAO(GameDAOI serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    void setSumRequestValidator(SumRequestValidator sumRequestValidator) {
        this.sumRequestValidator = sumRequestValidator;
    }

    void setLongParamValidator(LongParamValidator longParamValidator) {
        this.longParamValidator = longParamValidator;
    }
}
