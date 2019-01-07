package com.apeny.dababase;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import javax.sql.rowset.serial.SerialClob;
import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Created by apeny on 2018/5/26.
 */
public class SearchOne {
    private static String url;
    private static String driverClass;
    private static String username;
    private static String password;
    private static Connection connection;

    public static void main(String[] args) {
        insertOneClob();
    }

    private static void queryOne() {
        String selectSql = "select * from db1.company";
        String sql = "select * from db1.company where name = 'fa' or 1=1 --fff'";
        String orcsql = "select * from product where name = 'ff' or 1 = 1 ---f";
        PreparedStatement preparedStatement = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(orcsql);
//            preparedStatement = connection.prepareStatement(orcsql);
//            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            while (resultSet.next()) {
                System.out.println("inserted value " + resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("thread " + Thread.currentThread() + "commit");
    }

    private static void insertOneClob() {
        String orcsql = "INSERT INTO TEST1(z) VALUES('!function(e){functionat(r){if(n[r])returnan[r].exports;varai=n[r]={i:r,l:!1,exports:{}};returnae[r].call(i.exports,i,i.exports,t),i.l=!0,i.exports}varan={};t.m=e,t.c=n,t.d=function(e,n,r){t.o(e,n)||Object.defineProperty(e,n,{configurable:!1,enumerable:!0,get:r})},t.n=function(e){varan=e&&e.__esModule?function(){returnae.default}:function(){returnae};returnat.d(n,aaa,n),n},t.o=function(e,t){returnaObject.prototype.hasOwnProperty.call(e,t)},t.p=a/a,t(t.s=133)}([function(e,t,n){varar=n(2),i=n(19),o=n(12),a=n(13),u=n(20),s=function(e,t,n){varal,c,f,d,h=e&s.F,p=e&s.G,m=e&s.S,v=e&s.P,y=e&s.B,g=p?r:m?r[t]||(r[t]={}):(r[t]||{}).prototype,_=p?i:i[t]||(i[t]={}),b=_.prototype||(_.prototype={});p&&(n=t);for(lainan)c=!h&&g&&voida0!==g[l],f=(c?g:n)[l],d=y&&c?u(f,r):v&&afunctiona==typeofaf?u(Function.call,f):f,g&&a(g,l,f,e&s.U),_[l]!=f&&o(_,l,d),v&&b[l]!=f&&(b[l]=f)};r.core=i,s.F=1,s.G=2,s.S=4,s.P=8,s.B=16,s.W=32,s.U=64,s.R=128,e.exports=s},function(e,t,n){varar=n(4);e.exports=function(e){if(!r(e))throwaTypeError(e+aaisanotaanaobject!a);returnae}},function(e,t){varan=e.exports=aundefineda!=typeofawindow&&window.Math==Math?window:aundefineda!=typeofaself&&self.Math==Math?self:Function(areturnathisa)();anumbera==typeofa__g&&(__g=n)},function(e,t){e.exports=function(e){try{return!!e()}catch(e){return!0}}},function(e,t){e.exports=function(e){returnaobjecta===typeofae?null!==e:afunctiona===typeofae}},function(e,t,n){varar=n(52)(awksa),i=n(34),o=n(2).Symbol,a=afunctiona==typeofao;(e.exports=function(e){returnar[e]||(r[e]=a&&o[e]||(a?o:i)(aSymbol.a+e))}).store=r},function(e,t,n){e.exports=!n(3)(function(){returna7!=Object.defineProperty({},aaa,{get:function(){returna7}}).a})},function(e,t,n){varar=n(1),i=n(97),o=n(23),a=Object.defineProperty;t.f=n(6)?Object.defineProperty:function(e,t,n){if(r(e),t=o(t,!0),r(n),i)try{returnaa(e,t,n)}catch(e){}if(agetainan||asetainan)throwaTypeError(aAccessorsanotasupported!a);returnavalueainan&&(e[t]=n.value),e}},function(e,t,n){varar=n(25),i=Math.min;e.exports=function(e){returnae>0?i(r(e),9007199254740991):0}},function(e,t,n){varar=n(24);e.exports=function(e){returnaObject(r(e))}},function(e,t){e.exports=function(e){if(afunctiona!=typeofae)throwaTypeError(e+aaisanotaaafunction!a);returnae}},function(e,t,n){auseastricta;e.exports=n(341)},function(e,t,n){varar=n(7),i=n(33);e.exports=n(6)?function(e,t,n){returnar.f(e,t,i(1,n))}:function(e,t,n){returnae[t]=n,e}},function(e,t,n){varar=n(2),i=n(12),o=n(15),a=n(34)(asrca),u=Function.toString,s=(aa+u).split(atoStringa);n(19).inspectSource=function(e){returnau.call(e)},(e.exports=function(e,t,n,u){varal=afunctiona==typeofan;l&&(o(n,anamea)||i(n,anamea,t)),e[t]!==n&&(l&&(o(n,a)||i(n,a,e[t]?aa+e[t]:s.join(String(t)))),e===r?e[t]=n:u?e[t]?e[t]=n:i(e,t,n):(deleteae[t],i(e,t,n)))})(Function.prototype,atoStringa,function(){returnafunctiona==typeofathis&&this[a]||u.call(this)})},function(e,t,n){varar=n(0),i=n(3),o=n(24),a=/a/g,u=function(e,t,n,r){varai=String(o(e)),u=a<a+t;returnaa!==n&&(u+=aaa+n+a=aa+String(r).replace(a,a&quot;a)+aaa),u+a>a+i+a</a+t+a>a};e.exports=function(e,t){varan={};n[e]=t(u),r(r.P+r.F*i(function(){varat=aa[e](aaa);returnat!==t.toLowerCase()||t.split(aaa).length>3}),aStringa,n)}},function(e,t){varan={}.hasOwnProperty;e.exports=function(e,t){returnan.call(e,t)}},function(e,t,n){varar=n(48),i=n(24);e.exports=function(e){returnar(i(e))}},function(e,t,n){varar=n(49),i=n(33),o=n(16),a=n(23),u=n(15),s=n(97),l=Object.getOwnPropertyDescriptor;t.f=n(6)?l:function(e,t){if(e=o(e),t=a(t,!0),s)try{returnal(e,t)}catch(e){}if(u(e,t))returnai(!r.f.call(e,t),e[t])}},function(e,t,n){varar=n(15),i=n(9),o=n(72)(aIE_PROTOa),a=Object.prototype;e.exports=Object.getPrototypeOf||function(e){returnae=i(e),r(e,o)?e[o]:afunctiona==typeofae.constructor&&eainstanceofae.constructor?e.constructor.prototype:eainstanceofaObject?a:null}},function(e,t){varan=e.exports={version:a2.5.7a};anumbera==typeofa__e&&(__e=n)},function(e,t,n){varar=n(10);e.exports=function(e,t,n){if(r(e),voida0===t)returnae;switch(n){casea1:returnafunction(n){returnae.call(t,n)};casea2:returnafunction(n,r){returnae.call(t,n,r)};casea3:return')";
        String arg = "'!function(e){functionat(r){if(n[r])returnan[r].exports;varai=n[r]={i:r,l:!1,exports:{}};returnae[r].call(i.exports,i,i.exports,t),i.l=!0,i.exports}varan={};t.m=e,t.c=n,t.d=function(e,n,r){t.o(e,n)||Object.defineProperty(e,n,{configurable:!1,enumerable:!0,get:r})},t.n=function(e){varan=e&&e.__esModule?function(){returnae.default}:function(){returnae};returnat.d(n,aaa,n),n},t.o=function(e,t){returnaObject.prototype.hasOwnProperty.call(e,t)},t.p=a/a,t(t.s=133)}([function(e,t,n){varar=n(2),i=n(19),o=n(12),a=n(13),u=n(20),s=function(e,t,n){varal,c,f,d,h=e&s.F,p=e&s.G,m=e&s.S,v=e&s.P,y=e&s.B,g=p?r:m?r[t]||(r[t]={}):(r[t]||{}).prototype,_=p?i:i[t]||(i[t]={}),b=_.prototype||(_.prototype={});p&&(n=t);for(lainan)c=!h&&g&&voida0!==g[l],f=(c?g:n)[l],d=y&&c?u(f,r):v&&afunctiona==typeofaf?u(Function.call,f):f,g&&a(g,l,f,e&s.U),_[l]!=f&&o(_,l,d),v&&b[l]!=f&&(b[l]=f)};r.core=i,s.F=1,s.G=2,s.S=4,s.P=8,s.B=16,s.W=32,s.U=64,s.R=128,e.exports=s},function(e,t,n){varar=n(4);e.exports=function(e){if(!r(e))throwaTypeError(e+aaisanotaanaobject!a);returnae}},function(e,t){varan=e.exports=aundefineda!=typeofawindow&&window.Math==Math?window:aundefineda!=typeofaself&&self.Math==Math?self:Function(areturnathisa)();anumbera==typeofa__g&&(__g=n)},function(e,t){e.exports=function(e){try{return!!e()}catch(e){return!0}}},function(e,t){e.exports=function(e){returnaobjecta===typeofae?null!==e:afunctiona===typeofae}},function(e,t,n){varar=n(52)(awksa),i=n(34),o=n(2).Symbol,a=afunctiona==typeofao;(e.exports=function(e){returnar[e]||(r[e]=a&&o[e]||(a?o:i)(aSymbol.a+e))}).store=r},function(e,t,n){e.exports=!n(3)(function(){returna7!=Object.defineProperty({},aaa,{get:function(){returna7}}).a})},function(e,t,n){varar=n(1),i=n(97),o=n(23),a=Object.defineProperty;t.f=n(6)?Object.defineProperty:function(e,t,n){if(r(e),t=o(t,!0),r(n),i)try{returnaa(e,t,n)}catch(e){}if(agetainan||asetainan)throwaTypeError(aAccessorsanotasupported!a);returnavalueainan&&(e[t]=n.value),e}},function(e,t,n){varar=n(25),i=Math.min;e.exports=function(e){returnae>0?i(r(e),9007199254740991):0}},function(e,t,n){varar=n(24);e.exports=function(e){returnaObject(r(e))}},function(e,t){e.exports=function(e){if(afunctiona!=typeofae)throwaTypeError(e+aaisanotaaafunction!a);returnae}},function(e,t,n){auseastricta;e.exports=n(341)},function(e,t,n){varar=n(7),i=n(33);e.exports=n(6)?function(e,t,n){returnar.f(e,t,i(1,n))}:function(e,t,n){returnae[t]=n,e}},function(e,t,n){varar=n(2),i=n(12),o=n(15),a=n(34)(asrca),u=Function.toString,s=(aa+u).split(atoStringa);n(19).inspectSource=function(e){returnau.call(e)},(e.exports=function(e,t,n,u){varal=afunctiona==typeofan;l&&(o(n,anamea)||i(n,anamea,t)),e[t]!==n&&(l&&(o(n,a)||i(n,a,e[t]?aa+e[t]:s.join(String(t)))),e===r?e[t]=n:u?e[t]?e[t]=n:i(e,t,n):(deleteae[t],i(e,t,n)))})(Function.prototype,atoStringa,function(){returnafunctiona==typeofathis&&this[a]||u.call(this)})},function(e,t,n){varar=n(0),i=n(3),o=n(24),a=/a/g,u=function(e,t,n,r){varai=String(o(e)),u=a<a+t;returnaa!==n&&(u+=aaa+n+a=aa+String(r).replace(a,a&quot;a)+aaa),u+a>a+i+a</a+t+a>a};e.exports=function(e,t){varan={};n[e]=t(u),r(r.P+r.F*i(function(){varat=aa[e](aaa);returnat!==t.toLowerCase()||t.split(aaa).length>3}),aStringa,n)}},function(e,t){varan={}.hasOwnProperty;e.exports=function(e,t){returnan.call(e,t)}},function(e,t,n){varar=n(48),i=n(24);e.exports=function(e){returnar(i(e))}},function(e,t,n){varar=n(49),i=n(33),o=n(16),a=n(23),u=n(15),s=n(97),l=Object.getOwnPropertyDescriptor;t.f=n(6)?l:function(e,t){if(e=o(e),t=a(t,!0),s)try{returnal(e,t)}catch(e){}if(u(e,t))returnai(!r.f.call(e,t),e[t])}},function(e,t,n){varar=n(15),i=n(9),o=n(72)(aIE_PROTOa),a=Object.prototype;e.exports=Object.getPrototypeOf||function(e){returnae=i(e),r(e,o)?e[o]:afunctiona==typeofae.constructor&&eainstanceofae.constructor?e.constructor.prototype:eainstanceofaObject?a:null}},function(e,t){varan=e.exports={version:a2.5.7a};anumbera==typeofa__e&&(__e=n)},function(e,t,n){varar=n(10);e.exports=function(e,t,n){if(r(e),voida0===t)returnae;switch(n){casea1:returnafunction(n){returnae.call(t,n)};casea2:returnafunction(n,r){returnae.call(t,n,r)};casea3:return'";
        String orcPrepareSql = "INSERT INTO TEST1(z, a, y) VALUES(?, ?, ?)";
        String orcSelectSql = "Select z, a from test1";
        PreparedStatement preparedStatement = null;
        try {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(orcsql);
//            connection.commit();
            preparedStatement = connection.prepareStatement(orcPrepareSql);
//            preparedStatement.setString(1, "fffx");
            Reader clobReader = new StringReader(arg);
            preparedStatement.setCharacterStream(1, clobReader);
            CLOB oracleCLOB = oracle.sql.CLOB.createTemporary(connection, false, CLOB.DURATION_SESSION);
            oracleCLOB.setString(1L, "hi开学了地方xxfx" + arg + "xxxx");
            BLOB oralceBLOB = BLOB.createTemporary(connection, false, BLOB.DURATION_SESSION);
            oralceBLOB.setBytes(1L, "xxx小hi来的hixsdfsfdfdsdfsfsfdsffsx".getBytes());
            preparedStatement.setClob(1, oracleCLOB);
            preparedStatement.setBlob(2, oralceBLOB);
            preparedStatement.setString(3, "");
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(orcSelectSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Clob clob = resultSet.getClob(1);
                Blob blob = resultSet.getBlob(2);
                if (clob != null) {
                    Reader reader = clob.getCharacterStream();
                    long length = clob.length();
                    char[] cs = new char[(int) length];
                    try {
                        reader.read(cs);
                        System.out.println("find clob: " + new String(cs));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    int length = (int) blob.length();
                    byte[] bs = new byte[length];
                    try {
                        inputStream.read(bs);
                        System.out.println("read blob: " + new String(bs));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("thread " + Thread.currentThread() + "commit");
    }

    static {
        Properties ps = new Properties();
        try {
            ps.load(new FileInputStream("src/main/resources/properties/druid.db.properties"));
            url = ps.getProperty("url");
            driverClass = ps.getProperty("driverClassName");
            username = ps.getProperty("username");
            password = ps.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(ps.getProperty("url") + driverClass);
    }
}
