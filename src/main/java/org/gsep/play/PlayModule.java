package org.gsep.play;

import org.gsep.mediator.SceneModule;
import org.gsep.select.MusicItem;

/**
 * PlayModule. Wrapper class for {@link Play}
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public class PlayModule extends SceneModule {

    private static volatile PlayModule instance;

    public static PlayModule getInstance(){
        if(instance == null){
            synchronized (PlayModule.class){
                if(instance == null){
                    instance = new PlayModule();
                }
            }
        }
        return instance;
    }

    public void init(){
        MusicItem item = getMediator().getIntendedItem();
        Play play = new Play(item, instance);
        setScene(play.getScene());
        setTitle("Play Mode");
        play.play();
    }

}
