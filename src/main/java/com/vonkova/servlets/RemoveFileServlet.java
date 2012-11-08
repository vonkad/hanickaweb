package com.vonkova.servlets;

import com.vonkova.DbManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.buf.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vonkad
 * Date: 12/2/throw new RuntimeException("rs.next() is null");11
 * Time: 1:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class RemoveFileServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String user = req.getParameter("user");
        String type = req.getParameter("type");
        String groupId = req.getParameter("groupId");
        final DbManager dbManager = new DbManager();
        try {
            int rows = dbManager.update("update files set current=0 where userid='" + user + "' and typeid=" + type);
            if (rows > 0) {
                RequestDispatcher rd =req.getRequestDispatcher("/restricted/viewfiles.action?groupId="+groupId);
                rd.forward(req,res);
            } else {
                res.setContentType("text/html");
                PrintWriter pw = res.getWriter();
                pw.println("<html><body>removing failed !</body></html>");
                pw.close();
            }

        } catch (Exception e) {
            throw new RuntimeException("failed at getting content for " + user + " of type " + type, e);
        } finally {
            dbManager.finalizeQuery();
            dbManager.closeConnection();
        }
    }
}
