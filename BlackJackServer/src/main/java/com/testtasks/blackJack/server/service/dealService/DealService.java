package com.testtasks.blackJack.server.service.dealService;

import com.testtasks.blackJack.server.common.dao.DaoGameInfoI;
import com.testtasks.blackJack.server.common.utils.HandUtils;
import com.testtasks.blackJack.server.component.CardGenerator;
import com.testtasks.blackJack.server.component.GameInfoBuilder;
import com.testtasks.blackJack.server.dao.DaoException;
import com.testtasks.blackJack.server.dao.GameDAOI;
import com.testtasks.blackJack.server.domain.account.AccountI;
import com.testtasks.blackJack.server.dao.object.HandInfo;
import com.testtasks.blackJack.server.domain.deal.DealInfoI;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import com.testtasks.blackJack.server.exception.NotEnoughMoneyException;
import com.testtasks.blackJack.server.exception.ServiceException;
import com.testtasks.blackJack.server.validator.SumRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DealService implements DealServiceI{

    @Autowired
    private GameDAOI gameDAO;

    @Autowired
    private CardGenerator cardGenerator;

    @Autowired
    private GameInfoBuilder gameInfoBuilder;

    @Autowired
    private SumRequestValidator sumRequestValidator;

    @Override
    public GameInfoI startDeal(Long accountId, DealInfoI dealInfo) throws ServiceException {
        sumRequestValidator.validate(accountId, dealInfo.getDealSum());
        AccountI account = gameDAO.getAccount(accountId);
        if (dealInfo.getDealSum().compareTo(account.getAccountSum()) <= 0) {
            GameInfoI gameInfo = createGame(account, dealInfo);
            gameDAO.replenishPurse(accountId, gameInfo.getBet().negate());
            return gameInfo;
        }
        throw new NotEnoughMoneyException();
    }

    private GameInfoI createGame(AccountI account, DealInfoI dealInfo) throws DaoException {
        DaoGameInfoI game = gameDAO.createGame(account.getAccountId(), dealInfo.getDealSum());
        HandInfo handInfo = HandUtils.generateBeginHandInfo(cardGenerator);
        gameDAO.saveCardsInfo(game.getGameId(), handInfo.getCardsArray());
        return gameInfoBuilder.build(game, handInfo);
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

    void setSumRequestValidator(SumRequestValidator sumRequestValidator) {
        this.sumRequestValidator = sumRequestValidator;
    }
}
