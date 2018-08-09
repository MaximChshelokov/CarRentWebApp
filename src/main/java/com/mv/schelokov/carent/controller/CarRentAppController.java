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

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Action action = ActionFactory.action(request);
        request.setAttribute("URL", request.getRequestURI());

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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Front controller";
    }// </editor-fold>

}
