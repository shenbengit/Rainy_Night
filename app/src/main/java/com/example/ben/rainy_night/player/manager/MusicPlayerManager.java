package com.example.ben.rainy_night.player.manager;

/**
 * @author Ben
 * @date 2018/4/12
 */

public class MusicPlayerManager {


    private MusicPlayerManager() {

    }

    public static MusicPlayerManager getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final MusicPlayerManager INSTANCE = new MusicPlayerManager();
    }
}
