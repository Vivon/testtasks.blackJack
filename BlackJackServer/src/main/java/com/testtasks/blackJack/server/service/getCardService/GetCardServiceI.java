package com.testtasks.blackJack.server.service.getCardService;

import com.testtasks.blackJack.server.domain.gameInfo.GameInfo;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import com.testtasks.blackJack.server.exception.ServiceException;

public interface GetCardServiceI {

    /**
     * Получить новую карту
     * @param accountId ид счета
     * @param gameId ид игры
     * @throws ServiceException
     */
    GameInfoI getCard(Long accountId, Long gameId) throws ServiceException;
}
