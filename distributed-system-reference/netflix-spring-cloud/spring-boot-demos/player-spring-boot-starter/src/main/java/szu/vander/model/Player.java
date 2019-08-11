package szu.vander.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : Vander
 * @date :   2019/8/10
 * @description :
 */
@Setter
@Getter
@ToString
public class Player {

    /**
     * 玩家ID
     */
    private String playerId;

    /**
     * 玩家名称
     */
    private String playerName;

}
