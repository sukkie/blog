package com.cos.blog.interceptor;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 실행 시간을 기록하기 위한 Interceptor.
 * 
 * @author oykwon
 *
 */
@Log4j2
public class ExecuteTimerInterceptor implements HandlerInterceptor {

//	@Value("${test.path:/temp}")
//	private String path;

	private static final String ATTRIBUTE_KEY = ExecuteTimerInterceptor.class.getName();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute(ATTRIBUTE_KEY, System.nanoTime());

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		val beforeNanoSec = request.getAttribute(ATTRIBUTE_KEY);
		if (beforeNanoSec instanceof Long) {
			val elapsedNanoSec = System.nanoTime() - (long) beforeNanoSec;
			val elapsedMilliSec = java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(elapsedNanoSec);
			if (log.isDebugEnabled()) {
				log.debug("Elapsed {} ms", elapsedMilliSec);
			}
		}
	}
}
