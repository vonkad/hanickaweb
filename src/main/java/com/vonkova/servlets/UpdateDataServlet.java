package com.vonkova.servlets;

import com.vonkova.DbManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vonkad
 * Date: 4/19/12
 * Time: 11:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateDataServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final int groupId = extractIntFromParam(request, response, "groupId");
        final int userId = extractIntFromParam(request, response, "userId");
        final int key = extractIntFromParam(request, response, "key");
        final String value = request.getParameter("value");
        final DbManager dbManager = new DbManager();
        try {
            dbManager.update("delete from user_group_data where userid=" + userId + " and groupid=" + groupId + " and klic=" + key);
            dbManager.finalizeQuery();
            dbManager.update("insert into user_group_data values (" + userId + "," + groupId + "," + key + "," + (value == null ? "NULL" : ("\"" + value + "\""))+")");
            dbManager.finalizeQuery();
            dbManager.closeConnection();
        } finally {
            RequestDispatcher rd =request.getRequestDispatcher("/restricted/viewfiles.action?groupId="+groupId);
            rd.forward(request,response);
        }
    }

    private Integer extractIntFromParam(HttpServletRequest request, HttpServletResponse response, String param) throws ServletException, IOException {
        String groupIdAsString = request.getParameter(param);
        if (groupIdAsString == null) {
            forwardToError(request, response, "No " + param + " parameter");
            return null;
        }
        int value;
        try {
            value = Integer.parseInt(groupIdAsString);
            return value;
        } catch (Exception e) {
            forwardToError(request, response, param + "=" + groupIdAsString + ", which is not an integer");
            return null;
        }
    }

    private void forwardToError(HttpServletRequest request, HttpServletResponse response, String error)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", error);
        RequestDispatcher view = request.getRequestDispatcher("/restricted/files.vm");
        view.forward(request, response);
    }


}