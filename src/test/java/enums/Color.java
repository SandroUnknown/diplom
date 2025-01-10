package enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Random;


// TODO : Нужны ли тут методы для получения по тайтлу, айди и хешу?


@Getter
public enum Color {
    BERRY_RED("berry_red", "berry-red",  "Berry red"),
    RED("red","red",  "Red"),
    ORANGE("orange","orange",  "Orange"),
    YELLOW("yellow", "yellow", "Yellow"),
    OLIVE_GREEN("olive_green","olive-green",  "Olive green"),
    LIME_GREEN("lime_green","lime-green", "Lime green"),
    GREEN("green", "green", "Green"),
    MINT_GREEN("mint_green","mint-green",  "Mint green"),
    TEAL("teal", "teal", "Teal"),
    SKY_BLUE("sky_blue","sky-blue",  "Sky blue"),
    LIGHT_BLUE("light_blue","light-blue",  "Light blue"),
    BLUE("blue","blue",  "Blue"),
    GRAPE("grape","grape",  "Grape"),
    VIOLET("violet","violet",  "Violet"),
    LAVENDER("lavender","lavender",  "Lavender"),
    MAGENTA("magenta","magenta",  "Magenta"),
    SALMON("salmon","salmon",  "Salmon"),
    CHARCOAL("charcoal","charcoal",  "Charcoal"),
    GREY("grey","grey",  "Grey"),
    TAUPE("taupe","taupe",  "Taupe");

    private final String title;
    private final String cssUiTitle;
    private final String cssAndroidTitle;

    Color(String title, String cssUiTitle, String cssAndroidTitle) {
        this.title = title;
        this.cssUiTitle = cssUiTitle;
        this.cssAndroidTitle = cssAndroidTitle;
    }

    // TODO : работает метод??
    public static Color getRandom() {
        Color[] colors = values();
        int randomIndex = new Random().nextInt(colors.length);
        return colors[randomIndex];
    }

    public static Color getColorByTitle(String title) {
        for (Color color : Color.values()) {
            if (color.getTitle().equalsIgnoreCase(title)) {
                return color;
            }
        }
        return null;
    }

    @JsonValue
    public String getJsonValue() {
        return title;
    }
}
