package pl.sudoku.view.bundles;

import java.util.ListResourceBundle;

public class Authors extends ListResourceBundle {

    private static final Object[][] authors = {
            { "224326", "Bartosz Kepka student ID 224326"},
            { "224248", "Piotr Antczak student ID 224248"},
    };

    @Override
    protected Object[][] getContents() {
        return authors;
    }
}
