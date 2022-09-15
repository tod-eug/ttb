package sheets;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import exceptions.WritingSheetException;
import sheets.authorization.GoogleAuthorizeUtil;

import java.io.IOException;

public class SheetsProvider {

    private static String SPREADSHEET_ID = "1ADUPfvZF5-rWuzvFx2fGMz8WOHSIJ7jpNpP0GdhBUlE";

    private Sheets sheet = GoogleAuthorizeUtil.getSheet();

    public UpdateValuesResponse writeSheet(ValueRange value, String range, String valueInputOption) {
        UpdateValuesResponse result = null;
        try {
            return result = sheet.spreadsheets().values()
                    .update(SPREADSHEET_ID, range, value)
                    .setValueInputOption(valueInputOption)
                    .execute();
        } catch (IOException e) {
            throw new WritingSheetException("Error when writing sheet");
        }
    }
}
