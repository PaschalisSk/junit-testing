import static org.junit.Assert.*;

import org.junit.Test;

import st.Parser;

import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Task3_TDD_1 {
    private Parser parser;

    @Before
    public void set_up() {
        parser = new Parser();
    }

    /**
     * Specification 1
     */
    @Test
    public void test_order_of_search() {
        parser.add("list1", "list2", Parser.STRING);
        parser.add("list2", "list1", Parser.STRING);
        int parsed = parser.parse("--list1=1,2,3 -list1=4,5,6");
        assertEquals(0, parsed);
        List<Integer> expected_list1 = Arrays.asList(1, 2, 3);
        assertEquals(expected_list1, parser.getIntegerList("list1"));
        List<Integer> expected_list2 = Arrays.asList(4, 5, 6);
        assertEquals(expected_list2, parser.getIntegerList("list2"));
    }

    /**
     * Specification 2
     */
    @Test
    public void test_empty_list() {
        parser.add("list1", "l1", Parser.STRING);
        parser.add("list2", "l2", Parser.STRING);
        int parsed = parser.parse("--list2=1,2,3");
        assertEquals(0, parsed);
        List<Integer> expected_list1 = new ArrayList<>();
        assertEquals(expected_list1, parser.getIntegerList("list1"));
        List<Integer> expected_list2 = Arrays.asList(1, 2, 3);
        assertEquals(expected_list2, parser.getIntegerList("list2"));
    }

    /**
     * Specification 3
     */
    @Test
    public void test_non_number_seperators() {
        parser.add("list1", "l1", Parser.STRING);
        parser.add("list2", "l2", Parser.STRING);
        parser.add("list3", "l3", Parser.STRING);
        parser.add("list4", "l4", Parser.STRING);
        parser.add("list5", "l5", Parser.STRING);
        parser.add("list6", "l6", Parser.STRING);
        int parsed = parser.parse("-l1=1.2;3 -l2==1=2=3 -l3=\"a1 2'3\" -l4 '\"1}2{3}}' -l5=a1=a2a=3=a -l6=\"'3'2'1''\"");
        assertEquals(0, parsed);
        List<Integer> expected_list1 = Arrays.asList(1, 2, 3);
        assertEquals(expected_list1, parser.getIntegerList("list1"));
        List<Integer> expected_list2 = Arrays.asList(1, 2, 3);
        assertEquals(expected_list2, parser.getIntegerList("list2"));
        List<Integer> expected_list3 = Arrays.asList(1, 2, 3);
        assertEquals(expected_list3, parser.getIntegerList("list3"));
        List<Integer> expected_list4 = Arrays.asList(1, 2, 3);
        assertEquals(expected_list4, parser.getIntegerList("list4"));
        List<Integer> expected_list5 = Arrays.asList(1, 2, 3);
        assertEquals(expected_list5, parser.getIntegerList("list5"));
        List<Integer> expected_list6 = Arrays.asList(1, 2, 3);
        assertEquals(expected_list6, parser.getIntegerList("list6"));
    }

    /**
     * Specification 4
     */
    @Test
    public void test_dash() {
        parser.add("list1", "l1", Parser.STRING);
        parser.add("list2", "l2", Parser.STRING);
        parser.add("list3", "l3", Parser.STRING);
        parser.add("list4", "l4", Parser.STRING);
        parser.add("list5", "l5", Parser.STRING);
        parser.add("list6", "l6", Parser.STRING);
        int parsed = parser.parse("-l1=1-1 -l2=1-2 -l3=1,2-3 -l4=3-1 -l5=a10-8 -l6='a10- 8'");
        assertEquals(0, parsed);
        List<Integer> expected_list1 = Arrays.asList(1);
        assertEquals(expected_list1, parser.getIntegerList("list1"));
        List<Integer> expected_list2 = Arrays.asList(1, 2);
        assertEquals(expected_list2, parser.getIntegerList("list2"));
        List<Integer> expected_list3 = Arrays.asList(1, 2, 3);
        assertEquals(expected_list3, parser.getIntegerList("list3"));
        List<Integer> expected_list4 = Arrays.asList(1, 2, 3);
        assertEquals(expected_list4, parser.getIntegerList("list4"));
        List<Integer> expected_list5 = Arrays.asList(8, 9, 10);
        assertEquals(expected_list5, parser.getIntegerList("list5"));
        List<Integer> expected_list6 = new ArrayList<>();
        assertEquals(expected_list6, parser.getIntegerList("list6"));
    }

    /**
     * Specification 5
     */
    @Test
    public void test_unary_dash() {
        parser.add("list1", "l1", Parser.STRING);
        parser.add("list2", "l2", Parser.STRING);
        parser.add("list3", "l3", Parser.STRING);
        parser.add("list4", "l4", Parser.STRING);
        parser.add("list5", "l5", Parser.STRING);
        parser.add("list6", "l6", Parser.STRING);
        parser.add("list7", "l7", Parser.STRING);
        int parsed = parser.parse("-l1=-1 -l2=-1-2 -l3=-1--3 -l4=a-1a-a-3z-5 -l5=-1=-4 -l6=-1--1 -l7='1 --1");
        assertEquals(0, parsed);
        List<Integer> expected_list1 = Arrays.asList(-1);
        assertEquals(expected_list1, parser.getIntegerList("list1"));
        List<Integer> expected_list2 = Arrays.asList(-1, 0, 1, 2);
        assertEquals(expected_list2, parser.getIntegerList("list2"));
        List<Integer> expected_list3 = Arrays.asList(-3, -2, -1);
        assertEquals(expected_list3, parser.getIntegerList("list3"));
        List<Integer> expected_list4 = new ArrayList<>();
        assertEquals(expected_list4, parser.getIntegerList("list4"));
        List<Integer> expected_list5 = Arrays.asList(-4, -1);
        assertEquals(expected_list5, parser.getIntegerList("list5"));
        List<Integer> expected_list6 = Arrays.asList(-1);
        assertEquals(expected_list6, parser.getIntegerList("list6"));
        List<Integer> expected_list7 = new ArrayList<>();
        assertEquals(expected_list7, parser.getIntegerList("list7"));
    }

    /**
     * Specification 6
     */
    @Test
    public void test_dash_suffix() {
        parser.add("list1", "l1", Parser.STRING);
        parser.add("list2", "l2", Parser.STRING);
        parser.add("list3", "l3", Parser.STRING);
        int parsed = parser.parse("-l1=-1-- -l2='1- ' -l3=1a-");
        assertEquals(0, parsed);
        List<Integer> empty_list = new ArrayList<>();
        assertEquals(empty_list, parser.getIntegerList("list1"));
        assertEquals(empty_list, parser.getIntegerList("list2"));
        assertEquals(empty_list, parser.getIntegerList("list3"));
    }

    /**
     * Extra tests
     */
    @Test
    public void test_list_with_one_element() {
        parser.add("list1", "l1", Parser.STRING);
        int parsed = parser.parse("--list1=1");
        assertEquals(0, parsed);
        List<Integer> expected_list1 = Arrays.asList(1);
        assertEquals(expected_list1, parser.getIntegerList("list1"));
    }

}
