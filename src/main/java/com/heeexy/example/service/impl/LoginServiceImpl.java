package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.LoginDao;
import com.heeexy.example.service.LoginService;
import com.heeexy.example.service.PermissionService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.RSACrypt;
import com.heeexy.example.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zandaoguang
 * @description: 登录service实现类
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDao loginDao;
	@Autowired
	private PermissionService permissionService;

	/**
	 * 登录表单提交
	 */
	@Override
	public JSONObject authLogin(JSONObject jsonObject) throws Exception {
		String username = jsonObject.getString("username");
		String password = jsonObject.getString("password");
		System.out.println("password："+password);
		// 在此处进行RSA私钥解密
		String privateKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCd1i6VlLp6ymqQRHBokABNu8ZbZ+9p5GXGPDWWX1/s4cdPYyozpmcmJOfe0aHwUZMToIxzcjvxP+DeCla/+l/g9hSZRw9eMp/7cRpZ4IlDyi8vCMFlsga2XVsVfcFDM0VMdvJmWSlQL0tzHT6fsM2aj+QRbNp72UUUu/1YgA3TiiNhyYo7YNUVLEeHr84Mgw8hHAW4NQ4Ty+RlXU9fv9HjxewgfsUlN8gGFAHnsyijX27Ye7o/IkjcDAAElCmwrIjSO0nSvQxfehmWRBv7lcIuDlVlMYYSdtIidL39Cj6ixRCIkxNu277227pcPVYAdPBBj4KENrQZ8ga8BVeNCLx9AgMBAAECggEBAIgcX+fWbkndrK/klRtWsyxVteS1aJzkvW+KFJwse0iIG5y2Y3pPEbyvLtXT6daYQkE2vmgAJvkQoRZZzB2aCHyzOWhFA1e0SIpJKvQ+0aCIBtCEuGK+d1NkhluuKq+M00HxgXAJi7r+Tg5jNdH4YhXb6E2ulWsgHpaW1w8uvr2gkrGZVr1iprJCF83cFwKiYsFi9TP8voov2J7HMcRua7gUqz8WC8KtfuCkijg9bIELT7nliCszziqBZ3I71+PHn+tphym/jEQJcs4Mx9TxHYv/Ox+VuRm9tG/T62lx1xeJ0od4IWSYqvC5jpp8/s0oCd7SgGrfjnlGlQBWuKY9KQECgYEA2hko7YN0UbrYNW/pchXjvtguwxzwuQEdW559XLN336qGVx9aJym5WEXfNR804sfrQlm2Icu6x6xSLjiZFZMSUmr0D0+kFnNmUcB2S+QQCGoHozZoORcjrsucfkbK1FkRuKji/SVzLkq8dgNo/p8P4BT+KADLeiFQOc5TBwjVRQ0CgYEAuUQUVc6xX4KDIkXsfssc43JNYvitrnUqILV7S7XUKqb5TWsYgDplhCSRl12Td+0jeCdX1z9CNcQDAq7QErjh5EaDURwGlD7tNgSEGOKhIMiygIrvPQDf3+41Dn7DLCJg8qgRvkfXRdqKR4kiyivSpl2dFHEeeCIHU+Zp/4DJWTECgYBN5g6oRvuU9Zej/oTzKr5du/l31y5j5fIGd2VvZuq6CL5S/+/DbTO2Q76lyq+pEJ8G4+QZzhq4luQDflOvUQiKR4lErr19B1rUeFnIYX0YhEaWFSjEu28TT8ZllAN+NRIPUsNAh8/MCQWnYTBvQYD6GIVu5FnJFX0CKJ+fM5h5QQKBgHQmAVW2aq9FRG81RUpMjoTtwSR+YWzRWaL+BBl4GlbPLdbigI7LjZ9lyb0K1oxGKImIqOJyb1ED3RtOe3ZxYHVb27H7Dwjb1p2aATMUyg2ipX4/HDOzm6dpUOwQfs3pk7wtsEoKAiVyGGU2zY/QH5VbdUI+lOqd8tgfyW580D7RAoGBAKX9JPy2RKeED4xsdr3qPHL+FOr5FBmY2GFd1DOUXgShQHwG2lPqjEFXsBomBuzfKjcLMLKDNhlsHa/76m1gVbSnpyCtwYT3T3ZHzdwDl65HSXZ2dclFwHoszHo+L5w9bvvtO+PFDZDKo4Lm9ZfFJh2PZ+Usv1Sn+pTxoUvrYr86";
		//消息接收方
		username = RSACrypt.decrypt(RSACrypt.loadPrivateKey(privateKeyStr),RSACrypt.strToBase64(username));
		password = RSACrypt.decrypt(RSACrypt.loadPrivateKey(privateKeyStr),RSACrypt.strToBase64(password));

		System.out.println("username:"+username+"\n password:"+password);

		JSONObject info = new JSONObject();
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {

			currentUser.login(token);
			info.put("result", "success");
		} catch (AuthenticationException e) {
			info.put("result", "fail");
		}
		System.out.println("info:"+info);
		return CommonUtil.successJson(info);
	}

	/**
	 * 根据用户名和密码查询对应的用户
	 */
	@Override
	public JSONObject getUser(String username, String password) {
		return loginDao.getUser(username, password);
	}

	/**
	 * 查询当前登录用户的权限等信息
	 */
	@Override
	public JSONObject getInfo() {
		//从session获取用户信息
		Session session = SecurityUtils.getSubject().getSession();
		JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
		try{
			String username = userInfo.getString("username");
			JSONObject info = new JSONObject();
			JSONObject userPermission = permissionService.getUserPermission(username);
			session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
			info.put("userPermission", userPermission);
			return CommonUtil.successJson(info);

		}catch(Exception e){
			return CommonUtil.successJson("请关闭浏览器重新登陆！");
		}


	}

	/**
	 * 退出登录
	 */
	@Override
	public JSONObject logout() {
		try {
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
		} catch (Exception e) {
		}
		return CommonUtil.successJson();
	}
}
