package edu.szu.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import szu.vander.model.Player;

/**
 * @author : Vander
 * @date :   2019/8/11
 * @description :
 */
@RestController
public class PlayerController {

    @Autowired
    private Player player;

    @GetMapping("/show")
    public String showPlayer() {
        System.out.println(player.toString());
        return player.toString();
    }

}
