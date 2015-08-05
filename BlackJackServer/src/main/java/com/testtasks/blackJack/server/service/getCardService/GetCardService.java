package com.testtasks.blackJack.server.service.getCardService;

import com.testtasks.blackJack.server.common.GameState;
import com.testtasks.blackJack.server.common.PlayerType;
import com.testtasks.blackJack.server.common.dao.DaoGameInfoI;
import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.common.utils.HandUtils;
import com.testtasks.blackJack.server.component.CardGenerator;
import com.testtasks.blackJack.server.component.GameInfoBuilder;
import com.testtasks.blackJack.server.component.GameLogic;
import com.testtasks.blackJack.server.dao.GameDAOI;
import com.testtasks.blackJack.server.dao.object.CardInfo;
import com.testtasks.blackJack.server.domain.card.Card;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import com.testtasks.blackJack.server.exception.NotValidGameStateException;
import com.testtasks.blackJack.server.exception.ServiceException;
import com.testtasks.blackJack.server.validator.HeaderParamsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCardService implements GetCardServiceI{

    @Autowired
    private GameDAOI gameDAO;

    @Autowired
    private CardGenerator cardGenerator;

    @Autowired
    private GameInfoBuilder gameInfoBuilder;

    @Autowired
    private GameLogic gameLogic;

    @Autowired
    private HeaderParamsValidator headerParamsValidator;

    @Override
    public GameInfoI getCard(Long accountId, Long gameId) throws ServiceException {
        headerParamsValidator.validate(accountId, gameId);
        DaoGameInfoI daoGameInfo = gameDAO.getGameInfo(gameId, accountId);
        HandInfoI handInfo = gameDAO.getHandInfo(gameId);
        addNewCard(daoGameInfo, handInfo);
        return gameInfoBuilder.build(daoGameInfo, handInfo);
    }

    private void addNewCard(DaoGameInfoI daoGameInfo, HandInfoI handInfo) throws NotValidGameStateException, ServiceException {
        CardInfo newCard = generateCard(handInfo, daoGameInfo.getState());
        gameDAO.saveCardsInfo(daoGameInfo.getGameId(), newCard);
        handInfo.addCard(newCard);
    }

    private CardInfo generateCard(HandInfoI handInfo, GameState gameState) throws NotValidGameStateException {
        PlayerType playerType = getPlayerType(handInfo, gameState);
        Card card = cardGenerator.generate(handInfo);
        return new CardInfo(card, playerType);
    }

    private PlayerType getPlayerType(HandInfoI handInfo, GameState gameState) throws NotValidGameStateException {
        if (gameState == GameState.D &&
                gameLogic.isPlayerNotHaveBust(HandUtils.getCardsByPlayerType(handInfo, PlayerType.PLAYER))) {
            return PlayerType.PLAYER;
        } else if (gameState == GameState.S &&
                gameLogic.isDealerNotHaveMaxScore(HandUtils.getCardsByPlayerType(handInfo, PlayerType.DEALER))){
            return PlayerType.DEALER;
        } else {
            throw new NotValidGameStateException();
        }
    }

    void setGameDAO(GameDAOI gameDAO) {
        this.gameDAO = gameDAO;
    }

    void setCardGenerator(CardGenerator cardGenerator) {
        this.cardGenerator = cardGenerator;
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
