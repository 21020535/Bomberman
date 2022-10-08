//package main;
//
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
//import java.net.URL;
//
//public class Sound {
//
//    Clip clip;
//    URL soundURL[] = new URL[30]; // soundURL luu tru duong dan tep
//
//    public Sound() {
//        soundURL[0] = getClass().getResource("/res/Sound/Hitman.wav");
//        soundURL[1] = getClass().getResource("/res/Sound/BOM_SET.wav");
//        soundURL[2] = getClass().getResource("/res/Sound/BOM_11_M.wav");
//        soundURL[3] = getClass().getResource("/res/Sound/menu.wav");
////        soundURL[4] = getClass().getResource("/res/Sound/soundtrack.wav");
////        soundURL[5] = getClass().getResource("/res/Sound/soundtrack.wav");
////        soundURL[6] = getClass().getResource("/res/Sound/soundtrack.wav");
////        soundURL[7] = getClass().getResource("/res/Sound/soundtrack.wav");
//    }
//
//    public void setFile(int num) {
//        try {
//            AudioInputStream input = AudioSystem.getAudioInputStream(soundURL[num]);
//            clip = AudioSystem.getClip();
//            clip.open(input);
//            // dinh dang de mo tap am thanh trong java
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // khi muon phat tep am thanh chung ta goi phuong thuc hien thi
//    public void play() {
//        clip.start();
//    }
//
//    public void loop() {
//        clip.loop(Clip.LOOP_CONTINUOUSLY);
//    }
//
//    public void stop() {
//        clip.stop();
//    }
//
//}
