/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.portal.beans;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dematjasic - chatgpt
 */
@WebServlet("/file/*")
public class FileServlet extends HttpServlet {

    @Inject
    private FileManagerBean fileManagerBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fileName = request.getPathInfo().substring(1); // Gets file name from request
        File file = new File(fileManagerBean.getCurrentDirectory(), fileName);

        response.setHeader("Content-Type", getServletContext().getMimeType(fileName));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");

        Files.copy(file.toPath(), response.getOutputStream());
    }
}

