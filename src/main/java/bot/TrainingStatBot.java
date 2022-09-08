package bot;

import bot.commands.AddCommand;
import bot.commands.ExercisesCommand;
import bot.commands.StartCommand;
import bot.enums.ExercisesKeyboardOption;
import bot.enums.State;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import util.PropertiesProvider;

import java.util.*;

public class TrainingStatBot extends TelegramLongPollingCommandBot {

    public static Map<Long, List<String>> exercises = new HashMap<>();
    public static Map<Long, State> stateMap = new HashMap<>();

    public TrainingStatBot() {
        super();
        register(new StartCommand());
        register(new ExercisesCommand());
        register(new AddCommand());
    }

    @Override
    public String getBotUsername() {
        return PropertiesProvider.configurationProperties.get("BotName");
    }

    @Override
    public String getBotToken() {
        return PropertiesProvider.configurationProperties.get("BotToken");
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            processCallbackQuery(update);
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            processEmptyMessage(update);
        }
    }

    @Override
    public void processInvalidCommandUpdate(Update update) {
        super.processInvalidCommandUpdate(update);
    }

    @Override
    public boolean filter(Message message) {
        return super.filter(message);
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    private void processCallbackQuery(Update update) {
        String[] parsedCallback = update.getCallbackQuery().getData().split("-");
        if (parsedCallback[0].equals(ExercisesKeyboardOption.EDIT_EXERCISE.toString())) {
            stateMap.put(update.getCallbackQuery().getFrom().getId(), State.NEW_EXERCISE);
            sendMsg(update.getCallbackQuery().getMessage().getChatId(), Constants.ENTER_EXERCISE_NAME);
        } else if (parsedCallback[0].equals(ExercisesKeyboardOption.EDIT_VALUES.toString())) {
            stateMap.put(update.getCallbackQuery().getFrom().getId(), State.APPROACH);
            sendMsg(update.getCallbackQuery().getMessage().getChatId(), Constants.ENTER_APPROACH);
        } else if (update.getCallbackQuery().getData().equals(Constants.ADD_NEW_EXERCISE)) {
            stateMap.put(update.getCallbackQuery().getFrom().getId(), State.NEW_EXERCISE);
            sendMsg(update.getCallbackQuery().getMessage().getChatId(), Constants.ENTER_EXERCISE_NAME);
        }
    }

    private void processEmptyMessage(Update update) {
        User user = update.getMessage().getFrom();
        if (stateMap.get(user.getId()).equals(State.NEW_EXERCISE)) {
            List<String> userExercises = exercises.get(user.getId());
            if (userExercises == null)
                userExercises = new ArrayList<>();
            userExercises.add(update.getMessage().getText());
            exercises.put(user.getId(), userExercises);
            sendMsg(update.getMessage().getChatId(), Constants.SUCCESSFULLY_ADDED);
            stateMap.put(user.getId(), State.FREE);
        } else if (stateMap.get(user.getId()).equals(State.APPROACH)) {
            //save approaches amount
            sendMsg(update.getMessage().getChatId(), Constants.ENTER_VALUE);
            stateMap.put(user.getId(), State.VALUE);
        } else if (stateMap.get(user.getId()).equals(State.VALUE)) {
            //save value amount
            sendMsg(update.getMessage().getChatId(), Constants.SUCCESSFULLY_ADDED);
            stateMap.put(user.getId(), State.FREE);
        } else {
            sendMsg(update.getMessage().getChatId(), Constants.USE_EXERCISE_COMMAND);
        }
    }

    private void sendMsg(long chatId, String text) {
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText(text);
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
