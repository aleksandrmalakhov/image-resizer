import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import java.io.File;

public class ImageResizer {

    public static void startResizer(File file, int width, int height, String dst) {
        try {
            Thumbnails.of(file)
                    .size(width, height)
                    .outputFormat("jpg")
                    .outputQuality(1.0)
                    .toFiles(new File(dst), Rename.NO_CHANGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}