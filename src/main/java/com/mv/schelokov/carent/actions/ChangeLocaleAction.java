package com.mv.schelokov.carent.actions;

import com.mv.schelokov.carent.actions.consts.Actions;
import com.mv.schelokov.carent.actions.interfaces.AbstractAction;
import com.mv.schelokov.carent.actions.consts.Cookies;
import com.mv.schelokov.carent.actions.consts.Locales;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ChangeLocaleAction extends AbstractAction {
    private static final int ONE_MONTH = 60 * 60 * 24 * 30;
    private static final String LOCALE = "locale";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        String localeString = req.getParameter(LOCALE);
        if (Locales.ENGLISH.equals(localeString)
                || Locales.RUSSIAN.equals(localeString)) {
            Cookie cookie = new Cookie(Cookies.LOCALE, localeString);
            cookie.setMaxAge(ONE_MONTH);
            res.addCookie(cookie);
        }
        if (isUser(req) || isAdmin(req))
            return new JspForward(Actions.getActionName(Actions.WELCOME), true);
        return new JspForward(Actions.getActionName(Actions.HOME), true);
    }
}
