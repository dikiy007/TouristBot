package com.example.bot;

import com.example.model.City;
import com.example.model.User;
import com.example.service.CityService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@PropertySource("classpath:telegram.properties")
public class ChatBot extends TelegramLongPollingBot {
    private final UserService userService;
    private final CityService cityService;

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Autowired
    public ChatBot(CityService cityService, UserService userService) {
        this.cityService = cityService;
        this.userService = userService;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        final String text = update.getMessage().getText();
        final long chatId = update.getMessage().getChatId();

        User user = userService.findByChatId(chatId);
        City city = cityService.findByName(text);

        BotContext context;
        BotState state;

        if (user == null) {
            state = BotState.getInitialState();

            user = new User(chatId, state.ordinal());

            context = new BotContext(user, city, this);
            state.enter(context);
        } else {
            context = new BotContext(user, city, this);
            state = BotState.byId(user.getStateId());
        }

        state.handleInput(context);

        do {
            state = state.nextState();
            state.enter(context);
        } while (!state.isInputNeeded());

        user.setStateId(state.ordinal());
        userService.updateUser(user);
    }

    public static void sendMessage(BotContext context, String text) {
        SendMessage message = new SendMessage()
                .setChatId(context.getUser().getChatId())
                .setText(text);

        try {
            context.getBot().execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
