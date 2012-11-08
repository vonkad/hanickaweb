package com.vonkova.servlets;

import com.vonkova.StudentFilesAndData;
import com.vonkova.StudentManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ViewFilesServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String groupIdAsString = request.getParameter("groupId");
        if (groupIdAsString== null){
            forwardToError(request,response,"No groupId parameter");
            return;
        }
        int groupId;
        try{
            groupId = Integer.parseInt(groupIdAsString);
        }catch(Exception e){
            forwardToError(request,response,"groupId="+groupIdAsString+", which is not an integer");
            return;
        }
        StudentManager studentManager = new StudentManager();
        List<StudentFilesAndData> students = studentManager.getStudentsFromGroup(groupId);
        Map<Integer,String> types = studentManager.getTypesForGroup(groupId);
        Map<Integer,String> klice = studentManager.getKliceForGroup(groupId);
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        request.setAttribute("students", students);
        request.setAttribute("types", types);
        request.setAttribute("klice", klice);
        RequestDispatcher view = request.getRequestDispatcher("/restricted/files.vm");
        view.forward(request, response);
    }

    private void forwardToError(HttpServletRequest request, HttpServletResponse response,String error)
            throws ServletException, IOException{
        request.setAttribute("errorMessage",error);
        RequestDispatcher view = request.getRequestDispatcher("/restricted/files.vm");
        view.forward(request, response);
    }
}
