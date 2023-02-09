package jp.co.internous.kingdom.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import jp.co.internous.kingdom.model.domain.MstUser;
import jp.co.internous.kingdom.model.form.UserForm;
import jp.co.internous.kingdom.model.mapper.MstUserMapper;
import jp.co.internous.kingdom.model.mapper.TblCartMapper;
import jp.co.internous.kingdom.model.session.LoginSession;

@RestController
@RequestMapping("/kingdom/auth")
public class AuthController {
	
	private Gson gson = new  Gson();
	
	@Autowired
	private LoginSession loginSession;
	@Autowired
	private MstUserMapper userMapper;
	@Autowired
	private TblCartMapper cartMapper;

	@PostMapping("/login")
	public String login(@RequestBody UserForm form) {
		MstUser user = userMapper.findByUserNameAndPassword(form.getUserName(), form.getPassword());
		if(user != null) {
			int tempId = loginSession.getTempId();
			int count = cartMapper.findCountByUserId(tempId);
			if(tempId != 0 && count > 0) {
				cartMapper.updateUserId(user.getId(), tempId);
			} 
			loginSession.setUserId(user.getId());
			loginSession.setTempId(0);		
			loginSession.setUserName(user.getUserName());
			loginSession.setPassword(user.getPassword());
			loginSession.setIsLogin(true);
		} else {
			loginSession.setUserId(0);
			loginSession.setUserName(null);
			loginSession.setPassword(null);
			loginSession.setIsLogin(false);
		}
		return gson.toJson(user);
	}
	@PostMapping("/logout")
	public String logout() {
		loginSession.setUserId(0);
		loginSession.setTempId(0);
		loginSession.setUserName(null);
		loginSession.setPassword(null);
		loginSession.setIsLogin(false);
		return "";
	}
		
	@RequestMapping("/resetPassword")
	public String resetPassword(@RequestBody UserForm f) {
		String newPassword = f.getNewPassword();
		String newPasswordConfirm = f.getNewPasswordConfirm();
			
		MstUser user = userMapper.findByUserNameAndPassword(f.getUserName(), f.getPassword());
		if (user == null) {
			return "現在のパスワードが正しくありません。";
		}
			
		if (user.getPassword().equals(newPassword)) {
			return "現在のパスワードと同一文字列が入力されました。";
		}
			
		if (!newPassword.equals(newPasswordConfirm)) {
			return "新パスワードと確認用パスワードが一致しません。";
		}

		userMapper.updatePassword(user.getUserName(), f.getNewPassword());
		loginSession.setPassword(f.getNewPassword());
			
		return "パスワードが再設定されました。";
	}	
}
