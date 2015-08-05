package com.testtasks.blackJack.server.web;

import com.testtasks.blackJack.server.common.Consts;
import com.testtasks.blackJack.server.domain.deal.DealInfo;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import com.testtasks.blackJack.server.exception.ServiceException;
import com.testtasks.blackJack.server.service.finishGameService.FinishGameServiceI;
import com.testtasks.blackJack.server.service.getCardService.GetCardServiceI;
import com.testtasks.blackJack.server.service.dealService.DealServiceI;
import com.testtasks.blackJack.server.service.userStandService.UserStandServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private FinishGameServiceI finishGameService;

    @Autowired
    private DealServiceI dealService;

    @Autowired
    private GetCardServiceI getCardService;

    @Autowired
    private UserStandServiceI userStandService;

    @RequestMapping(value="deal", method = RequestMethod.POST,
            produces = Consts.CONTENT, consumes = Consts.CONTENT)
    public GameInfoI startDeal(@RequestHeader Long accountId, @RequestBody DealInfo dealInfo) throws ServiceException {
        return dealService.startDeal(accountId, dealInfo);
    };

    @RequestMapping(value="hit", method = RequestMethod.POST,
            produces = Consts.CONTENT, consumes = Consts.CONTENT)
    public GameInfoI getCard(@RequestHeader Long accountId, @RequestHeader Long gameId) throws ServiceException {
        return getCardService.getCard(accountId, gameId);
    };

    @RequestMapping(value="stand", method = RequestMethod.POST,
            produces = Consts.CONTENT, consumes = Consts.CONTENT)
    public GameInfoI stopUserHits(@RequestHeader Long accountId, @RequestHeader Long gameId) throws ServiceException {
        return userStandService.userStand(accountId, gameId);
    };

    @RequestMapping(value="finishGame", method = RequestMethod.POST,
            produces = Consts.CONTENT, consumes = Consts.CONTENT)
    public GameInfoI finishGame(@RequestHeader Long accountId, @RequestHeader Long gameId) throws ServiceException {
        return finishGameService.finishGame(accountId, gameId);
    };
}
