package pl.sudoku.view.bundles;

import java.util.ListResourceBundle;

public class Authors_pl extends ListResourceBundle {

    private static final Object[][] authors = {
            { "224326", "Bartosz Kępka nr indeksu 224326"},
            { "224248", "Piotr Antczak nr indeksu 224248"},
    };

    @Override
    protected Object[][] getContents() {
        return authors;

    }
}