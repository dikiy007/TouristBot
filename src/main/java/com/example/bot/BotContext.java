package com.example.bot;

import com.example.model.City;
import com.example.model.User;

public class BotContext {
    private User user;
    private City city;
    private ChatBot bot;

    public BotContext(User user, City city, ChatBot bot) {
        this.user = user;
        this.city = city;
        this.bot = bot;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChatBot getBot() {
        return bot;
    }

    public void setBot(ChatBot bot) {
        this.bot = bot;
    }
}
