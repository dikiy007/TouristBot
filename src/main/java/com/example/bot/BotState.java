package com.example.bot;

public enum BotState {
    START(false) {
        @Override
        public void enter(BotContext context) {
            ChatBot.sendMessage(context, "Hello");
        }

        @Override
        public BotState nextState() {
            return ENTER_CITY;
        }
    },

    ENTER_CITY {
        private BotState next;

        @Override
        public void enter(BotContext context) {
            ChatBot.sendMessage(context, "Enter city please");
        }

        @Override
        public void handleInput(BotContext context) {
            if (context.getCity() == null) {
                ChatBot.sendMessage(context, "City not found");
                next = ENTER_CITY;
            } else {
                next = APPROVED;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    APPROVED(false) {
        @Override
        public void enter(BotContext context) {
            ChatBot.sendMessage(context, context.getCity().getDescription());
        }

        @Override
        public BotState nextState() {
            return ENTER_CITY;
        }
    };

    private static BotState[] states;
    private final boolean inputNeeded;

    BotState(boolean inputNeeded) {
        this.inputNeeded = inputNeeded;
    }

    BotState() {
        this.inputNeeded = true;
    }

    public static BotState getInitialState() {
        return byId(0);
    }

    public static BotState byId(int id) {
        if (states == null) {
            states = BotState.values();
        }

        return states[id];
    }

    public boolean isInputNeeded() {
        return inputNeeded;
    }

    public void handleInput(BotContext context) {

    }

    public abstract void enter(BotContext context);
    public abstract BotState nextState();
}
