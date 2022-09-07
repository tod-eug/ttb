package bot.commands;

import bot.TrainingStatBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class ExercisesCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "exercises";
    }

    @Override
    public String getDescription() {
        return "exercises";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        List<String> usersExercises = TrainingStatBot.exercises.get(message.getFrom().getId());

        String exercisesList = "";
        if (!exercisesList.isEmpty()) {
            for (String s : usersExercises) {
                exercisesList = exercisesList + "- " + s + "\n";
            }
        }

        String text = "Упражнения: \n " + exercisesList;

        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());
        sm.setText(text);
        sm.setReplyMarkup(KeyboardFactory.exercisesList("exercisesList", message.getFrom()));
        try {
            absSender.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
