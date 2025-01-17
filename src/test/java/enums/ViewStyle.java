package enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ViewStyle {
    LIST("list", 1),
    BOARD("board", 2);

    private final String title;
    private final int number;

    ViewStyle(String title, int number) {
        this.title = title;
        this.number = number;
    }

    @JsonValue
    public String getJsonValue() {
        return title;
    }
}