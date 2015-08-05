package com.testtasks.blackJack.server.service.userStandService;

import com.testtasks.blackJack.server.domain.gameInfo.GameInfo;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import com.testtasks.blackJack.server.exception.ServiceException;

public interface UserStandServiceI {

    /**
     * Остановить взятие карты пользователя
     * @param accountId ид счета
     * @param gameId ид игры
     * @throws ServiceException
     */
    GameInfoI userStand(Long accountId, Long gameId) throws ServiceException;
}
