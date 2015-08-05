package com.testtasks.blackJack.server.component;

import com.testtasks.blackJack.server.common.dao.DaoGameInfoI;
import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.common.utils.HandUtils;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfo;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import org.springframework.stereotype.Component;

@Component
public class GameInfoBuilder {

    /**
     * Сгенерировать объект для вывода клиенту, из данных пришедших из БД
     * @param daoGameInfo данные по игре
     * @param handInfo рукаи игроков
     */
    public GameInfoI build(DaoGameInfoI daoGameInfo, HandInfoI handInfo){
        GameInfo gameInfo = new GameInfo(daoGameInfo.getGameId(), daoGameInfo.getBet(), daoGameInfo.getState());
        HandUtils.expandedHandsToPlayers(gameInfo.getPlayer(), gameInfo.getDealer(), handInfo);
        gameInfo.recalcScores();
        return gameInfo;
    }
}
