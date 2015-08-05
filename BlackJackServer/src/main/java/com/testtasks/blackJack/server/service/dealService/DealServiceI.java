package com.testtasks.blackJack.server.service.dealService;

import com.testtasks.blackJack.server.domain.deal.DealInfoI;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfo;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import com.testtasks.blackJack.server.exception.ServiceException;

public interface DealServiceI {

    /**
     * Начать игру
     * @param accountId ид счета
     * @param dealInfo сумма ставки
     * @throws ServiceException
     */
    GameInfoI startDeal(Long accountId, DealInfoI dealInfo) throws ServiceException;
}
