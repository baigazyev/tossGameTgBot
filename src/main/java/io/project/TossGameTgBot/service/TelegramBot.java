package io.project.TossGameTgBot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import io.project.TossGameTgBot.config.*;

import io.project.TossGameTgBot.config.BotConfig;

@Component

public class TelegramBot extends TelegramLongPollingBot {
	
	final BotConfig config;
	
	
	
	
	
	public TelegramBot(BotConfig config) {
		this.config = config;
	}
	
	

	

	@Override
	public String getBotUsername() {
		
		return config.getBotName();
	}

	@Override
	public String getBotToken() {
		
		return config.getBotToken();
	}
	
	
	
	@Override
	public void onUpdateReceived(Update update) {
		
		
		if(update.hasMessage() && update.getMessage().hasText()) {
			String messageText = update.getMessage().getText();
			long chatId = update.getMessage().getChatId();
			
			switch (messageText) {
			
			case "/start":
				
					startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
					
					break;
					
			case "/toss":
					tossCoin(chatId);
					break;
				
				
				
			default:
				sendMessage(chatId, "Sorry, this command not recognized");
				
				
			}
		}
		
		
	}
	
	
	private void startCommandReceived(long chatId, String name){
		
		String answer = "Hi, " + name + ". I am TossGameBot " + "\n" + "I will help you toss a coin so you can make a decision.";
		startButton(chatId, answer);
		
	}
	
	private void sendMessage(long chatId, String textToSend) {
		SendMessage message = new SendMessage();
		message.setChatId(String.valueOf(chatId));
		message.setText(textToSend);
		try {
			execute(message);
		}
		catch(TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void tossCoin(long chatId) {
		
		
		CoinSide side = CoinSide.randomSide();
		sendMessage(chatId, side.toString());
		
	}
	
	private void startButton(long chatId, String button) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(String.valueOf(chatId));
		sendMessage.setParseMode(ParseMode.MARKDOWN);
		sendMessage.setText(button);
		
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		replyKeyboardMarkup.setResizeKeyboard(true);
		List<KeyboardRow> keyboardRowList = new ArrayList<>();
		KeyboardRow keyboardRow1 = new KeyboardRow();
		KeyboardButton keyboardButton1 = new KeyboardButton();
		keyboardButton1.setText("/toss");
		keyboardRow1.add(keyboardButton1);
		keyboardRowList.add(keyboardRow1);
		replyKeyboardMarkup.setKeyboard(keyboardRowList);
		sendMessage.setReplyMarkup(replyKeyboardMarkup);
		try {
			execute(sendMessage);
		}
		catch(TelegramApiException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
	
	

}
