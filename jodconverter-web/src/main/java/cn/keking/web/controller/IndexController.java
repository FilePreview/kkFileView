package cn.keking.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *  页面跳转
 * @author yudian-it
 * @date 2017/12/27
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    /**
     * Author：houzheng
     * Date：11-18
     * 转到Index
     *
     */

    public String go2Index(){
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    /**
     * Author：houzheng
     * Date：11-18
     * 跳转到根目录
     *
     */

    public String root() {
        return "redirect:/index";
    }
}
