package org.usfirst.frc.team3151.auto.vision;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3151.subsystem.CameraStreamer;

import java.util.List;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionRunner;

public final class GearVisionPipelineListener implements VisionRunner.Listener<GearVisionPipeline> {

    private static final Scalar MARKER_COLOR = new Scalar(255, 255, 0);

    private final CvSource gearContoursOut;
    private int targetCenter = -1;

    GearVisionPipelineListener() {
        CameraServer server = CameraServer.getInstance();
        this.gearContoursOut = server.putVideo("Gear Contours", CameraStreamer.FRAME_WIDTH, CameraStreamer.FRAME_HEIGHT);
    }

    @Override
    public void copyPipelineOutputs(GearVisionPipeline pipeline) {
        List<MatOfPoint> contours = pipeline.filterContoursOutput();
        Mat overlayOn = pipeline.hslThresholdOutput();

        if (contours.size() == 2) {
            Rect boxA = Imgproc.boundingRect(contours.get(0));
            Rect boxB = Imgproc.boundingRect(contours.get(1));
            int boxACenter = boxA.x + (boxA.width / 2);
            int boxBCenter = boxB.x + (boxB.width / 2);

            targetCenter = (boxACenter + boxBCenter) / 2;

            // overlay what we detect as the center of the target
            Imgproc.line(
                overlayOn,
                new Point(targetCenter, 0),
                new Point(targetCenter, CameraStreamer.FRAME_HEIGHT),
                MARKER_COLOR,
                2
            );
        } else {
            targetCenter = -1;
        }

        drawContourOverlay(pipeline.hslThresholdOutput(), contours);
        gearContoursOut.putFrame(overlayOn);
    }

    public int getTargetCenter() {
        return targetCenter;
    }

    private void drawContourOverlay(Mat overlayOn, List<MatOfPoint> contours) {
        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(overlayOn, contours, i, MARKER_COLOR, 1);
        }
    }

}