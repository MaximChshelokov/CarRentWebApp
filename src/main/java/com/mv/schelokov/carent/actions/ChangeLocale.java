package com.mv.schelokov.carent.actions;

import com.mv.schelokov.carent.consts.Cookies;
import com.mv.schelokov.carent.exceptions.ActionException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ChangeLocale extends AbstractAction {
    private static final int ONE_MONTH = 60 * 60 * 24 * 30;

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        String localeString = req.getParameter("locale");
        if ("en".equals(localeString) || "ru".equals(localeString)) {
            Cookie cookie = new Cookie(Cookies.LOCALE, localeString);
            cookie.setMaxAge(ONE_MONTH);
            res.addCookie(cookie);
        }
        if (isUser(req) || isAdmin(req))
            return new JspForward("action/welcome", true);
        return new JspForward("action/home", true);
    }
}
