package com.vonkova.servlets;

import com.vonkova.DbManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Blob;
import java.sql.ResultSet;
import java.text.Normalizer;

public class DownloadFileServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String user = req.getParameter("user");
        String type = req.getParameter("type");
        final DbManager dbManager = new DbManager();
        try {
            ResultSet rs = dbManager.query("select filename,content " +
                    "from files where userid='" + user + "' and typeid=" + type+
                     " and current=1");

            if (rs.next()) {
                Blob blob = rs.getBlob("content");
                InputStream r = blob.getBinaryStream();
                String contentType = "application/binary";
                String fname = rs.getString("filename");
                res.setCharacterEncoding("UTF-8");
                res.setHeader("Content-Disposition", "attachment; filename=\"" + Normalizer.normalize(fname, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "") + "\"");
                if (fname.indexOf('.') > -1) {
                    contentType = "text/" + fname.substring(fname.indexOf(".") + 1);
                }
                res.setContentType(contentType);
                int read = 0;

                byte[] bytes = new byte[1024];
                OutputStream out = res.getOutputStream();

                while ((read = r.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
            } else {
                throw new RuntimeException("rs.next() is null");
            }
        } catch (Exception e) {
            throw new RuntimeException("failed at getting content for " + user + " of type " + type, e);
        } finally {
            dbManager.finalizeQuery();
            dbManager.closeConnection();
        }
    }
}