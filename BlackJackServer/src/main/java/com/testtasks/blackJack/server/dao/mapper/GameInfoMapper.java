package com.testtasks.blackJack.server.dao.mapper;

import com.testtasks.blackJack.server.common.GameState;
import com.testtasks.blackJack.server.dao.object.DaoGameInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameInfoMapper implements RowMapper<DaoGameInfo> {

    static final String GAME_ID_ROW_ALIAS = "game_id";
    static final String BET_ROW_ALIAS = "bet";
    static final String STATE_ROW_ALIAS = "state";

    @Override
    public DaoGameInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            DaoGameInfo daoGameInfo = new DaoGameInfo(rs.getLong(GAME_ID_ROW_ALIAS),
                    rs.getBigDecimal(BET_ROW_ALIAS),
                    GameState.valueOfDBAlias(rs.getString(STATE_ROW_ALIAS).charAt(0)));
            return daoGameInfo;
        } catch(IllegalArgumentException ex){
            throw new SQLException(ex.getMessage(), ex);
        }
    }
}
