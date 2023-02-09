package jp.co.internous.kingdom.model.session;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class LoginSession implements Serializable {
	private static final long serialVersionUID = -4505629762363906244L;
	
	private int userId;
	private int tempId;
	private String userName;
	private String password;
	private boolean isLogin;

	public int getUserId() {
		return this.userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getTempId() {
		return this.tempId;
	}
	
	public void setTempId(int tempId) {
		this.tempId = tempId;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean getIsLogin() {
		return this.isLogin;
	}
	
	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
}
