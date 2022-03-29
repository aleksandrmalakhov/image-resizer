import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final String SRC_FOLDER = "src/main/resources/src";
    private static final String DST_FOLDER = "src/main/resources/dst/";

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        File[] filesSrcDir = new File(SRC_FOLDER).listFiles();
        assert filesSrcDir != null;

        for (File file : filesSrcDir) {
            service.execute(() -> {
                try {
//                    ImageResizer.methodThumbnail(file, Size.LENTA.getWidth(), Size.LENTA.getHeight(), DST_FOLDER);
//                    ImageResizer.methodImgscalr(file, Size.LENTA.getWidth(), Size.LENTA.getHeight(), DST_FOLDER);
//                    ImageResizer.methodGraphics2D(file, Size.STORIES.getWidth(), Size.STORIES.getHeight(), DST_FOLDER);
//                    ImageResizer.methodScaledInstance(file, Size.PORTRAIT.getWidth(), Size.PORTRAIT.getHeight(), DST_FOLDER);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
    }
}