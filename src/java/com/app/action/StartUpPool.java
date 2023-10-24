 package com.app.action;

import com.app.connection.JDCConnectionDriver;
import com.app.util.ParametroUtil;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;

public class StartUpPool extends HttpServlet {

    private JDCConnectionDriver connectionPool;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            ServletContext context = getServletContext();
            connectionPool = new JDCConnectionDriver(context.getInitParameter("lookupName"));
            ParametroUtil.IDR_HOME = context.getInitParameter("idrHome");
            context.setAttribute("POOL", connectionPool);
            System.out.println("Pool >> Carregado.");
        }
        catch(Exception e) {
            System.err.println("Erro na montagem do POOL de conexoes: " + e.getMessage());
            connectionPool = null;
        }
    }

    protected int getPoolSize() {
        return(1);
    }

    protected int getPoolMaxSize() {
        return(6);
    }
}
