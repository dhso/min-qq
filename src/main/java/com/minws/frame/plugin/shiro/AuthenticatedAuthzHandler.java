package com.minws.frame.plugin.shiro;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;

/**
 * 已认证通过访问控制处理器
 * 单例模式运行。
 *
 * @author dafei
 *
 */
class AuthenticatedAuthzHandler extends AbstractAuthzHandler {

	private static AuthenticatedAuthzHandler aah = new AuthenticatedAuthzHandler();

	private AuthenticatedAuthzHandler(){}

	public static  AuthenticatedAuthzHandler me(){
		return aah;
	}

	@Override
    public void assertAuthorized() throws AuthorizationException {
		if (!getSubject().isAuthenticated() ) {
            throw new UnauthenticatedException( "The current Subject is not authenticated.  Access denied." );
        }
	}
}
