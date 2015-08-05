package com.testtasks.blackJack.server.service.userStandService;

import com.testtasks.blackJack.server.common.GameState;
import com.testtasks.blackJack.server.common.dao.DaoGameInfoI;
import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.component.GameInfoBuilder;
import com.testtasks.blackJack.server.dao.GameDAOI;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import com.testtasks.blackJack.server.exception.NotValidGameStateException;
import com.testtasks.blackJack.server.exception.ServiceException;
import com.testtasks.blackJack.server.validator.HeaderParamsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStandService implements UserStandServiceI{

    @Autowired
    private GameDAOI gameDAO;

    @Autowired
    private GameInfoBuilder gameInfoBuilder;

    @Autowired
    private HeaderParamsValidator headerParamsValidator;

    private static final GameState STAND_GAME_STATE = GameState.S;

    @Override
    public GameInfoI userStand(Long accountId, Long gameId) throws ServiceException {
        headerParamsValidator.validate(accountId, gameId);
        DaoGameInfoI daoGameInfo = gameDAO.getGameInfo(gameId, accountId);
        HandInfoI handInfo = gameDAO.getHandInfo(gameId);
        validateGameState(daoGameInfo.getState());
        gameDAO.updateGameState(gameId, STAND_GAME_STATE);
        return gameInfoBuilder.build(daoGameInfo, handInfo);
    }

    private void validateGameState(GameState state) throws NotValidGameStateException {
        if (GameState.D != state){
            throw new NotValidGameStateException();
        }
    }

    void setGameDAO(GameDAOI gameDAO) {
        this.gameDAO = gameDAO;
    }

    void setGameInfoBuilder(GameInfoBuilder gameInfoBuilder) {
        this.gameInfoBuilder = gameInfoBuilder;
    }

    void setHeaderParamsValidator(HeaderParamsValidator headerParamsValidator) {
        this.headerParamsValidator = headerParamsValidator;
    }
}
