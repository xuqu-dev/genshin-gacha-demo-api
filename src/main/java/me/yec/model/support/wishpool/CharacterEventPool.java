package me.yec.model.support.wishpool;

import java.io.Serializable;

/**
 * @author yec
 * @date 12/4/20 10:41 PM
 */
public class CharacterEventPool extends GenshinEventWishPool implements Serializable {

    private static final long serialVersionUID = 8480268142659091689L;

    public CharacterEventPool() {
        type = GenshinWishPoolType.CHARACTER_EVENT;
    }

}
