import com.idrsolutions.image.scale.QualityThumbnail;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizer {

    public static void methodThumbnailator(File file, int width, int height, String dst) {
        try {
            Thumbnails.of(file)
                    .size(width, height)
                    .outputFormat("jpg")
                    .outputQuality(1)
                    .toFiles(new File(dst), Rename.NO_CHANGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void methodImgscalr(File file, int width, int height, String dst) throws IOException {
        BufferedImage image = ImageIO.read(file);

        if (image == null) {
            return;
        }

        BufferedImage newImage = Scalr.resize(image,
                Scalr.Method.ULTRA_QUALITY,
                Scalr.Mode.AUTOMATIC,
                width,
                height,
                Scalr.OP_ANTIALIAS);

        File newFile = new File(dst + file.getName());
        ImageIO.write(newImage, "jpg", newFile);
    }
}