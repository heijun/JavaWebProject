package com.example_01;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import com.sun.xml.internal.ws.api.pipe.Engine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/index")
public class ThymeleafServlet extends HttpServlet {
    TemplateEngine engine;
    @Override
    public void init() throws ServletException {
        engine = new TemplateEngine();
        //设定模板解析器决定了从哪里获取模板文件，这里直接使用ClassLoaderTemplateResolver表示加载内部资源文件
        ClassLoaderTemplateResolver r = new ClassLoaderTemplateResolver();
        engine.setTemplateResolver(r);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        //创建上下文，上下文中包含了所有需要替换到模板中的内容
        Context context = new Context();
        context.setVariable("title", "我是标题");
        //通过此方法就可以直接解析模板并返回响应
        engine.process("index.html", context, resp.getWriter());
    }
}
