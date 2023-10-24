/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.action;

import com.app.connection.JDCConnectionDriver;
import com.app.util.Errors;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author macuser
 */
public abstract class IDRAction extends Action {

    protected static JDCConnectionDriver connectionPool;
    protected boolean requerAutorizacao;

    public IDRAction() {
        requerAutorizacao = true;
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ActionErrors actionErrors = new ActionErrors();
        Errors errors = new Errors();
        ActionForward forward = null;

        try {
            ServletContext context = super.getServlet().getServletContext();
            connectionPool = (JDCConnectionDriver) context.getAttribute("POOL");//Obtendo a referencia do Pool de Conexoes
            if (connectionPool == null) {
                throw new IllegalArgumentException();
            } else {
                String action = request.getParameter("action");
                if (action == null) {
                    action = "page";
                }
                forward = this.execute(mapping, form, request, response, errors, action);
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            forward = mapping.findForward("nonConnected");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Salva os erros
        errors.save(request);

        if (!actionErrors.isEmpty()) {
            saveErrors(request, actionErrors);
        }
        return forward;

    }

    public abstract ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response, Errors errors, String action);
}
