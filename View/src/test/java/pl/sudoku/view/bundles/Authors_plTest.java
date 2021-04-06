package pl.sudoku.view.bundles;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class Authors_plTest {

    @Test
    void getContents_AuthorOne_ShouldReturnProperAuthorDescription() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                "pl.sudoku.view.bundles.Authors", new Locale("pl"));
        String expectedDescription = "Bartosz Kępka nr indeksu 224326";

        assertEquals(expectedDescription, resourceBundle.getString("224326"));
    }

    @Test
    void getContents_AuthorTwo_ShouldReturnProperAuthorDescription() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                "pl.sudoku.view.bundles.Authors", new Locale("pl"));
        String expectedDescription = "Piotr Antczak nr indeksu 224248";

        assertEquals(expectedDescription, resourceBundle.getString("224248"));
    }

    @Test
    void getContents_WrongKey_ShouldThrowMissingResourceException() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                "pl.sudoku.view.bundles.Authors", new Locale("pl"));

        assertThrows(MissingResourceException.class, () -> {
            resourceBundle.getString("wrong");}
        );
    }
}