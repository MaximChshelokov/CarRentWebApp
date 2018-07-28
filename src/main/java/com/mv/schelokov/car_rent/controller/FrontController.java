package com.mv.schelokov.car_rent.controller;

import com.mv.schelokov.car_rent.controller.actions.Action;
import com.mv.schelokov.car_rent.controller.actions.ActionFactory;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
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
public class FrontController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(FrontController.class);

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
        if (action == null) {
            response.sendError(HttpURLConnection.HTTP_NOT_FOUND);
            return;
        }
        JspForward forward = null;
        try {
            forward = action.execute(request, response);
        } catch (ActionException ex) {
            LOG.error("Failed to redirect", ex);
            throw new ServerException("Redirection error");
        }
        if (forward != null) {
            String path = "/" + forward.getUrl();
            if (forward.isRedirect()) {
                response.sendRedirect(path);
            } else {
                request.getRequestDispatcher(path).forward(request, response);
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
