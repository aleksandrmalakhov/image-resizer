import com.idrsolutions.image.scale.QualityThumbnail;
import lombok.NonNull;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizer {

    public static void methodThumbnail(File file, int width, int height, String dst) {
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

        writer(dst, file, newImage);
    }

    public static void methodGraphics2D(File file, int width, int height, String dst) throws IOException {
        BufferedImage image = ImageIO.read(file);

        float scaleX = (float) width / image.getWidth();
        float scaleY = (float) height / image.getHeight();
        float scale = Math.min(scaleX, scaleY);
        int w = Math.round(image.getWidth() * scale);
        int h = Math.round(image.getHeight() * scale);

        int type = image.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB :
                BufferedImage.TYPE_INT_ARGB;

        boolean scaleDown = scale < 1;

        if (scaleDown) {
            int currentW = image.getWidth();
            int currentH = image.getHeight();
            BufferedImage resized = image;
            while (currentW > w || currentH > h) {
                currentW = Math.max(w, currentW / 2);
                currentH = Math.max(h, currentH / 2);

                BufferedImage temp = new BufferedImage(currentW, currentH, type);
                Graphics2D g2 = temp.createGraphics();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2.drawImage(resized, 0, 0, currentW, currentH, null);

                } finally {
                    g2.dispose();
                }
                resized = temp;
            }

            writer(dst, file, resized);
        } else {
            Object hint = scale > 2 ? RenderingHints.VALUE_INTERPOLATION_BICUBIC :
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR;

            BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resized.createGraphics();
            try {
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
                g2.drawImage(image, 0, 0, w, h, null);
            } finally {
                g2.dispose();
            }

            writer(dst, file, resized);
        }
    }

    public static void methodScaledInstance(File file, int width, int height, String dst) throws IOException {
        BufferedImage image = ImageIO.read(file);

        Image resultingImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        newImage.getGraphics().drawImage(resultingImage, 0, 0, null);

        writer(dst, file, newImage);
    }

    private static void writer(String dst, @NonNull File file, BufferedImage newImage) throws IOException {
        File newFile = new File(dst + file.getName());
        ImageIO.write(newImage, "jpg", newFile);
    }
}