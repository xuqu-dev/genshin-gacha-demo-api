package me.yec.controller;

import lombok.RequiredArgsConstructor;
import me.yec.model.support.Result;
import me.yec.service.SimpleAuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yec
 * @date 12/6/20 8:04 PM
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SimpleAuthController {

    private final SimpleAuthService simpleAuthService;

    @GetMapping("/auth")
    public Result<String> auth() {
        String s = simpleAuthService.doAuth();
        return Result.ok(s);
    }
}
