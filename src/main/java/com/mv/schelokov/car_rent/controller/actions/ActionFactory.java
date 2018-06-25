package com.mv.schelokov.car_rent.controller.actions;

import com.mv.schelokov.car_rent.controller.Urls;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ActionFactory {
    
    public static Action action(HttpServletRequest req) {
        String actionName = req.getPathInfo().replaceAll("/", "").toUpperCase();
        if ("".equals(actionName))
            return new ShowPage("index.html");
        switch(Urls.valueOf(actionName)) {
            case HOME:
                return new ShowPage("index.html");
            default:
                return new ShowPage("index.html");                
        }
    }
}
