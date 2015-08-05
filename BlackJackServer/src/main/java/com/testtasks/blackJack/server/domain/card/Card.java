package com.testtasks.blackJack.server.domain.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public enum Card {

    DIAMONDS_ACE(0, "diamonds", "Ace", 11, true),
    DIAMONDS_KING(12, "diamonds", "King", 10),
    DIAMONDS_QUEEN(11, "diamonds", "Queen", 10),
    DIAMONDS_JACK(10, "diamonds", "Jack", 10),
    DIAMONDS_TEN(9, "diamonds", "10", 10),
    DIAMONDS_NINE(8, "diamonds", "9", 9),
    DIAMONDS_EIGHT(7, "diamonds", "8", 8),
    DIAMONDS_SEVEN(6, "diamonds", "7", 7),
    DIAMONDS_SIX(5, "diamonds", "6", 6),
    DIAMONDS_FIVE(4, "diamonds", "5", 5),
    DIAMONDS_FOUR(3, "diamonds", "4", 4),
    DIAMONDS_THREE(2, "diamonds", "3", 3),
    DIAMONDS_TWO(1, "diamonds", "2", 2),

    HEARTS_ACE(13, "hearts", "Ace", 11, true),
    HEARTS_KING(25, "hearts", "King", 10),
    HEARTS_QUEEN(24, "hearts", "Queen", 10),
    HEARTS_JACK(23, "hearts", "Jack", 10),
    HEARTS_TEN(22, "hearts", "10", 10),
    HEARTS_NINE(21, "hearts", "9", 9),
    HEARTS_EIGHT(20, "hearts", "8", 8),
    HEARTS_SEVEN(19, "hearts", "7", 7),
    HEARTS_SIX(18, "hearts", "6", 6),
    HEARTS_FIVE(17, "hearts", "5", 5),
    HEARTS_FOUR(16, "hearts", "4", 4),
    HEARTS_THREE(15, "hearts", "3", 3),
    HEARTS_TWO(14, "hearts", "2", 2),

    SPADES_ACE(26, "spades", "Ace", 11, true),
    SPADES_KING(38, "spades", "King", 10),
    SPADES_QUEEN(37, "spades", "Queen", 10),
    SPADES_JACK(36, "spades", "Jack", 10),
    SPADES_TEN(35, "spades", "10", 10),
    SPADES_NINE(34, "spades", "9", 9),
    SPADES_EIGHT(33, "spades", "8", 8),
    SPADES_SEVEN(32, "spades", "7", 7),
    SPADES_SIX(31, "spades", "6", 6),
    SPADES_FIVE(30, "spades", "5", 5),
    SPADES_FOUR(29, "spades", "4", 4),
    SPADES_THREE(28, "spades", "3", 3),
    SPADES_TWO(27, "spades", "2", 2),

    CRUSADE_ACE(39, "crusade", "Ace", 11, true),
    CRUSADE_KING(51, "crusade", "King", 10),
    CRUSADE_QUEEN(50, "crusade", "Queen", 10),
    CRUSADE_JACK(49, "crusade", "Jack", 10),
    CRUSADE_TEN(48, "crusade", "10", 10),
    CRUSADE_NINE(47, "crusade", "9", 9),
    CRUSADE_EIGHT(46, "crusade", "8", 8),
    CRUSADE_SEVEN(45, "crusade", "7", 7),
    CRUSADE_SIX(44, "crusade", "6", 6),
    CRUSADE_FIVE(43, "crusade", "5", 5),
    CRUSADE_FOUR(42, "crusade", "4", 4),
    CRUSADE_THREE(41, "crusade", "3", 3),
    CRUSADE_TWO(40, "crusade", "2", 2),;

    private Integer cardId;
    private String suit;
    private String rank;
    private Integer cardScore;
    private Boolean isCardAce;

    Card(Integer cardId, String suit, String rank, Integer cardScore, Boolean isCardAce) {
        this.cardId = cardId;
        this.suit = suit;
        this.rank = rank;
        this.cardScore = cardScore;
        this.isCardAce = isCardAce;
    }

    Card(Integer cardId, String suit, String rank, Integer cardScore) {
        this.cardId = cardId;
        this.suit = suit;
        this.rank = rank;
        this.cardScore = cardScore;
        this.isCardAce = false;
    }

    public static Card valueOfCardId(Integer cardId){
        for (Card card : Card.values()) {
            if (card.cardId.equals(cardId)){
                return card;
            }
        }
        throw new IllegalArgumentException("Не верный ключ карты!");
    }

    @JsonProperty("suit")
    public String getSuit() {
        return suit;
    }

    @JsonProperty("rank")
    public String getRank() {
        return rank;
    }

    @JsonProperty("cardScore")
    public Integer getCardScore() {
        return cardScore;
    }

    @JsonIgnore
    public Boolean isCardAce() {
        return isCardAce;
    }

    @JsonIgnore
    public Integer getCardId() {
        return cardId;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", suit='" + suit + '\'' +
                ", rank='" + rank + '\'' +
                ", cardScore=" + cardScore +
                ", isCardAce=" + isCardAce +
                '}';
    }
}
