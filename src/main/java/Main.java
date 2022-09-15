import bot.TrainingStatBot;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import sheets.SheetsProvider;
import util.PropertiesProvider;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList("Expenses January"),
                        Arrays.asList("books", "30"),
                        Arrays.asList("pens", "10"),
                        Arrays.asList("Expenses February"),
                        Arrays.asList("clothes", "20"),
                        Arrays.asList("shoes", "5")));
        SheetsProvider sheetsProvider = new SheetsProvider();
        sheetsProvider.writeSheet(body, "A1", "RAW");

        PropertiesProvider.setup();

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TrainingStatBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
