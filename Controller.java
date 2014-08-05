package others;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

public abstract class Controller extends HttpServlet {

    private void callFunction(HttpServletRequest request, HttpServletResponse response, String nombre, String type) {
        Class c = this.getClass();
        Object[] args_value = {request, response};
        Class[] args_class = {HttpServletRequest.class, HttpServletResponse.class};
        Method m = null;

        String action = (nombre.charAt(0) + "").toUpperCase();
        nombre = nombre.replaceFirst(nombre.charAt(0) + "", action);

        try {
            m = c.getMethod(type + nombre, args_class);
        } catch (SecurityException se) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, se);
        } catch (NoSuchMethodException nsme) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, nsme);
        }

        if (m != null) {
            try {
                m.invoke(this, args_value);
            } catch (IllegalArgumentException iae) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, iae);
            } catch (IllegalAccessException iae) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, iae);
            } catch (InvocationTargetException ite) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ite);
            }
        } else {
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
            callFunction(request, response, action, "action");
            //action = "Index";
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = "";
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                request.setAttribute("multiparts", multiparts);
                for(FileItem item: multiparts) {
                    if(item.isFormField() && item.getFieldName().equals("action")) {
                        action = Streams.asString(item.getInputStream());
                        break;
                    }
                }
            } catch (FileUploadException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            action = request.getParameter("action");
        }
        /*if (action == null) {
        System.out.println("Datos: : D");
            Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
            for (Map.Entry<String, String[]> entry : entrySet) {
                System.out.print(entry.getKey());
                for (String ss : entry.getValue()) {
                    System.out.println(ss);
                }
            }
        }*/
        System.err.println("Esto en action por POST: " + action);
        if (action != null && !action.isEmpty()) {
            callFunction(request, response, action, "post");
        }
    }
    
    protected void checkAccessLogin(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession(true).getAttribute("id_user") == null) {
            response.sendRedirect("Login");
        }        
    }
    
    protected void checkAccessAdmin(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession(true).getAttribute("admin_mode") == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }        
    }
    
    protected boolean isAdmin(HttpServletRequest request) {
        boolean isAdmin_ = false;
        if(request.getSession(true).getAttribute("admin_mode") != null) {
            isAdmin_ = true;
        }
        return isAdmin_;
    }
}
