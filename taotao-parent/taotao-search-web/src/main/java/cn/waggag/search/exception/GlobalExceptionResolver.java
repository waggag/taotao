package cn.waggag.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

/**
 * @author 王港
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object hander,
                                         Exception exception) {
        //控制台打印异常
        logger.info("进入全局异常处理器！");
        logger.debug("测试Hander的类型" + hander.getClass());
        //控制台打印异常
        exception.printStackTrace();
        //向日志文件中写入异常
        logger.error("系统发生异常", exception);
        //发邮件jmail
        //发信息，第三方公司
        //展示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "你的网络有问题，请稍后重试！");
        modelAndView.setViewName("error/execption");
        return modelAndView;
    }

}
