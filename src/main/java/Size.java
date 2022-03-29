import lombok.Getter;

@Getter
public enum Size {
    AVATAR(210, 210),
    SQUARE(1080, 1080),
    LANDSCAPE(1080, 680),
    PORTRAIT(1080, 1350),
    LENTA(1080, 1080),
    STORIES(1080, 1920);

    private final int width;
    private final int height;

    Size(int width, int height) {
        this.width = width;
        this.height = height;
    }
}