package com.mv.schelokov.car_rent.controller.actions;

import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ShowPage extends AbstractAction {
    
    private static final Logger log = Logger.getLogger(ShowPage.class);
    private final String url;
    
    public ShowPage(String url) {
        this.url = url;
    }

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        // Get the subdirectory name in the jsp folder. This name must be the
        // same as a role name for the user (case insensitive)
        String urlAccessRole = url.replace("WEB-INF/jsp", "");
        urlAccessRole = urlAccessRole.substring(0, urlAccessRole
                .lastIndexOf("/")).replaceAll("/", "");
        if (urlAccessRole.length() > 0) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                User user = (User) session.getAttribute("user");
                if (user == null || !urlAccessRole.toLowerCase()
                        .equals(user.getRole().getRoleName().toLowerCase())) {
                    sendForbidden(res);
                    return null;
                }
            }
        }
        return new JspForward(url);
    }
    
}
