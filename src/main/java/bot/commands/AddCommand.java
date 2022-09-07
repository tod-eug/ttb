package bot.commands;

import bot.enums.ExercisesKeyboardOption;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class AddCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "add";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        MessageProcessor mp = new MessageProcessor();
        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());
        sm.setText("Выберите упражнение:");
        sm.setReplyMarkup(KeyboardFactory.exercisesList(ExercisesKeyboardOption.EDIT_VALUES, message.getFrom()));
        mp.sendMsg(absSender, sm);
    }
}
