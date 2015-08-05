package com.testtasks.blackJack.server.common;

import java.math.BigDecimal;

public class Consts {

    public static final BigDecimal DEFAULT_ACCOUNT_SUM = BigDecimal.ZERO;

    public static final Integer BLACK_JACK_SCORE = 21;
    public static final Integer DEALER_MAX_SCORE = 17;
    public static final Integer DEFAULT_ACE_SCORE = 1;

    public static final String CONTENT = "application/json; charset=utf-8";

    public static final String ACCOUNT_ID_REQUEST_ALIAS = "accountId";
    public static final String GAME_ID_REQUEST_ALIAS = "gameId";
}
