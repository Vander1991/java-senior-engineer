package szu.vander.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : Vander
 * @date :   2019/8/10
 * @description :
 */
@ToString
@Getter
@Setter
@ConfigurationProperties(prefix = "game.player")
public class PlayerProperties {

    private String playerId;

    private String playerName;

}
