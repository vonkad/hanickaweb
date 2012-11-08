package com.vonkova.servlets;

import com.vonkova.DbManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UploadFileServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doGet(req, res);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");


        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        List<FileItem> lof;
        try {
            lof = upload.parseRequest(req);
        } catch (FileUploadException fue) {
            throw new RuntimeException(fue);
        }

        Integer groupId = null;

        try {
            DbManager dbManager = new DbManager();
            
            Connection conn = dbManager.getConnection();
            String user = null;            
            Integer type = null;
            String fileName = null;
            InputStream is = null;
            Long fileSize = null;
            for (FileItem fi : lof) {
                if (fi.isFormField()){
                    if ("user".equals(fi.getFieldName())){
                        user = fi.getString("UTF-8");
                    }else if ("type".equals(fi.getFieldName())){
                        type = Integer.parseInt(fi.getString("UTF-8"));
                    }else if ("groupId".equals(fi.getFieldName())){
                        groupId = Integer.parseInt(fi.getString("UTF-8"));
                    }
                }else{
                    is = fi.getInputStream();
                    fileSize = fi.getSize();
                    fileName = fi.getName();
                }
            }

            if (user != null && type != null && fileSize != null && fileName !=null && fileSize > 0 && fileName.length()>0) {
                
                ResultSet maxVersionRS = dbManager.query("select max(version) from files where userid=" + user + " and typeid=" + type + " group by userid,typeid");
                int maxVersion = 0;
                if (maxVersionRS.next()) {
                    maxVersion = maxVersionRS.getInt(1);
                }
                dbManager.finalizeQuery();
                
                dbManager.update("update files set current=0 where userid='" + user + "' and typeid=" + type + " and version="+maxVersion);
                
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO files VALUES (?,?,?,?,?,?)");
                pstmt.setString(1, user);
                pstmt.setInt(2, type);
                pstmt.setInt(3,maxVersion+1);
                pstmt.setBoolean(4, true); // current
                pstmt.setString(5, fileName);
                pstmt.setBinaryStream(6, is, fileSize.intValue());
                pstmt.executeUpdate();
                pstmt.close();
                pstmt = null;
                is.close();
                is = null;
            } else {
                req.setAttribute("errorMessage","The upload has failed");
            }

            dbManager.closeConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher rd =req.getRequestDispatcher("/restricted/viewfiles.action?groupId="+groupId);
        rd.forward(req,res);
    }
}