package enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;


// TODO : Нужен ли тут метод для получения по стилю?


@Getter
public enum ViewStyle {
    LIST("list"),
    BOARD("board");

    private final String title;

    ViewStyle(String title) {
        this.title = title;
    }

    public static ViewStyle getViewStyleByTitle(String title) {
        for (ViewStyle viewStyle : ViewStyle.values()) {
            if (viewStyle.getTitle().equalsIgnoreCase(title)) {
                return viewStyle;
            }
        }
        return null;
    }

    @JsonValue
    public String getJsonValue() {
        return title;
    }
}