/*
  * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
  * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package processing.core;

/**
 * A dictionary of key codes.
 *
 * @author sampw
 */
public enum KeyCode {
    BACKSPACE(8),
    TAB(9),
    ENTER(10),
    SHIFT(16),
    CTRL(17),
    LEFT_ALT(18),
    RIGHT_ALT(19),
    CAPS_LOCK(20),
    ESCAPE(27),
    SPACE(32),
    END(3),
    HOME(2),
    LEFT(37),
    UP(38),
    RIGHT(39),
    DOWN(40),
    INSERT(26),
    PRINT_SCREEN(5),
    PAUSE(148),
    DELETE(147),
    BACKTICK(96),
    ZERO(48),
    ONE(49),
    TWO(50),
    THREE(51),
    FOUR(52),
    FIVE(53),
    SIX(54),
    SEVEN(55),
    EIGHT(56),
    NINE(57),
    DASH(45),
    EQUALS(61),
    Q(81),
    W(87),
    E(69),
    R(82),
    T(84),
    Y(89),
    U(85),
    I(73),
    O(79),
    P(80),
    LEFT_SQUARE_BRACKET(91),
    RIGHT_SQUARE_BRACKET(93),
    BACKSLASH(92),
    A(65),
    S(83),
    D(68),
    F(70),
    G(71),
    H(72),
    J(74),
    K(75),
    L(76),
    SEMICOLON(59),
    Z(90),
    X(88),
    C(67),
    V(86),
    B(66),
    N(78),
    M(77),
    COMMA(44),
    PERIOD(46),
    SLASH(47),
    F1(97),
    F2(98),
    F3(99),
    F4(100),
    F5(101),
    F6(102),
    F7(103),
    F8(104),
    F9(105),
    F10(106),
    F11(107),
    F12(108),
    NUM_LOCK(148),
    NUMPAD_0(128),
    NUMPAD_1(129),
    NUMPAD_2(130),
    NUMPAD_3(131),
    NUMPAD_4(132),
    NUMPAD_5(133),
    NUMPAD_6(134),
    NUMPAD_7(135),
    NUMPAD_8(136),
    NUMPAD_9(137),
    NUMPAD_DECIMAL(138),
    NUMPAD_ADD(139),
    NUMPAD_SUBTRACT(140),
    NUMPAD_MULTIPLY(141),
    NUMPAD_DIVIDE(142),
    UNKNOWN(0);
    private final int code;

    KeyCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return name();
    }

    public int getValue() {
        return code;
    }

    /**
     * Returns the enum key-getValue value for the corresponding number getValue
     * @param keyCode the number getValue
     * @return the enum key-getValue
     */
    public static KeyCode valueOf(int keyCode) {
        for (KeyCode val : KeyCode.values()) {
            if (val.getValue() == keyCode) {
                return val;
            }
        }
        return UNKNOWN;
    }
}