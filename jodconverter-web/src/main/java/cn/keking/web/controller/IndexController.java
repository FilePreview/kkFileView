package cn.keking.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面跳转
 *
 * @author yudian-it
 * @date 2017/12/27
 */
@Controller
public class IndexController {
    /**
     * Author：houzheng
     * Date：11-18
     * 转到Index
     */
    @GetMapping(value = "/index")
    public String go2Index() {
        return "index";
    }

    /**
     * Author：houzheng
     * Date：11-18
     * 跳转到根目录
     */
    @GetMapping(value = "/")
    public String root() {
        return "redirect:/index";
    }
}