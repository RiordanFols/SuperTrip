package ru.chernov.supertrip.domain.dto;

import lombok.Getter;
import ru.chernov.supertrip.domain.entity.User;

/**
 * @author Pavel Chernov
 */
@Getter
public class PrivilegeInfo {

    private final double spent;
    private final double threshold;
    private final String desc;

    public PrivilegeInfo(User user) {
        var curLevel = user.getPrivilegeLevel();
        var spent = user.getSpent();
        var firstPart = "You spent " + spent + "$. ";
        var secondPart = "You have " + curLevel.toString().toLowerCase() + " status. ";
        var nextLevel = curLevel.getNextLevel();

        if (nextLevel != null) {
            var left = nextLevel.getThreshold() - user.getSpent();
            var nextLevelName = nextLevel.toString().toLowerCase();
            secondPart += left + "$ until " + nextLevelName + " status.";
            this.threshold = nextLevel.getThreshold();
        } else {
            this.threshold = curLevel.getThreshold();
        }

        this.spent = spent;
        this.desc = firstPart + secondPart;
    }
}
