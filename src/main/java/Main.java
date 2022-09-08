import bot.TrainingStatBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import sheets.SheetService;
import util.PropertiesProvider;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Main {
    public static void main(String[] args) {

//        PropertiesProvider.setup();
//
//        try {
//            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//            botsApi.registerBot(new TrainingStatBot());
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }

        try {
            SheetService.setup();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SheetService sheetService = new SheetService();

        try {
            sheetService.whenWriteSheet();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
