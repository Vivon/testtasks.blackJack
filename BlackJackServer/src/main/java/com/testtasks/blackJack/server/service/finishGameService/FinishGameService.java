package com.testtasks.blackJack.server.service.finishGameService;

import com.testtasks.blackJack.server.common.GameState;
import com.testtasks.blackJack.server.common.WinType;
import com.testtasks.blackJack.server.common.dao.DaoGameInfoI;
import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.component.GameInfoBuilder;
import com.testtasks.blackJack.server.component.GameLogic;
import com.testtasks.blackJack.server.dao.DaoException;
import com.testtasks.blackJack.server.dao.GameDAOI;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import com.testtasks.blackJack.server.domain.player.PlayerHand;
import com.testtasks.blackJack.server.exception.ServiceException;
import com.testtasks.blackJack.server.validator.HeaderParamsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FinishGameService implements FinishGameServiceI{

    @Autowired
    private GameDAOI gameDAO;

    @Autowired
    private GameInfoBuilder gameInfoBuilder;

    @Autowired
    private GameLogic gameLogic;

    @Autowired
    private HeaderParamsValidator headerParamsValidator;

    private static final GameState FINISH_GAME_STATE = GameState.F;
    private static final BigDecimal MULTIPLY_BLACK_JACK = BigDecimal.valueOf(1.5);

    @Override
    public GameInfoI finishGame(Long accountId, Long gameId) throws ServiceException {
        headerParamsValidator.validate(accountId, gameId);
        GameInfoI gameInfo = getGameInfo(accountId, gameId);
        if (FINISH_GAME_STATE != gameInfo.getState()) {
            WinType winner = gameLogic.getWinner(gameInfo.getPlayer(), gameInfo.getDealer());
            gameDAO.updateGameState(gameId, FINISH_GAME_STATE);
            replenishPurseAction(accountId, gameInfo, winner);
        }
        return gameInfo;
    }

    private GameInfoI getGameInfo(Long accountId, Long gameId) throws DaoException {
        DaoGameInfoI daoGameInfo = gameDAO.getGameInfo(gameId, accountId);
        HandInfoI handInfo = gameDAO.getHandInfo(gameId);
        return gameInfoBuilder.build(daoGameInfo, handInfo);
    }

    private void replenishPurseAction(Long accountId, GameInfoI gameInfo, WinType winType) throws DaoException {
        if (WinType.PLAYER_WIN == winType){
            gameDAO.replenishPurse(accountId, getResultBet(gameInfo.getPlayer(), gameInfo.getBet()));
        }
    }

    private BigDecimal getResultBet(PlayerHand player, BigDecimal bet) {
        if (player.getHand().size() == 2){
            return bet.multiply(MULTIPLY_BLACK_JACK);
        }
        return bet;
    }

    void setGameDAO(GameDAOI gameDAO) {
        this.gameDAO = gameDAO;
    }

    void setGameInfoBuilder(GameInfoBuilder gameInfoBuilder) {
        this.gameInfoBuilder = gameInfoBuilder;
    }

    void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    void setHeaderParamsValidator(HeaderParamsValidator headerParamsValidator) {
        this.headerParamsValidator = headerParamsValidator;
    }
}
