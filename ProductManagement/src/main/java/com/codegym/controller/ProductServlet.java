package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.service.ProductService;
import com.codegym.service.ProductServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name="ProductServlet", urlPatterns="/products")
public class ProductServlet extends HttpServlet {
    private final ProductService service = new ProductServiceImpl();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        req.setCharacterEncoding("UTF-8"); String action=req.getParameter("action"); if(action==null)action="list";
        switch(action){
            case "create": req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req,resp); break;
            case "edit": showEdit(req,resp); break;
            case "delete": service.remove(Integer.parseInt(req.getParameter("id"))); resp.sendRedirect(req.getContextPath()+"/products"); break;
            case "view": showView(req,resp); break;
            case "search": req.setAttribute("products",service.searchByName(req.getParameter("name"))); req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req,resp); break;
            default: req.setAttribute("products",service.findAll()); req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req,resp);
        }
    }
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        req.setCharacterEncoding("UTF-8"); String action=req.getParameter("action");
        Product p=readProduct(req);
        if("create".equals(action)) service.save(p); else if("edit".equals(action)) service.update(Integer.parseInt(req.getParameter("id")),p);
        resp.sendRedirect(req.getContextPath()+"/products");
    }
    private Product readProduct(HttpServletRequest req){return new Product(0,req.getParameter("name"),Double.parseDouble(req.getParameter("price")),req.getParameter("description"),req.getParameter("manufacturer"));}
    private void showEdit(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{Product p=service.findById(Integer.parseInt(req.getParameter("id"))); req.setAttribute("product",p); req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req,resp);}
    private void showView(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{Product p=service.findById(Integer.parseInt(req.getParameter("id"))); req.setAttribute("product",p); req.getRequestDispatcher("/WEB-INF/views/view.jsp").forward(req,resp);}
}
