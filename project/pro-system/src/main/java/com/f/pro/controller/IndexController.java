package com.f.pro.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.f.pro.common.constant.ProConstant;
import com.f.pro.common.exception.ValidateCodeException;
import com.f.pro.common.util.R;
import com.f.pro.domain.SysRole;
import com.f.pro.domain.SysUser;
import com.f.pro.dto.user.RegisterUserDTO;
import com.f.pro.security.code.img.CaptchaUtil;
import com.f.pro.security.code.sms.service.SmsCodeService;
import com.f.pro.security.swagger.PublicApi;
import com.f.pro.security.util.SecurityUtil;
import com.f.pro.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(tags = "1.后台登录前的接口对接控制器")
@RestController
@Validated
public class IndexController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private SmsCodeService smsCodeService;

    @Value("${project.url.address}")
    private String url;

//    @ApiOperation(value = "生成验证码(用于用户登录使用)", notes = "生成验证码")
    @PublicApi(value = "生成验证码(用于用户登录使用)", notes = "生成验证码")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query", name = "t", value = "t", required = false, dataType = "String"))
    @GetMapping(value = "/captcha.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        // 生成图片验证码
        BufferedImage image = CaptchaUtil.createImage();
        // 生成文字验证码
        String randomText = CaptchaUtil.drawRandomText(image);
        // 保存到验证码到 redis 有效期两分钟
        String t = request.getParameter("t");
        redisTemplate.opsForValue().set(ProConstant.PRO_IMAGE_KEY + t, randomText.toLowerCase(), 2, TimeUnit.MINUTES);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpeg", out);
    }


    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @PostMapping("/sendCode/{phone}")
    public R sendSmsCode(@PathVariable("phone") String phone) {
        Map<String, Object> map = smsCodeService.sendCode(phone);
        // 获取状态码 00000 成功 00141 一小时内发送给单个手机次数超过限制 00142 一天内发送给单个手机次数超过限制
        String respCode = map.get("respCode").toString();
        String code = map.get("code").toString();
        if ("00141".equals(respCode) || "00142".equals(respCode)) {
            return R.error("发送次数过多,稍后再试");
        }
        // 保存到验证码到 redis 有效期两分钟
        redisTemplate.opsForValue().set(phone, code, 2, TimeUnit.MINUTES);
        return R.ok();
    }


    @PostMapping("/register")
    public R register(@RequestBody RegisterUserDTO userDTO) {
        Object redisCode = redisTemplate.opsForValue().get(userDTO.getPhone());
        if (ObjectUtil.isNull(redisCode)) {
            throw new ValidateCodeException("验证码已失效");
        }
        if (!userDTO.getSmsCode().toLowerCase().equals(redisCode)) {
            throw new ValidateCodeException("短信验证码错误");
        }
        return R.ok(userService.register(userDTO));
    }

//    @ApiOperation(value = "用户登录接口", notes = "用户登录")
    @PublicApi(value = "用户登录接口", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "code", value = "验证码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "t", value = "t", required = true, dataType = "String")
    })
    @PostMapping(value = "/login")
    public R login(String username, String password, HttpServletRequest request) {
        String token = request.getParameter("token");
        if (StrUtil.isNotEmpty(token)) return R.ok(token);
        return R.ok(userService.login(username, password));
    }

    @ApiOperation(value = "登录成功后，获取用户的基本信息", notes = "用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public R info() {
        Map<String, Object> map = new HashMap<>();
        // 默认信息
        String name = "Super Admin";
        String avatar = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561394014552&di=17b6c1233048e5276f48309b306c7699&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201804%2F29%2F20180429210111_gtsnf.jpg";
        List<String> list = new ArrayList<>();
        String deptType = "0";
        list.add("admin");
        // 登录用户信息
        String loginUserName = SecurityUtil.getUser().getUsername();
        if (!ObjectUtils.isEmpty(loginUserName)) {
            SysUser sysUser = userService.findByUserInfoName(SecurityUtil.getUser().getUsername());
            name = sysUser.getUsername();
            avatar = ObjectUtils.isNotEmpty(sysUser.getAvatar()) ? sysUser.getAvatar() : avatar;
            deptType = sysUser.getDeptType();
            List<SysRole> roleList = sysUser.getRoleList();
            if (roleList != null && roleList.size() > 0) {
                list = new ArrayList<>();
                for (int i = 0; i < roleList.size(); i++) {
                    list.add(roleList.get(i).getRoleCode());
                }
            }
        }
        map.put("roles", list);
        map.put("avatar", avatar);
        map.put("name", name);
        map.put("deptType", deptType);
        return R.ok(map);
    }

    /**
     * @Description 使用jwt前后分离 只需要前端清除token即可 暂时返回success
     **/
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "success";
    }
}
