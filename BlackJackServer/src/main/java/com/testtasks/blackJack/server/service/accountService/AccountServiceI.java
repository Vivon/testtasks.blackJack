package com.testtasks.blackJack.server.service.accountService;

import com.testtasks.blackJack.server.dao.DaoException;
import com.testtasks.blackJack.server.domain.account.AccountI;
import com.testtasks.blackJack.server.domain.replenish.ReplenishInfoI;
import com.testtasks.blackJack.server.exception.ServiceException;

public interface AccountServiceI {

    /**
     * Создато новый счет
     * @throws DaoException
     */
    AccountI createAccount() throws DaoException;

    /**
     * Получить информацию о существующем счете
     * @param accountId ид счета
     * @throws ServiceException
     */
    AccountI getAccount(Long accountId) throws ServiceException;

    /**
     * Пополнить счет
     * @param accountId ид счета
     * @param replenishInfo сумма пополнения
     * @throws ServiceException
     */
    AccountI replenishPurse(Long accountId, ReplenishInfoI replenishInfo) throws ServiceException;
}
