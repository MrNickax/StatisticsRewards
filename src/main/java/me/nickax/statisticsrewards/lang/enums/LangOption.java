package me.nickax.statisticsrewards.lang.enums;

public enum LangOption {

    // General:
    RELOAD(MessageType.GENERAL),
    UNKNOWN_COMMAND(MessageType.GENERAL),
    NO_PERMISSION(MessageType.GENERAL),
    USAGE(MessageType.GENERAL),
    NOT_VALID_PLAYER(MessageType.GENERAL),
    NOT_VALID_STATISTIC(MessageType.GENERAL),
    NOT_VALID_MATERIAL(MessageType.GENERAL),
    NOT_VALID_ENTITY(MessageType.GENERAL),
    NOT_VALID_NUMBER(MessageType.GENERAL),
    STATISTIC_ADD(MessageType.GENERAL),
    STATISTIC_ADD_UNTYPED(MessageType.GENERAL),
    STATISTIC_SET(MessageType.GENERAL),
    STATISTIC_SET_UNTYPED(MessageType.GENERAL),
    STATISTIC_GET(MessageType.GENERAL),
    STATISTIC_GET_UNTYPED(MessageType.GENERAL),
    REWARD_NOT_CONFIGURED(MessageType.GENERAL),
    REWARD_NOT_FOUND(MessageType.GENERAL),
    REWARD_ALREADY_COLLECTED(MessageType.GENERAL),
    REWARD_NOT_COLLECTED(MessageType.GENERAL),
    REWARD_SET_COLLECTED(MessageType.GENERAL),
    REWARD_SET_UNCOLLECTED(MessageType.GENERAL);

    private final MessageType messageType;

    LangOption(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
