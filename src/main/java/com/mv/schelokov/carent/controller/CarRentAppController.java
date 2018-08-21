package com.mv.schelokov.carent.controller;

import com.mv.schelokov.carent.actions.interfaces.Action;
import com.mv.schelokov.carent.actions.factory.ActionFactory;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.actions.exceptions.ActionException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.rmi.ServerException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CarRentAppController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(CarRentAppController.class);
    private static final String REDIRECTION_ERROR = "Redirection error";

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActionFactory actionFactory = new ActionFactory();
        Action action = actionFactory.createAction(request);
        request.setAttribute("URL", request.getQueryString() != null ? 
                String.join("", request.getRequestURI(), "?", request.getQueryString())
                : request.getRequestURI());

        if (action == null) {
            response.sendError(HttpURLConnection.HTTP_NOT_FOUND);
            return;
        }
        JspForward forward = new JspForward();
        try {
            forward = action.execute(request, response);
        } catch (ActionException ex) {
            LOG.error(REDIRECTION_ERROR, ex);
            throw new ServerException(REDIRECTION_ERROR);
        }
        if (!forward.getUrl().isEmpty()) {
            String path = "/" + forward.getUrl();
            if (forward.isRedirect()) {
                response.sendRedirect(response.encodeRedirectURL(path));
            } else {
                request.getRequestDispatcher(response.encodeURL(path))
                        .forward(request, response);
            }
        }
    }
}
