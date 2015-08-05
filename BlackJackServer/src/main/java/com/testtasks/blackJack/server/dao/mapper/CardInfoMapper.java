package com.testtasks.blackJack.server.dao.mapper;

import com.testtasks.blackJack.server.dao.object.CardInfo;
import com.testtasks.blackJack.server.common.PlayerType;
import com.testtasks.blackJack.server.domain.card.Card;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardInfoMapper implements RowMapper<CardInfo> {

    static final String PLAYER_ROW_ALIAS = "player";
    static final String CARD_ID_ROW_ALIAS = "card_id";

    @Override
    public CardInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            PlayerType playerType = PlayerType.valueOf(rs.getString(PLAYER_ROW_ALIAS));
            Integer cardId = rs.getInt(CARD_ID_ROW_ALIAS);
            return new CardInfo(Card.valueOfCardId(cardId), playerType);
        } catch (IllegalArgumentException ex){
            throw new SQLException(ex.getMessage(), ex);
        }
    }

}
