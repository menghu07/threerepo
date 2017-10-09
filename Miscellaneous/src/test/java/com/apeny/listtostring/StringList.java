package com.apeny.listtostring;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Created by apeny on 2017年09月25日.
 */
public class StringList {
    public static void main(String[] args) {
        fuzzySearch();
        List<String> list = new ArrayList<String>();
        list.add("item1");
        list.add("item2");
        list.add("item3");
        String ff = "${yu}";
//        System.out.println("list.toString() : " + list.toString().substring(1, list.toString().length() - 1));
        for (int i = 0; i < getInt(); i++) {
//            System.out.println("list......");
        }

    }

    public static int getInt() {
//        System.out.println("int i");
        return 4;
    }

    public static void fuzzySearch() {
        //使用正则模糊匹配
        String reg = ".+from\\s+(([^,]+)(,(.+))?)";
        String regSimple = ".+from\\s+(.+)\\s+(where\\s+.+)?";
        Pattern pattern = Pattern.compile(reg, CASE_INSENSITIVE);
        String sql = "select t.* FROM mainTable t, subTable t1 where t.templateID=t1.templateID and t1.institutionID=${InstitutionID}";
        String other = "select * from tt8 t8, tt9 t9 where t8.i = t9.i";
        String nowhere = "select * from tt8";
        String nowhere8 = "select * from tt8 t8";
        Matcher matcher = pattern.matcher(nowhere);
        //
        String deleteSQL = "delete from mainTable t where exists(select 1 from subTable t1 where t.templateID=t1.templateID and t1.institutionID=${InstitutionID})";

        System.out.println("groupCount: " + matcher.groupCount());
        if (matcher.find()) {
            String mainTable = matcher.group(2);
            if (mainTable == null) {
                return;
            }
            String subSQL = matcher.group(4);
            System.out.println(" mainTable: " + mainTable);
            System.out.println("find### group1 : " + matcher.group(1));
            System.out.println("find### group2 : " + matcher.group(4));
            String correlation = "";
            if (subSQL != null) {
                correlation = " where exists(select 1 from " + matcher.group(4) + ")";
            }

            //有子表用子查询，没有子表不用子查询
            String startSQL = "delete from " + mainTable + correlation;
            System.out.println(startSQL);
        }
    }
}
