package com.apeny.servletjsp.bean;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monis on 2018/10/15.
 */
@Component("apyAccessRealm")
public class ApyAccessRealm extends AuthorizingRealm {
    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();
        List<String> permissionList=new ArrayList<String>();
        permissionList.add("user:add");
        permissionList.add("user:delete");
        if (userName.equals("ahu")) {
            permissionList.add("user:query");
        }
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionList);
        info.addRole("admin");
        return info;
    }

    /**
     * 验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        if ("".equals(userName)) {
            return  null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName,"123456",this.getName());
        return info;
    }
}
