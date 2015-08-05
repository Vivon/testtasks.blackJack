package com.testtasks.blackJack.server.service.finishGameService;

import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import com.testtasks.blackJack.server.exception.ServiceException;

public interface FinishGameServiceI {

    /**
     * Закончить игру и получить выигрыш
     * @param accountId ид счета
     * @param gameId ид игры
     * @throws ServiceException
     */
    GameInfoI finishGame(Long accountId, Long gameId) throws ServiceException;

}

