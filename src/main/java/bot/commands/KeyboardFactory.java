package bot.commands;

import bot.Constants;
import bot.TrainingStatBot;
import bot.enums.ExercisesKeyboardOption;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardFactory {

    public static ReplyKeyboard exercisesList(ExercisesKeyboardOption exercisesKeyboardOption, User user) {
        List<String> usersExercises = TrainingStatBot.exercises.get(user.getId());

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        if (usersExercises != null) {
            if (!usersExercises.isEmpty()) {
                for (String s : usersExercises) {
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(s);
                    button.setCallbackData("#" + exercisesKeyboardOption + "+" +user.getId() + "+" + s);
                    rowInline.add(button);
                }
            }
        }

        if (exercisesKeyboardOption == ExercisesKeyboardOption.EDIT_EXERCISE) {
            InlineKeyboardButton addNewExercise = new InlineKeyboardButton();
            addNewExercise.setText("+");
            addNewExercise.setCallbackData(Constants.ADD_NEW_EXERCISE);
            rowInline.add(addNewExercise);
        }
        rowsInline.add(rowInline);
        keyboardMarkup.setKeyboard(rowsInline);
        return keyboardMarkup;
    }
}
