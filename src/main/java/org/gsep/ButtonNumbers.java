package org.gsep;

/**
 * @author  Abigail Lilley
 */
public enum ButtonNumbers {
    MACNUMBERS     (new int[]{ 0, 1, 4, 2, 5, 3, 8, 15, 10, 12, 13, 17}),
    UNIXNUMBERS    (new int[]{ 0, 1, 4, 2, 5, 3, 8, 14, 10, 12, 17, 16}),
    WINDOWSNUMBERS (new int[]{ 0, 1, 4, 2, 5, 3, 8, 16, 10, 12, 13, 14});

    private final int[] numbers;

    ButtonNumbers(int[] numbers) {
        this.numbers = numbers;
    }

    public int[] getNumbers() {
        return numbers;
    }
}
