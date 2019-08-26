package szu.vander.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import szu.vander.model.Player;

/**
 * @author : Vander
 * @date :   2019/8/10
 * @description :
 */
@EnableConfigurationProperties(PlayerProperties.class)
public class PlayerAutoconfigure {

    /**
     * 注入Player Bean
     *
     * @param playerProperties
     * @return
     */
    @Bean
    public Player getPlayer(PlayerProperties playerProperties) {
        Player player = new Player();
        player.setPlayerId(playerProperties.getPlayerId());
        player.setPlayerName(playerProperties.getPlayerName());
        return player;
    }

}
