package com.testtasks.blackJack.server.dao;

import com.testtasks.blackJack.server.common.Consts;
import com.testtasks.blackJack.server.common.GameState;
import com.testtasks.blackJack.server.common.dao.DaoGameInfoI;
import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.dao.executor.*;
import com.testtasks.blackJack.server.dao.object.CardInfo;
import com.testtasks.blackJack.server.dao.object.HandInfo;
import com.testtasks.blackJack.server.domain.account.Account;
import com.testtasks.blackJack.server.domain.account.AccountI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class GameDAO extends JdbcDaoSupport implements GameDAOI {

    @Autowired
    GameDAO(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public AccountI getAccount(Long accountId) throws DaoException {
        try {
            return new GetAccountExecutor(accountId).execute(getJdbcTemplate());
        } catch (EmptyResultDataAccessException ex){
            throw new NotFoundAccountException(accountId, ex);
        } catch (Exception ex) {
            throw new SystemDaoException(ex);
        }

    }

    @Override
    public DaoGameInfoI getGameInfo(Long gameId, Long accountId) throws DaoException {
        try {
            return new GetGameInfoExecutor(gameId, accountId).execute(getJdbcTemplate());
        } catch (EmptyResultDataAccessException ex){
            throw new NotFoundGameException(gameId, accountId, ex);
        } catch (Exception ex) {
            throw new SystemDaoException(ex);
        }
    }

    @Override
    public HandInfoI getHandInfo(Long gameId) throws DaoException {
        List<CardInfo> cardsInfo;
        HandInfo handInfo = new HandInfo();
        try {
            cardsInfo = new CardInfoExecutor(gameId).execute(getJdbcTemplate());
        } catch (Exception ex) {
            throw new SystemDaoException(ex);
        }
        if (CollectionUtils.isEmpty(cardsInfo)){
            throw new NotFoundHandException(gameId);
        }
        handInfo.getCards().addAll(cardsInfo);
        return handInfo;
    }

    @Override
    public AccountI replenishPurse(Long accountId, BigDecimal sumToReplenish) throws DaoException {
        try {
            new ReplenishPurseUpdateExecutor(accountId, sumToReplenish).execute(getJdbcTemplate());
        } catch (Exception ex) {
            throw new SystemDaoException(ex);
        }
        return getAccount(accountId);
    }

    @Override
    public void updateGameState(Long gameId, GameState gameState) throws DaoException {
        try {
            new GameStateUpdateExecutor(gameId, gameState).execute(getJdbcTemplate());
        } catch (Exception ex) {
            throw new SystemDaoException(ex);
        }
    }

    @Override
    public AccountI createAccount() throws DaoException {
        try {
            Number accountId = new CreateAccountExecutor().execute(getJdbcTemplate());
            return new Account(accountId.longValue(), Consts.DEFAULT_ACCOUNT_SUM);
        } catch (Exception ex) {
            throw new SystemDaoException(ex);
        }
    }

    @Override
    public DaoGameInfoI createGame(Long accountId, BigDecimal dealSum) throws DaoException {
        Number gameId;
        try {
            gameId = new CreateGameExecutor(accountId, dealSum).execute(getJdbcTemplate());
        } catch (Exception ex) {
            throw new SystemDaoException(ex);
        }
        return getGameInfo(gameId.longValue(), accountId);
    }

    @Override
    public void saveCardsInfo(Long gameId, CardInfo... cards) throws DaoException  {
        try {
            new SaveCardExecutor(gameId, cards).execute(getJdbcTemplate());
        } catch (Exception ex) {
            throw new SystemDaoException(ex);
        }
    }
}
