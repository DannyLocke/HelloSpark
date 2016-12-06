package com.ironyard;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.lang.reflect.Array;
import java.util.HashMap;

public class Main {

    public static User user;

    public static void main(String[] args) {

        Spark.init();

        //get the site
        Spark.get(
                "/",    //argument 1 of 3 : location of the page (root)

                ((request, response) -> {       //argument 2 of 3: lambda to put together code
                    HashMap m = new HashMap();

                    if(user == null){
                        return new ModelAndView(m, "login.html");
                    }
                    else {
                        m.put("name", user.name);
                        return new ModelAndView(m, "home.html");    //3 of 3: return a hashmap data and file where data goes
                    }
                }),

                new MustacheTemplateEngine()//the templates engine I'm using
        );

        //receive input from user and post to site
        Spark.post(
                "/login",

                ((request, response) -> {
                    String name = request.queryParams("loginName");
                    //create user object
                    user = new User(name);
                    response.redirect("/");

                    return "";
                })
        );
        Spark.post(
                "/logout",
                ((request,response) -> {
                    user = null;
                    response.redirect("/");
                    return "";
                })
        );
    }
}
