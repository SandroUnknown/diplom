package enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Random;


// TODO : Нужны ли тут методы для получения по тайтлу, айди и хешу?


@Getter
public enum Color {
    BERRY_RED("berry_red", "berry-red", 30, "#b8256f"),
    RED("red","red", 31, "#db4035"),
    ORANGE("orange","orange", 32, "#ff9933"),
    YELLOW("yellow", "yellow",33, "#fad000"),
    OLIVE_GREEN("olive_green","olive-green", 34, "#afb83b"),
    LIME_GREEN("lime_green","lime-green",35, "#7ecc49"),
    GREEN("green", "green",36, "#299438"),
    MINT_GREEN("mint_green","mint-green", 37, "#6accbc"),
    TEAL("teal", "teal",38, "#158fad"),
    SKY_BLUE("sky_blue","sky-blue", 39, "#14aaf5"),
    LIGHT_BLUE("light_blue","light-blue", 40, "#96c3eb"),
    BLUE("blue","blue", 41, "#4073ff"),
    GRAPE("grape","grape", 42, "#884dff"),
    VIOLET("violet","violet", 43, "#af38eb"),
    LAVENDER("lavender","lavender", 44, "#eb96eb"),
    MAGENTA("magenta","magenta", 45, "#e05194"),
    SALMON("salmon","salmon", 46, "#ff8d85"),
    CHARCOAL("charcoal","charcoal", 47, "#808080"),
    GREY("grey","grey", 48, "#b8b8b8"),
    TAUPE("taupe","taupe", 49, "#ccac93");

    private final String title;
    private final String cssTitle;
    private final int id;
    private final String hex;

    Color(String title, String cssTitle, int id, String hex) {
        this.title = title;
        this.cssTitle = cssTitle;
        this.id = id;
        this.hex = hex;
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

    public static Color getColorById(int id) {
        for (Color color : Color.values()) {
            if (color.getId() == id) {
                return color;
            }
        }
        return null;
    }

    public static Color getColorByHex(String hex) {
        for (Color color : Color.values()) {
            if (color.getHex().equalsIgnoreCase(hex)) {
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
