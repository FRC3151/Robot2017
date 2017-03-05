package org.usfirst.frc.team3151.subsystem;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoCamera;
import edu.wpi.first.wpilibj.CameraServer;

public final class CameraStreamer {

    public static final int FRAME_WIDTH = 320;
    public static final int FRAME_HEIGHT = 240;

    private final VideoCamera gearCamera;
    private final VideoCamera spokeCamera;
    private final VideoCamera ropeCamera;

    public CameraStreamer() {
        CameraServer server = CameraServer.getInstance();

        // gear camera is flipped + published in our auto pipeline. we don't publish
        // this stream to save on bandwidth
        gearCamera = new UsbCamera("Gear Camera Raw", "/dev/v4l/by-path/XXX");
        spokeCamera = server.startAutomaticCapture("Spoke Camera", "/dev/v4l/by-path/XXX");
        ropeCamera = server.startAutomaticCapture("Rope Camera", "/dev/v4l/by-path/XXX");

        configCamera(gearCamera, 30);
        configCamera(spokeCamera, 5);
        configCamera(ropeCamera, 5);
    }

    private void configCamera(VideoCamera camera, int fps) {
        camera.setResolution(FRAME_WIDTH, FRAME_HEIGHT);
        camera.setFPS(fps);
        camera.setBrightness(35);
        camera.setExposureManual(35);
        camera.setWhiteBalanceManual(4_500);
    }

    public VideoCamera getGearCamera() {
        return gearCamera;
    }

    public VideoCamera getSpokeCamera() {
        return spokeCamera;
    }

    public VideoCamera getRopeCamera() {
        return ropeCamera;
    }

}