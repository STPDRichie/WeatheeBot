package com.example.Weather_Bot.appconfig;

import com.example.Weather_Bot.WeatherTelegramBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;

    private DefaultBotOptions.ProxyType proxyType;
    private String proxyHost;
    private int proxyPort;

    @Bean
    public WeatherTelegramBot MyTelegramBot() {
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);

        options.setProxyHost(proxyHost);
        options.setProxyPort(proxyPort);
        options.setProxyType(proxyType);

        WeatherTelegramBot myTelegramBot = new WeatherTelegramBot(options);
        myTelegramBot.setBotUserName(botUserName);
        myTelegramBot.setBotToken(botToken);
        myTelegramBot.setWebHookPath(webHookPath);

        return myTelegramBot;
    }
}
