package com.apeny.mybatisproject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.apeny.mybatisproject.domain.Blog;
import com.apeny.mybatisproject.mapper.BlogMapper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	System.out.println("test app ?");
        assertTrue( true );
    }
    
    public void testMybatis() {
    	System.out.println("mybatis is called....");
    	InputStream in;
		try {
			in = Resources.getResourceAsStream("META-INF/spring/mybatis-config.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
			SqlSession session = factory.openSession();
			BlogMapper mapper = session.getMapper(BlogMapper.class);
			Blog blog = mapper.selectBlog(102);
			System.out.println("blog digest 111 222 333 444 555" + blog.getDigest() + blog + "fff");
			System.out.println("branch code is added");
			System.out.println(session.getConfiguration().getParameterMapNames());
			System.out.println("session. one edit test merge let we see no-fastfoward no-ff" + session.getConnection().getSchema());
			System.out.println("add one line to dev dev different adds");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
