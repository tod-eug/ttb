package bot.commands;

import bot.Constants;
import bot.TrainingStatBot;
import bot.enums.ExercisesKeyboardOption;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

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

        String text = Constants.EXERCISES_COMMAND + exercisesList;

        MessageProcessor mp = new MessageProcessor();
        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());
        sm.setText(text);
        sm.setReplyMarkup(KeyboardFactory.exercisesList(ExercisesKeyboardOption.EDIT_EXERCISE, message.getFrom()));
        mp.sendMsg(absSender, sm);
    }
}
