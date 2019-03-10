package com.apeny.servletjsp.applicationstarter;

import com.apeny.servletjsp.bean.shardingsphere.targetimpl.BeforePeriodPreciseShardingAlgorithm;
import com.apeny.servletjsp.bean.shardingsphere.targetimpl.BeforePeriodRangeShardingAlgorithm;
import com.apeny.servletjsp.bean.shardingsphere.targetimpl.NeighborPreciseShardingAlgorithm;
import com.apeny.servletjsp.bean.shardingsphere.targetimpl.NeighborRangeShardingAlgorithm;
import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.core.rule.TableRule;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by monis on 2019/1/27.
 */
public class AlgorithmTest {

    private static final List<String> AVAILABLE_TABLES = new ArrayList<>();

    private static final List<String> BEFORE_AVAILABLE_TABLES = new ArrayList<>();

    static {
        AVAILABLE_TABLES.add("t_shardingx");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx");
        for (int i = 1; i <= 12; i++) {
            for (int date = 0; date <= 1; date++) {
                String table = "t_shardingx18";
                String month = i < 10 ? ("0" + i) : "" + i;
                String tableDate = "0" + date;
                AVAILABLE_TABLES.add(table + month + tableDate);
                BEFORE_AVAILABLE_TABLES.add(table + month + tableDate);
            }
        }
        AVAILABLE_TABLES.add("t_shardingxlast");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190100");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190101");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190200");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190201");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190300");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190301");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190400");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190401");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190500");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190501");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190600");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190601");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190700");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190701");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190800");
        BEFORE_AVAILABLE_TABLES.add("t_shardingx190801");
        BEFORE_AVAILABLE_TABLES.add("t_shardingxlast");
    }

    /**
     * neighbor test
     */
    @Test
    public void testNeighborPreciseAlgorithmOne() {
        NeighborPreciseShardingAlgorithm neighborPreciseShardingAlgorithm = new NeighborPreciseShardingAlgorithm();
        PreciseShardingValue<String> preciseShardingValue = new PreciseShardingValue<>("t_shardingx", "SourceTxNo", "1712011212121234567890123");
        Collection<String> hi = neighborPreciseShardingAlgorithm.doSharding(AVAILABLE_TABLES, preciseShardingValue);
        System.out.println("hi = " + hi);
        Assert.assertTrue(Arrays.asList("t_shardingx", "t_shardingx180100").equals(hi));
    }

    @Test
    public void testNeighborPreciseAlgorithmOne2() {
        NeighborPreciseShardingAlgorithm neighborPreciseShardingAlgorithm = new NeighborPreciseShardingAlgorithm();
        PreciseShardingValue<String> preciseShardingValue = new PreciseShardingValue<>("t_shardingx", "SourceTxNo", "1812011212121234567890123");
        Collection<String> hi = neighborPreciseShardingAlgorithm.doSharding(AVAILABLE_TABLES, preciseShardingValue);
        System.out.println("hi = " + hi);
        Assert.assertTrue(Arrays.asList("t_shardingx181200", "t_shardingx181201", "t_shardingx181101").equals(hi));
    }

    @Test
    public void testNeighborPreciseAlgorithmOne3() {
        NeighborPreciseShardingAlgorithm neighborPreciseShardingAlgorithm = new NeighborPreciseShardingAlgorithm();
        PreciseShardingValue<String> preciseShardingValue = new PreciseShardingValue<>("t_shardingx", "SourceTxNo", "1812311212121234567890123");
        Collection<String> hi = neighborPreciseShardingAlgorithm.doSharding(AVAILABLE_TABLES, preciseShardingValue);
        System.out.println("hi = " + hi);
        Assert.assertTrue(Arrays.asList("t_shardingx181201", "t_shardingxlast", "t_shardingx181200").equals(hi));
    }

    @Test
    public void testNeighborPreciseAlgorithmOne4() {
        NeighborPreciseShardingAlgorithm neighborPreciseShardingAlgorithm = new NeighborPreciseShardingAlgorithm();
        PreciseShardingValue<String> preciseShardingValue = new PreciseShardingValue<>("t_shardingx", "SourceTxNo", "1912311212121234567890123");
        Collection<String> hi = neighborPreciseShardingAlgorithm.doSharding(AVAILABLE_TABLES, preciseShardingValue);
        System.out.println("hi = " + hi);
        Assert.assertTrue(Arrays.asList("t_shardingxlast", "t_shardingx181201").equals(hi));
    }

    //< <=
    @Test
    public void testNeighborRangeAlgorithmOne1() {
        NeighborRangeShardingAlgorithm neighborRangeShardingAlgorithm = new NeighborRangeShardingAlgorithm();
        Range range = Range.lessThan("1710311212121234567890123");
        range = Range.atMost("1710311212121234567890123");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "SourceTxNO", range);
        Collection<String> hi = neighborRangeShardingAlgorithm.doSharding(AVAILABLE_TABLES, begin, null);
        System.out.println("hi = " + hi);
        Assert.assertTrue(Arrays.asList("t_shardingx", "t_shardingx180100").equals(hi));
    }

    @Test
    public void testNeighborRangeAlgorithmOne2() {
        NeighborRangeShardingAlgorithm neighborRangeShardingAlgorithm = new NeighborRangeShardingAlgorithm();
        Range range = Range.lessThan("1802281212121234567890123");
        range = Range.atMost("1802281212121234567890123");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "SourceTxNO", range);
        Collection<String> hi = neighborRangeShardingAlgorithm.doSharding(AVAILABLE_TABLES, begin, null);
        System.out.println("hi = " + hi);
        Assert.assertTrue(Arrays.asList("t_shardingx", "t_shardingx180100", "t_shardingx180101", "t_shardingx180200", "t_shardingx180201", "t_shardingx180300").equals(hi));
    }

    @Test
    public void testNeighborRangeAlgorithmOne3() {
        NeighborRangeShardingAlgorithm neighborRangeShardingAlgorithm = new NeighborRangeShardingAlgorithm();
        Range range = Range.lessThan("1812011212121234567890123");
        range = Range.atMost("1812011212121234567890123");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "SourceTxNO", range);
        Collection<String> hi = neighborRangeShardingAlgorithm.doSharding(AVAILABLE_TABLES, begin, null);
        System.out.println("hi = " + hi);
        AVAILABLE_TABLES.remove(AVAILABLE_TABLES.size() - 1);
        Assert.assertTrue((AVAILABLE_TABLES).equals(hi));
    }

    @Test
    public void testNeighborRangeAlgorithmOne4() {
        NeighborRangeShardingAlgorithm neighborRangeShardingAlgorithm = new NeighborRangeShardingAlgorithm();
        Range range = Range.lessThan("1912011212121234567890123");
//        range = Range.atMost("1912011212121234567890123");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "SourceTxNO", range);
        Collection<String> hi = neighborRangeShardingAlgorithm.doSharding(AVAILABLE_TABLES, begin, null);
        System.out.println("hi = " + hi);
        Assert.assertTrue((AVAILABLE_TABLES).equals(hi));
    }

    //> >=
    @Test
    public void testNeighborRangeAlgorithmTwo1() {
        NeighborRangeShardingAlgorithm neighborRangeShardingAlgorithm = new NeighborRangeShardingAlgorithm();
        Range range = Range.greaterThan("1710311212121234567890123");
        range = Range.atLeast("1710311212121234567890123");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "SourceTxNO", range);
        Collection<String> hi = neighborRangeShardingAlgorithm.doSharding(AVAILABLE_TABLES, begin, null);
        System.out.println("hi = " + hi);
        Assert.assertTrue(AVAILABLE_TABLES.equals(hi));
    }

    @Test
    public void testNeighborRangeAlgorithmTwo2() {
        NeighborRangeShardingAlgorithm neighborRangeShardingAlgorithm = new NeighborRangeShardingAlgorithm();
        Range range = Range.greaterThan("1802281212121234567890123");
        range = Range.atLeast("1802281212121234567890123");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "SourceTxNO", range);
        Collection<String> hi = neighborRangeShardingAlgorithm.doSharding(AVAILABLE_TABLES, begin, null);
        System.out.println("hi = " + hi);
        //t_shardingx
        AVAILABLE_TABLES.remove(0);
        //t_shardingx180100
        AVAILABLE_TABLES.remove(0);
        //t_shardingx180101
        AVAILABLE_TABLES.remove(0);
        Assert.assertTrue(AVAILABLE_TABLES.equals(hi));
    }

    @Test
    public void testNeighborRangeAlgorithmTwo3() {
        NeighborRangeShardingAlgorithm neighborRangeShardingAlgorithm = new NeighborRangeShardingAlgorithm();
        Range range = Range.greaterThan("1812011212121234567890123");
        range = Range.atLeast("1812011212121234567890123");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "SourceTxNO", range);
        Collection<String> hi = neighborRangeShardingAlgorithm.doSharding(AVAILABLE_TABLES, begin, null);
        System.out.println("hi = " + hi);
        AVAILABLE_TABLES.remove(AVAILABLE_TABLES.size() - 1);
        Assert.assertTrue((Arrays.asList("t_shardingx181101", "t_shardingx181200", "t_shardingx181201", "t_shardingxlast")).equals(hi));
    }

    @Test
    public void testNeighborRangeAlgorithmTwo4() {
        NeighborRangeShardingAlgorithm neighborRangeShardingAlgorithm = new NeighborRangeShardingAlgorithm();
        Range range = Range.greaterThan("1912011212121234567890123");
        range = Range.atLeast("1912011212121234567890123");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "SourceTxNO", range);
        Collection<String> hi = neighborRangeShardingAlgorithm.doSharding(AVAILABLE_TABLES, begin, null);
        System.out.println("hi = " + hi);
        Assert.assertTrue((Arrays.asList("t_shardingx181201", "t_shardingxlast")).equals(hi));
    }

    //> >= AND < <=
    @Test
    public void testNeighborRangeAlgorithmThree1() {
        NeighborRangeShardingAlgorithm neighborRangeShardingAlgorithm = new NeighborRangeShardingAlgorithm();
        Range range = Range.range("1710311212121234567890123", BoundType.OPEN, "1802051212121234567890123", BoundType.OPEN);
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "SourceTxNO", range);
        Collection<String> hi = neighborRangeShardingAlgorithm.doSharding(AVAILABLE_TABLES, begin, null);
        System.out.println("hi = " + hi);
        Assert.assertTrue(Arrays.asList("t_shardingx", "t_shardingx180100", "t_shardingx180101", "t_shardingx180200", "t_shardingx180201").equals(hi));
    }

    @Test
    public void testNeighborRangeAlgorithmThree2() {
        NeighborRangeShardingAlgorithm neighborRangeShardingAlgorithm = new NeighborRangeShardingAlgorithm();
        Range range = Range.range("1801011212121234567890123", BoundType.CLOSED, "1802051212121234567890123", BoundType.CLOSED);
//        range = Range.atLeast("1802281212121234567890123");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "SourceTxNO", range);
        Collection<String> hi = neighborRangeShardingAlgorithm.doSharding(AVAILABLE_TABLES, begin, null);
        System.out.println("hi = " + hi);
        Assert.assertTrue(Arrays.asList("t_shardingx", "t_shardingx180100", "t_shardingx180101",
                "t_shardingx180200", "t_shardingx180201").equals(hi));
    }

    @Test
    public void testNeighborRangeAlgorithmThree3() {
        NeighborRangeShardingAlgorithm neighborRangeShardingAlgorithm = new NeighborRangeShardingAlgorithm();
        Range range = Range.range("1811011212121234567890123", BoundType.CLOSED, "1902051212121234567890123", BoundType.CLOSED);
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "SourceTxNO", range);
        Collection<String> hi = neighborRangeShardingAlgorithm.doSharding(AVAILABLE_TABLES, begin, null);
        System.out.println("hi = " + hi);
        AVAILABLE_TABLES.remove(AVAILABLE_TABLES.size() - 1);
        Assert.assertTrue((Arrays.asList("t_shardingx181001", "t_shardingx181100", "t_shardingx181101", "t_shardingx181200", "t_shardingx181201", "t_shardingxlast")).equals(hi));
    }

    @Test
    public void testNeighborRangeAlgorithmThree4() {
        NeighborRangeShardingAlgorithm neighborRangeShardingAlgorithm = new NeighborRangeShardingAlgorithm();
        Range range = Range.range("1901211212121234567890123", BoundType.CLOSED, "1902051212121234567890123", BoundType.CLOSED);
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "SourceTxNO", range);
        Collection<String> hi = neighborRangeShardingAlgorithm.doSharding(AVAILABLE_TABLES, begin, null);
        System.out.println("hi = " + hi);
        Assert.assertTrue((Arrays.asList("t_shardingx181201", "t_shardingxlast")).equals(hi));
    }


    @Test
    public void testNeighborRangeAlgorithmThree5() {
        NeighborRangeShardingAlgorithm neighborRangeShardingAlgorithm = new NeighborRangeShardingAlgorithm();
        Range range = Range.range("1701211212121234567890123", BoundType.CLOSED, "1902051212121234567890123", BoundType.CLOSED);
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "SourceTxNO", range);
        Collection<String> hi = neighborRangeShardingAlgorithm.doSharding(AVAILABLE_TABLES, begin, null);
        System.out.println("hi = " + hi);
        Assert.assertTrue(AVAILABLE_TABLES.equals(hi));
    }

    /**
     * before period
     */
    @Test
    public void testBeforePeriodPreciseAlgorithmOne() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodPreciseShardingAlgorithm beforePeriodPreciseShardingAlgorithm = new BeforePeriodPreciseShardingAlgorithm();
        PreciseShardingValue<String> preciseShardingValue = new PreciseShardingValue<>("t_shardingx", "AccountTime", "20171201121212678");
        Collection<String> hi = beforePeriodPreciseShardingAlgorithm.doSharding(AVAILABLE_TABLES, preciseShardingValue, tableRule);
        System.out.println("hi = " + hi);
        Assert.assertTrue(Arrays.asList("t_shardingx").equals(hi));
    }

    @Test
    public void testBeforePeriodPreciseAlgorithmOne2() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodPreciseShardingAlgorithm beforePeriodPreciseShardingAlgorithm = new BeforePeriodPreciseShardingAlgorithm();
        PreciseShardingValue<String> preciseShardingValue = new PreciseShardingValue<>("t_shardingx", "AccountTime", "20180205121212678");
        Collection<String> hi = beforePeriodPreciseShardingAlgorithm.doSharding(AVAILABLE_TABLES, preciseShardingValue, tableRule);
        System.out.println("hi = " + hi);
        Assert.assertTrue(Arrays.asList("t_shardingx", "t_shardingx180100", "t_shardingx180101", "t_shardingx180200").equals(hi));
    }

    @Test
    public void testBeforePeriodPreciseAlgorithmOne3() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodPreciseShardingAlgorithm beforePeriodPreciseShardingAlgorithm = new BeforePeriodPreciseShardingAlgorithm();
        PreciseShardingValue<String> preciseShardingValue = new PreciseShardingValue<>("t_shardingx", "AccountTime", "20181231121212678");
        Collection<String> hi = beforePeriodPreciseShardingAlgorithm.doSharding(AVAILABLE_TABLES, preciseShardingValue, tableRule);
        System.out.println("hi = " + hi);
        AVAILABLE_TABLES.remove(AVAILABLE_TABLES.size() - 1);
        Assert.assertTrue(AVAILABLE_TABLES.equals(hi));
    }

    @Test
    public void testBeforePeriodPreciseAlgorithmOne4() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodPreciseShardingAlgorithm beforePeriodPreciseShardingAlgorithm = new BeforePeriodPreciseShardingAlgorithm();
        PreciseShardingValue<String> preciseShardingValue = new PreciseShardingValue<>("t_shardingx", "AccountTime", "20191231121212678");
        Collection<String> hi = beforePeriodPreciseShardingAlgorithm.doSharding(AVAILABLE_TABLES, preciseShardingValue, tableRule);
        System.out.println("hi = " + hi);
        AVAILABLE_TABLES.remove(0);
        Assert.assertTrue(AVAILABLE_TABLES.equals(hi));
    }

    //> >=
    @Test
    public void testBeforePeriodRangeAlgorithmTwo1() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodRangeShardingAlgorithm beforePeriodRangeShardingAlgorithm = new BeforePeriodRangeShardingAlgorithm();
        Range range = Range.greaterThan("20171031121212");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "AccountTime", range);
        Collection<String> hi = beforePeriodRangeShardingAlgorithm.doSharding(AVAILABLE_TABLES, begin, tableRule);
        System.out.println("hi = " + hi);
        Assert.assertTrue(AVAILABLE_TABLES.equals(hi));
    }

    @Test
    public void testBeforePeriodRangeAlgorithmTwo2() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodRangeShardingAlgorithm beforePeriodRangeShardingAlgorithm = new BeforePeriodRangeShardingAlgorithm();
        Range range = Range.greaterThan("20190228121212");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "AccountTime", range);
        Collection<String> hi = beforePeriodRangeShardingAlgorithm.doSharding(BEFORE_AVAILABLE_TABLES, begin, tableRule);
        System.out.println("hi = " + hi);
        BEFORE_AVAILABLE_TABLES.remove(0);
        BEFORE_AVAILABLE_TABLES.remove(0);
        BEFORE_AVAILABLE_TABLES.remove(0);
        BEFORE_AVAILABLE_TABLES.remove(0);
        Assert.assertTrue(BEFORE_AVAILABLE_TABLES.equals(hi));
    }

    @Test
    public void testBeforePeriodRangeAlgorithmTwo3() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodRangeShardingAlgorithm beforePeriodRangeShardingAlgorithm = new BeforePeriodRangeShardingAlgorithm();
        Range range = Range.greaterThan("20290228121212");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "AccountTime", range);
        Collection<String> hi = beforePeriodRangeShardingAlgorithm.doSharding(BEFORE_AVAILABLE_TABLES, begin, tableRule);
        System.out.println("hi = " + hi);
        List<String> expect = BEFORE_AVAILABLE_TABLES.subList(BEFORE_AVAILABLE_TABLES.size() - 25, BEFORE_AVAILABLE_TABLES.size());
        Assert.assertTrue(expect.equals(hi));
    }

    //< <=
    @Test
    public void testBeforePeriodRangeAlgorithmThree1() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodRangeShardingAlgorithm beforePeriodRangeShardingAlgorithm = new BeforePeriodRangeShardingAlgorithm();
        Range range = Range.lessThan("20171031121212");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "AccountTime", range);
        Collection<String> hi = beforePeriodRangeShardingAlgorithm.doSharding(BEFORE_AVAILABLE_TABLES, begin, tableRule);
        System.out.println("hi = " + hi);
        Assert.assertTrue(Arrays.asList("t_shardingx").equals(hi));
    }

    @Test
    public void testBeforePeriodRangeAlgorithmThree2() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodRangeShardingAlgorithm beforePeriodRangeShardingAlgorithm = new BeforePeriodRangeShardingAlgorithm();
        Range range = Range.lessThan("20190728121212");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "AccountTime", range);
        Collection<String> hi = beforePeriodRangeShardingAlgorithm.doSharding(BEFORE_AVAILABLE_TABLES, begin, tableRule);
        System.out.println("hi = " + hi);
        BEFORE_AVAILABLE_TABLES.remove(BEFORE_AVAILABLE_TABLES.size() - 1);
        BEFORE_AVAILABLE_TABLES.remove(BEFORE_AVAILABLE_TABLES.size() - 1);
        BEFORE_AVAILABLE_TABLES.remove(BEFORE_AVAILABLE_TABLES.size() - 1);
        Assert.assertTrue(BEFORE_AVAILABLE_TABLES.equals(hi));
    }

    @Test
    public void testBeforePeriodRangeAlgorithmThree3() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodRangeShardingAlgorithm beforePeriodRangeShardingAlgorithm = new BeforePeriodRangeShardingAlgorithm();
        Range range = Range.lessThan("20290228121212");
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "AccountTime", range);
        Collection<String> hi = beforePeriodRangeShardingAlgorithm.doSharding(BEFORE_AVAILABLE_TABLES, begin, tableRule);
        System.out.println("hi = " + hi);
        Assert.assertTrue(BEFORE_AVAILABLE_TABLES.equals(hi));
    }


    @Test
    public void testBeforePeriodRangeAlgorithmFour1() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodRangeShardingAlgorithm beforePeriodRangeShardingAlgorithm = new BeforePeriodRangeShardingAlgorithm();
        Range range = Range.range("20171031121212", BoundType.OPEN, "20180205121212", BoundType.OPEN);
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "AccountTime", range);
        Collection<String> hi = beforePeriodRangeShardingAlgorithm.doSharding(BEFORE_AVAILABLE_TABLES, begin, tableRule);
        System.out.println("hi = " + hi);
        List<String> expect = BEFORE_AVAILABLE_TABLES.subList(0, 4);
        Assert.assertTrue(expect.equals(hi));
    }

    @Test
    public void testBeforePeriodRangeAlgorithmFour2() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodRangeShardingAlgorithm beforePeriodRangeShardingAlgorithm = new BeforePeriodRangeShardingAlgorithm();
        Range range = Range.range("20180131121212", BoundType.OPEN, "20180518121212", BoundType.OPEN);
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "AccountTime", range);
        Collection<String> hi = beforePeriodRangeShardingAlgorithm.doSharding(BEFORE_AVAILABLE_TABLES, begin, tableRule);
        System.out.println("hi = " + hi);
        List<String> expect = BEFORE_AVAILABLE_TABLES.subList(0, 11);
        Assert.assertTrue(expect.equals(hi));
    }


    @Test
    public void testBeforePeriodRangeAlgorithmFour3() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodRangeShardingAlgorithm beforePeriodRangeShardingAlgorithm = new BeforePeriodRangeShardingAlgorithm();
        Range range = Range.range("20180531121212", BoundType.OPEN, "20280518121212", BoundType.OPEN);
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "AccountTime", range);
        Collection<String> hi = beforePeriodRangeShardingAlgorithm.doSharding(BEFORE_AVAILABLE_TABLES, begin, tableRule);
        System.out.println("hi = " + hi);
        Assert.assertTrue(BEFORE_AVAILABLE_TABLES.equals(hi));
    }

    @Test
    public void testBeforePeriodRangeAlgorithmFour4() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodRangeShardingAlgorithm beforePeriodRangeShardingAlgorithm = new BeforePeriodRangeShardingAlgorithm();
        Range range = Range.range("20190428121212", BoundType.OPEN, "20280518121212", BoundType.OPEN);
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "AccountTime", range);
        Collection<String> hi = beforePeriodRangeShardingAlgorithm.doSharding(BEFORE_AVAILABLE_TABLES, begin, tableRule);
        System.out.println("hi = " + hi);
        List<String> expect = BEFORE_AVAILABLE_TABLES.subList(8, BEFORE_AVAILABLE_TABLES.size());
        Assert.assertTrue(expect.equals(hi));
    }

    @Test
    public void testBeforePeriodRangeAlgorithmFour5() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodRangeShardingAlgorithm beforePeriodRangeShardingAlgorithm = new BeforePeriodRangeShardingAlgorithm();
        Range range = Range.range("20170428121212", BoundType.OPEN, "20170518121212", BoundType.OPEN);
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "AccountTime", range);
        Collection<String> hi = beforePeriodRangeShardingAlgorithm.doSharding(BEFORE_AVAILABLE_TABLES, begin, tableRule);
        System.out.println("hi = " + hi);
        Assert.assertTrue(Arrays.asList("t_shardingx").equals(hi));
    }

    @Test
    public void testBeforePeriodRangeAlgorithmFour6() {
        TableRule tableRule = new TableRule(null, "t_shardingx");
        tableRule.setPerMonthTables(2);
        BeforePeriodRangeShardingAlgorithm beforePeriodRangeShardingAlgorithm = new BeforePeriodRangeShardingAlgorithm();
        Range range = Range.range("20270428121212", BoundType.OPEN, "20280518121212", BoundType.OPEN);
        RangeShardingValue begin = new RangeShardingValue("t_shardingx", "AccountTime", range);
        Collection<String> hi = beforePeriodRangeShardingAlgorithm.doSharding(BEFORE_AVAILABLE_TABLES, begin, tableRule);
        System.out.println("hi = " + hi);
        List<String> expect = BEFORE_AVAILABLE_TABLES.subList(BEFORE_AVAILABLE_TABLES.size() - 25, BEFORE_AVAILABLE_TABLES.size());
        Assert.assertTrue(expect.equals(hi));
    }
}