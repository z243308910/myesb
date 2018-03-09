package com.echounion.bossmanager.common.captcha;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 提供验证码图片的Servlet
 * @author 胡礼波
 * 10:08:05 AM Oct 17, 2012
 */
@SuppressWarnings("serial")
public class JcaptchaServlet extends HttpServlet {
	
	private Logger logger=Logger.getLogger(JcaptchaServlet.class);
	
	public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";
	private ImageCaptchaService captchaService;

	@Override
	public void init() throws ServletException {
		WebApplicationContext appCtx = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		captchaService = (ImageCaptchaService) BeanFactoryUtils
				.beanOfTypeIncludingAncestors(appCtx, ImageCaptchaService.class);
		logger.info("验证码生成器初始化完毕");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		boolean flag=false;
		BufferedImage challenge =null;
		do
		{
			try {
				String captchaId = request.getSession().getId();
				challenge= captchaService.getImageChallengeForID(captchaId, request.getLocale());
				ImageIO.write(challenge, CAPTCHA_IMAGE_FORMAT, jpegOutputStream);
				flag=false;
			} catch (IllegalArgumentException e) {
				flag=true;
			} catch (CaptchaServiceException e) {
				flag=true;
			}
			catch (Exception e) {
				flag=true;
				logger.info("验证码生成异常......"+e.getMessage());
			}
		}while(flag);
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/" + CAPTCHA_IMAGE_FORMAT);

		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}
}
