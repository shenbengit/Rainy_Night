package com.example.ben.rainy_night.fragment.event;

import me.yokeyword.fragmentation.SupportFragment;

/**
 *
 * @author YoKeyword
 * @date 16/6/30
 */
public class StartBrotherEvent {
    public SupportFragment targetFragment;

    public StartBrotherEvent(SupportFragment targetFragment) {
        this.targetFragment = targetFragment;
    }
}
