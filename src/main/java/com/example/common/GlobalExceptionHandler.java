package com.example.common;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * アプリケーション内で処理されなかった例外をキャッチする。
 * エラーページへ遷移させます。
 *
 * @author T.Araki
 *
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        return null;
    }

}
