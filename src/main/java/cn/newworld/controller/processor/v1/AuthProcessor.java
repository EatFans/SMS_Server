package cn.newworld.controller.processor.v1;

import cn.newworld.controller.Processor;
import cn.newworld.http.RequestMapping;
import cn.newworld.http.RequestType;
import cn.newworld.http.ContentType;
import cn.newworld.http.HttpStatus;
import cn.newworld.http.model.Request;
import cn.newworld.http.model.Response;
import cn.newworld.util.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class AuthProcessor implements Processor {
    private final String URL = "/v1/auth";

    @RequestMapping(requestUrl = URL+"/login", requestType = RequestType.GET)
    public String userLogin(Request request){
        // TODO: 处理用户登录

        String test = "{\n" +
                "    \"action\": \"userLogin\",\n" +
                "    \"data\": {\n" +
                "        \"name\": \"testMan\",\n" +
                "        \"password\": \"123456\",\n" +
                "        \"token\": \"32feq13\"\n" +
                "    }\n" +
                "}";


        return Response.createResponse()
                .setStatus(HttpStatus.OK.getCode())
                .setReasonPhrase(HttpStatus.OK.getReasonPhrase())
                .addHeader("Content-Type", ContentType.APPLICATION_JSON)
                .addHeader("Content-Length", String.valueOf(test.length()))
                .setBody(test)
                .buildResponseMessage();
    }

    @RequestMapping(requestUrl = URL+"/register", requestType = RequestType.GET)
    public String register(Request request){
        // TODO: 处理用户注册
        String test = "{\n" +
                "    \"action\": \"userLogin\",\n" +
                "    \"data\": {\n" +
                "        \"name\": \"testMan\",\n" +
                "        \"password\": \"123456\",\n" +
                "        \"token\": \"32feq13\"\n" +
                "    }\n" +
                "}";
        return Response.createResponse().setBody(test).buildResponseMessage();
    }

    @RequestMapping(requestUrl = URL+"/test",requestType = RequestType.GET)
    public String test(Request request){
        Map<String, String> queryParams = request.getQueryParams();
        String test = "Hi! EatFan";
        // 查询url 中的数据
        String name = queryParams.get("name");
        if (name == null){
            Logger.info("请求的参数为空");
            return null;
        }
        return Response.createResponse()
                .setStatus(HttpStatus.OK.getCode())
                .setReasonPhrase(HttpStatus.OK.getReasonPhrase())
                .addHeader("Content-Type", ContentType.TEXT_PLAIN)
                .addHeader("Content-Length", String.valueOf(test.length()))
                .setBody(test)
                .buildResponseMessage();
    }

    @RequestMapping(requestUrl = "/download",requestType = RequestType.GET)
    public String download(Request request){
        String testFilePath = "test.txt";
        byte[] binaryData = new byte[0];
        try (FileInputStream fileInputStream = new FileInputStream(new File(testFilePath))){
            binaryData = fileInputStream.readAllBytes();

        } catch (IOException e){
            Logger.error(e.getMessage());
        }

        Response response = Response.createResponse();
        response.setStatus(HttpStatus.OK.getCode());
        response.setReasonPhrase(HttpStatus.OK.getReasonPhrase());
        response.addHeader("Content-Type",ContentType.TEXT_PLAIN);
        response.addHeader("Content-Disposition", "attachment; filename=\"test.txt\"");
        response.setBody(binaryData);
        return response.buildResponseMessage();
    }

    @RequestMapping(requestUrl = "/login")
    public String getHtml(Request request){
        String loginHtml = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>登录</title>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/loginStyle.css\">\n" +
                "    <link href=\"https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css\" rel=\"stylesheet\">\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div class=\"wrapper\">\n" +
                "        <form action=\"\">\n" +
                "            <h1>登录</h1>\n" +
                "            <div class=\"input-box\">\n" +
                "                <label>\n" +
                "                    <input type=\"text\" placeholder=\"用户名\" required>\n" +
                "                    <i class=\"bx bxs-user\"></i>\n" +
                "                </label>\n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"input-box\">\n" +
                "                <label>\n" +
                "                    <input type=\"text\" placeholder=\"密码\" required>\n" +
                "                    <i class=\"bx bxs-lock-alt\"></i>\n" +
                "                </label>\n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"remember-forget\" >\n" +
                "                <label>\n" +
                "                    <input type=\"checkbox\"> 记住我\n" +
                "                </label>\n" +
                "                <a href=\"#\">忘记密码？</a>\n" +
                "            </div>\n" +
                "\n" +
                "            <button type=\"submit\" class=\"button\">登录</button>\n" +
                "\n" +
                "            <div class=\"register-link\">\n" +
                "                <p>没有账户？<a href=\"#\">注册</a></p>\n" +
                "            </div>\n" +
                "        </form>\n" +
                "    </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<script></script>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        return Response.createResponse()
                .setStatus(HttpStatus.OK.getCode())
                .setReasonPhrase(HttpStatus.OK.getReasonPhrase())
                .addHeader("Content-Type",ContentType.TEXT_HTML)
                .setBody(loginHtml)
                .buildResponseMessage();

    }

    @RequestMapping(requestUrl = "/css/loginStyle.css")
    public String getCss(Request request){
        String css = "* {\n" +
                "    margin: 0;\n" +
                "    padding: 0;\n" +
                "    box-sizing: border-box;\n" +
                "\n" +
                "}\n" +
                "\n" +
                "body {\n" +
                "    display: flex;\n" +
                "    justify-content: center;\n" +
                "    align-items: center;\n" +
                "    min-height: 100vh;\n" +
                "    background: url(\"https://foruda.gitee.com/images/1706362000069689950/adc411eb_11560668.jpeg\") no-repeat;\n" +
                "    background-size: cover;\n" +
                "    background-position: center;\n" +
                "}\n" +
                "\n" +
                ".wrapper {\n" +
                "    width: 420px;\n" +
                "    background: transparent;\n" +
                "    border: 2px solid rgba(256,256,256,.2);\n" +
                "    backdrop-filter: blur(20px);\n" +
                "    box-shadow: 0 0 10px rgba(0,0,0, .2);\n" +
                "    color: #fff;\n" +
                "    border-radius: 10px;\n" +
                "    padding: 30px 40px;\n" +
                "}\n" +
                "\n" +
                ".wrapper h1 {\n" +
                "    font-size: 36px;\n" +
                "    text-align: center;\n" +
                "\n" +
                "}\n" +
                "\n" +
                ".wrapper .input-box {\n" +
                "    position: relative;\n" +
                "    width: 100%;\n" +
                "    height: 50px;\n" +
                "    margin: 30px 0;\n" +
                "}\n" +
                "\n" +
                ".input-box input {\n" +
                "    width: 100%;\n" +
                "    height: 100%;\n" +
                "    background: transparent;\n" +
                "    outline: none;\n" +
                "    border: 2px solid rgba(255,255,255, .2);\n" +
                "    border-radius: 40px;\n" +
                "    font-size: 16px;\n" +
                "    color: #ffffff;\n" +
                "    padding: 20px 45px 20px 20px;\n" +
                "}\n" +
                "\n" +
                ".input-box input::placeholder {\n" +
                "    color: #ffffff;\n" +
                "\n" +
                "}\n" +
                "\n" +
                ".input-box i {\n" +
                "    position: absolute;\n" +
                "    right: 20px;\n" +
                "    top: 50%;\n" +
                "    transform: translateY(-50%);\n" +
                "    font-size: 20px;\n" +
                "}\n" +
                "\n" +
                ".wrapper .remember-forget {\n" +
                "    display: flex;\n" +
                "    justify-content: space-between;\n" +
                "    font-size: 14,5px;\n" +
                "    margin: -15px 0 15px;\n" +
                "}\n" +
                "\n" +
                ".remember-forget label input {\n" +
                "    accent-color: #ffffff;\n" +
                "    margin-right: 3px;\n" +
                "}\n" +
                "\n" +
                ".remember-forget a {\n" +
                "    color: #ffffff;\n" +
                "    text-decoration: none;\n" +
                "}\n" +
                "\n" +
                ".remember-forget a:hover {\n" +
                "    text-decoration: underline;\n" +
                "}\n" +
                "\n" +
                ".wrapper .button {\n" +
                "    width: 100%;\n" +
                "    height: 45px;\n" +
                "    background: #ffffff;\n" +
                "    border: none;\n" +
                "    outline: none;\n" +
                "    border-radius: 40px;\n" +
                "    box-shadow: 0 0 10px rgba(0,0,0,.1);\n" +
                "    cursor: pointer;\n" +
                "    font-size: 16px;\n" +
                "    color: #333;\n" +
                "    font-weight: 600;\n" +
                "}\n" +
                "\n" +
                ".wrapper .register-link {\n" +
                "    font-size: 14.5px;\n" +
                "    text-align: center;\n" +
                "    margin: 20px 0 15px;\n" +
                "}\n" +
                "\n" +
                ".register-link p a {\n" +
                "    color: #ffffff;\n" +
                "    text-decoration: none;\n" +
                "    font-weight: 600;\n" +
                "}\n" +
                "\n" +
                ".register-link p a:hover {\n" +
                "    text-decoration: underline;\n" +
                "}";

        return Response.createResponse()
                .setStatus(HttpStatus.OK.getCode())
                .setReasonPhrase(HttpStatus.OK.getReasonPhrase())
                .addHeader("Content-Type",ContentType.TEXT_CSS)
                .setBody(css)
                .buildResponseMessage();
    }

}
