package pl.sudoku.view;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxDigitsEnumTest {
    @Test
    public void getDigits_ONE_ShouldReturnOne() {
        MaxDigitsEnum maxDigitsEnum = MaxDigitsEnum.ONE;
        assertEquals(1, maxDigitsEnum.getDigits());
    }

    @Test
    public void getDigits_TWO_ShouldReturnOne() {
        MaxDigitsEnum maxDigitsEnum = MaxDigitsEnum.TWO;
        assertEquals(2, maxDigitsEnum.getDigits());
    }
}