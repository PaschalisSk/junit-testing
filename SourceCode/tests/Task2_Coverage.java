import static org.junit.Assert.*;

import org.junit.Test;

import st.Parser;

import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class Task2_Coverage {
    private Parser parser;

    @Before
    public void set_up() {
        parser = new Parser();
    }

    /**
     * Specification 3.1
     */
    @Test
    public void test_add_shortcut() {
        parser.add("output", "o", Parser.STRING);
        int parsed = parser.parse("-o=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("o"));
    }

    @Test
    public void test_add_shortcut_override_name_shortcut_parse_with_name() {
        parser.add("output", "o", Parser.STRING);
        parser.add("output", "out", Parser.STRING);
        int parsed = parser.parse("--output=output.txt");
        assertEquals(0, parsed);
        assertEquals("", parser.getString("o"));
        assertEquals("output.txt", parser.getString("output"));
        assertEquals("output.txt", parser.getString("out"));
    }

    @Test
    public void test_add_shortcut_override_name_shortcut_parse_with_new_shortcut() {
        parser.add("output", "o", Parser.STRING);
        parser.add("output", "out", Parser.STRING);
        int parsed = parser.parse("-out=output.txt");
        assertEquals(0, parsed);
        assertEquals("", parser.getString("o"));
        assertEquals("output.txt", parser.getString("output"));
        assertEquals("output.txt", parser.getString("out"));
    }

    @Test
    public void test_add_shortcut_override_name_shortcut_parse_with_old_shortcut() {
        parser.add("output", "o", Parser.STRING);
        parser.add("output", "out", Parser.STRING);
        int parsed = parser.parse("-o=output.txt");
        assertEquals(0, parsed);
        assertEquals("", parser.getString("o"));
        assertEquals("", parser.getString("output"));
        assertEquals("", parser.getString("out"));
    }

    @Test
    public void test_add_shortcut_override_name_type() {
        parser.add("output", "o", Parser.BOOLEAN);
        parser.add("output", "o", Parser.INTEGER);
        int parsed = parser.parse("--output=5");
        assertEquals(0, parsed);
        assertEquals(5, parser.getInteger("o"));
    }

    @Test
    public void test_add_shortcut_override_name_after_assignment() {
        parser.add("output", "o", Parser.INTEGER);
        int parsed = parser.parse("--output=5");
        assertEquals(0, parsed);
        parser.add("output", "o", Parser.INTEGER);
        assertEquals(0, parser.getInteger("o"));
    }


    /**
     * Specification 3.2
     */
    @Test(expected = Exception.class)
    public void test_add_shortcut_name_empty() {
        parser.add("", "o", Parser.STRING);
    }

    @Test
    public void test_add_shortcut_name_underscore() {
        parser.add("output_", "o", Parser.STRING);
        int parsed = parser.parse("--output_=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("output_"));
    }

    @Test
    public void test_add_shortcut_name_underscore_first() {
        parser.add("_output", "o", Parser.STRING);
        int parsed = parser.parse("--_output=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("_output"));
    }

    @Test
    public void test_add_shortcut_name_underscore_middle() {
        parser.add("out_put", "o", Parser.STRING);
        int parsed = parser.parse("--out_put=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("out_put"));
    }

    @Test
    public void test_add_shortcut_name_number() {
        parser.add("output1", "o", Parser.STRING);
        int parsed = parser.parse("--output1=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("output1"));
    }

    @Test
    public void test_add_shortcut_name_number_middle() {
        parser.add("out123put", "o", Parser.STRING);
        int parsed = parser.parse("--out123put=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("out123put"));
    }

    @Test(expected = Exception.class)
    public void test_add_shortcut_name_number_first() {
        parser.add("1output", "o", Parser.STRING);
    }

    @Test(expected = Exception.class)
    public void test_add_shortcut_name_special_char() {
        parser.add("out%put", "o", Parser.STRING);
    }

    @Test(expected = Exception.class)
    public void test_add_shortcut_name_special_char_first() {
        parser.add("%output", "o", Parser.STRING);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void test_add_shortcut_name_multiple_tests() {
        parser.add("output", "o", Parser.STRING);
        parser.add("o", "o", Parser.STRING);
        parser.add("_", "o", Parser.STRING);
        parser.add("_1", "o", Parser.STRING);
        parser.add("_o", "o", Parser.STRING);
        parser.add("o1", "o", Parser.STRING);
    }

    @Test
    public void test_add_shortcut_shortcut_underscore() {
        parser.add("output", "o_", Parser.STRING);
        int parsed = parser.parse("-o_=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("o_"));
    }

    @Test
    public void test_add_shortcut_shortcut_underscore_first() {
        parser.add("output", "_o", Parser.STRING);
        int parsed = parser.parse("-_o=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("_o"));
    }

    @Test
    public void test_add_shortcut_shortcut_underscore_middle() {
        parser.add("output", "o_1", Parser.STRING);
        int parsed = parser.parse("-o_1=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("o_1"));
    }

    @Test
    public void test_add_shortcut_shortcut_number() {
        parser.add("output", "o1", Parser.STRING);
        int parsed = parser.parse("-o1=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("o1"));
    }

    @Test
    public void test_add_shortcut_shortcut_number_middle() {
        parser.add("output", "o1a", Parser.STRING);
        int parsed = parser.parse("-o1a=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("o1a"));
    }

    @Test(expected = Exception.class)
    public void test_add_shortcut_shortcut_number_first() {
        parser.add("output", "1o", Parser.STRING);
    }

    @Test(expected = Exception.class)
    public void test_add_shortcut_shortcut_special_char() {
        parser.add("output", "o%o", Parser.STRING);
    }

    @Test(expected = Exception.class)
    public void test_add_shortcut_shortcut_special_char_first() {
        parser.add("output", "%o", Parser.STRING);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void test_add_shortcut_shortcut_multiple_tests() {
        parser.add("output", "output", Parser.STRING);
        parser.add("output", "o", Parser.STRING);
        parser.add("output", "_", Parser.STRING);
        parser.add("output", "_1", Parser.STRING);
        parser.add("output", "_o", Parser.STRING);
        parser.add("output", "o1", Parser.STRING);
    }


    /**
     * Specification 3.3
     */
    @Test
    public void test_add_shortcut_name_case_sensitive() {
        parser.add("output", "o", Parser.STRING);
        parser.add("Output", "o", Parser.STRING);
        int parsed = parser.parse("--output=output.txt --Output=Out.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("output"));
        assertEquals("Out.txt", parser.getString("Output"));
    }

    @Test
    public void test_add_shortcut_shortcut_case_sensitive() {
        parser.add("output1", "o", Parser.STRING);
        parser.add("output2", "O", Parser.STRING);
        int parsed = parser.parse("-o=output.txt -O=Out.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("o"));
        assertEquals("Out.txt", parser.getString("O"));
    }

    /**
     * Specification 3.4
     */
    @Test
    public void test_add_shortcut_shortcut_equal_to_other_name() {
        parser.add("output1", "o", Parser.STRING);
        parser.add("output2", "output1", Parser.STRING);
        int parsed = parser.parse("--output1=output.txt -output1=Out.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("o"));
        assertEquals("Out.txt", parser.getString("output2"));
    }

    /**
     * Specification 3.5
     */
    @Test
    public void test_add_shortcut_boolean_true() {
        parser.add("opt1", "o1", Parser.BOOLEAN);
        parser.add("opt2", "o2", Parser.BOOLEAN);
        parser.add("opt3", "o3", Parser.BOOLEAN);
        parser.add("opt4", "o4", Parser.BOOLEAN);
        parser.add("opt5", "o5", Parser.BOOLEAN);
        int parsed = parser.parse("-o1 -o2=true -o3=100 -o4 100 --opt5");
        assertEquals(0, parsed);
        assertTrue(parser.getBoolean("o1"));
        assertTrue(parser.getBoolean("o2"));
        assertTrue(parser.getBoolean("o3"));
        assertTrue(parser.getBoolean("o4"));
        assertTrue(parser.getBoolean("o5"));
    }

    @Test
    public void test_add_shortcut_boolean_false() {
        parser.add("opt1", "o1", Parser.BOOLEAN);
        parser.add("opt2", "o2", Parser.BOOLEAN);
        parser.add("opt3", "o3", Parser.BOOLEAN);
        int parsed = parser.parse("-o1=false -o2=0 -o3=FaLSe");
        assertEquals(0, parsed);
        assertFalse(parser.getBoolean("o1"));
        assertFalse(parser.getBoolean("o2"));
        assertFalse(parser.getBoolean("o3"));
    }

    /**
     * Specification 4.1
     */
    @Test
    public void test_add() {
        parser.add("output", Parser.STRING);
        int parsed = parser.parse("--output=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("output"));
    }

    @Test
    public void test_add_override_name_deletes_shortcut() {
        parser.add("output", "o", Parser.INTEGER);
        parser.add("output", Parser.INTEGER);
        int parsed = parser.parse("--output=10 -o=5");
        assertEquals(0, parsed);
        assertEquals(0, parser.getInteger("o"));
        assertEquals(10, parser.getInteger("output"));
    }

    @Test
    public void test_add_override_name_type() {
        parser.add("output", Parser.BOOLEAN);
        parser.add("output", Parser.INTEGER);
        int parsed = parser.parse("--output=5");
        assertEquals(0, parsed);
        assertEquals(5, parser.getInteger("output"));
    }

    @Test
    public void test_add_override_name_after_assignment() {
        parser.add("output", Parser.INTEGER);
        int parsed = parser.parse("--output=5");
        assertEquals(0, parsed);
        parser.add("output", Parser.INTEGER);
        assertEquals(0, parser.getInteger("output"));
    }


    /**
     * Specification 4.2
     */
    @Test(expected = Exception.class)
    public void test_add_name_empty() {
        parser.add("", Parser.STRING);
    }

    @Test
    public void test_add_name_underscore() {
        parser.add("output_", Parser.STRING);
        int parsed = parser.parse("--output_=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("output_"));
    }

    @Test
    public void test_add_name_underscore_first() {
        parser.add("_output", Parser.STRING);
        int parsed = parser.parse("--_output=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("_output"));
    }

    @Test
    public void test_add_name_underscore_middle() {
        parser.add("out_put", Parser.STRING);
        int parsed = parser.parse("--out_put=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("out_put"));
    }

    @Test
    public void test_add_name_number() {
        parser.add("output1", Parser.STRING);
        int parsed = parser.parse("--output1=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("output1"));
    }

    @Test
    public void test_add_name_number_middle() {
        parser.add("out123put", Parser.STRING);
        int parsed = parser.parse("--out123put=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("out123put"));
    }

    @Test(expected = Exception.class)
    public void test_add_name_number_first() {
        parser.add("1output", Parser.STRING);
    }

    @Test(expected = Exception.class)
    public void test_add_name_special_char() {
        parser.add("out%put", Parser.STRING);
    }

    @Test(expected = Exception.class)
    public void test_add_name_special_char_first() {
        parser.add("%output", Parser.STRING);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void test_add_name_multiple_tests() {
        parser.add("output", Parser.STRING);
        parser.add("o", Parser.STRING);
        parser.add("_", Parser.STRING);
        parser.add("_1", Parser.STRING);
        parser.add("_o", Parser.STRING);
        parser.add("o1", Parser.STRING);
    }

    /**
     * Specification 4.3
     */
    @Test
    public void test_add_name_case_sensitive() {
        parser.add("output", Parser.STRING);
        parser.add("Output", Parser.STRING);
        int parsed = parser.parse("--output=output.txt --Output=Out.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("output"));
        assertEquals("Out.txt", parser.getString("Output"));
    }

    /**
     * Specification 4.4
     */
    @Test
    public void test_add_shortcut_equal_to_other_name() {
        parser.add("output1", Parser.STRING);
        parser.add("output2", "output1", Parser.STRING);
        int parsed = parser.parse("--output1=output.txt -output1=Out.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("output1"));
        assertEquals("Out.txt", parser.getString("output2"));
    }

    /**
     * Specification 4.5
     */
    @Test
    public void test_add_boolean_true() {
        parser.add("opt1", Parser.BOOLEAN);
        parser.add("opt2", Parser.BOOLEAN);
        parser.add("opt3", Parser.BOOLEAN);
        parser.add("opt4", Parser.BOOLEAN);
        int parsed = parser.parse("--opt1 --opt2=true --opt3=100 --opt4 100");
        assertEquals(0, parsed);
        assertTrue(parser.getBoolean("opt1"));
        assertTrue(parser.getBoolean("opt2"));
        assertTrue(parser.getBoolean("opt3"));
        assertTrue(parser.getBoolean("opt4"));
    }

    @Test
    public void test_add_boolean_false() {
        parser.add("opt1",Parser.BOOLEAN);
        parser.add("opt2",Parser.BOOLEAN);
        parser.add("opt3",Parser.BOOLEAN);
        int parsed = parser.parse("--opt1=false --opt2=0 --opt3=FaLSe");
        assertEquals(0, parsed);
        assertFalse(parser.getBoolean("opt1"));
        assertFalse(parser.getBoolean("opt2"));
        assertFalse(parser.getBoolean("opt3"));
    }

    /**
     * Specification 5.1
     */
    @Test
    public void test_parse_non_existing_option() {
        int parsed = parser.parse("--opt1=output.txt");
        assertEquals(0, parsed);
        assertEquals("", parser.getString("opt1"));
    }

    @Test
    public void test_parse_negative_int() {
        parser.add("opt1", "o1", Parser.INTEGER);
        int parsed = parser.parse("--opt1=-7");
        assertEquals(0, parsed);
        assertEquals(-7, parser.getInteger("opt1"));
    }

    @Test
    public void test_parse_char_from_string() {
        parser.add("opt1", "o1", Parser.CHAR);
        int parsed = parser.parse("--opt1=output.txt");
        assertEquals(0, parsed);
        assertEquals('o', parser.getChar("opt1"));
    }

    @Test
    public void test_parse_str_starting_with_double_dash() {
        parser.add("opt1", "o1", Parser.STRING);
        int parsed = parser.parse("--opt1=--output.txt");
        assertEquals(0, parsed);
        assertEquals("--output.txt", parser.getString("opt1"));
    }

    @Test
    public void test_parse_double_dash_name() {
        parser.add("opt1", "o1", Parser.STRING);
        int parsed = parser.parse("--opt1=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("opt1"));
    }

    @Test
    public void test_parse_double_dash_shortcut() {
        parser.add("opt1", "o1", Parser.STRING);
        int parsed = parser.parse("--o1=output.txt");
        assertEquals(0, parsed);
        assertNotEquals("output.txt", parser.getString("opt1"));
    }

    /**
     * Specification 5.2
     */
    @Test
    public void test_parse_dash_name() {
        parser.add("opt1", "o1", Parser.STRING);
        int parsed = parser.parse("-opt1=output.txt");
        assertEquals(0, parsed);
        assertNotEquals("output.txt", parser.getString("opt1"));
    }

    @Test
    public void test_parse_dash_shortcut() {
        parser.add("opt1", "o1", Parser.STRING);
        int parsed = parser.parse("-o1=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("opt1"));
    }

    /**
     * Specification 5.3
     */
    @Test
    public void test_parse_assign_equal() {
        parser.add("opt1", "o1", Parser.STRING);
        int parsed = parser.parse("--opt1=output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("opt1"));
    }

    @Test
    public void test_parse_assign_space() {
        parser.add("opt1", "o1", Parser.STRING);
        int parsed = parser.parse("--opt1 output.txt");
        assertEquals(0, parsed);
        assertEquals("output.txt", parser.getString("opt1"));
    }

    @Test
    public void test_parse_assign_random_char() {
        parser.add("opt1", "o1", Parser.STRING);
        int parsed = parser.parse("--opt1%value");
        assertEquals(0, parsed);
        // From the pdf: 'Assigning a value to the option can either be using a "=" or a space.
        // option=value and option value are both valid.
        // Here, opt1 gets a value without using "=" or space
        // The specification states that an option gets a value ONLY with "=" or space
        // This is not the case in the implementation
        assertEquals("", parser.getString("opt1"));
    }

    /**
     * Specification 5.4
     */
    @Test
    public void test_parse_single_quotes() {
        parser.add("opt1", "o1", Parser.INTEGER);
        int parsed = parser.parse("--opt1='7'");
        assertEquals(0, parsed);
        assertEquals(7, parser.getInteger("opt1"));
    }

    @Test
    public void test_parse_double_quotes() {
        parser.add("opt1", "o1", Parser.INTEGER);
        int parsed = parser.parse("--opt1=\"7\"");
        assertEquals(0, parsed);
        assertEquals(7, parser.getInteger("opt1"));
    }

    @Test
    public void test_parse_no_quotes() {
        parser.add("opt1", "o1", Parser.INTEGER);
        int parsed = parser.parse("--opt1=7");
        assertEquals(0, parsed);
        assertEquals(7, parser.getInteger("opt1"));
    }

    /**
     * Specification 5.5
     */
    @Test
    public void test_parse_single_quotes_in_double_quotes() {
        parser.add("opt1", "o1", Parser.STRING);
        int parsed = parser.parse("--opt1=\"value='val'\"");
        assertEquals(0, parsed);
        assertEquals("value='val'", parser.getString("opt1"));
    }

    @Test
    public void test_parse_double_quotes_in_single_quotes() {
        parser.add("opt1", "o1", Parser.STRING);
        int parsed = parser.parse("--opt1='value=\"val\"'");
        assertEquals(0, parsed);
        assertEquals("value=\"val\"", parser.getString("opt1"));
    }

    /**
     * Specification 5.6
     */
    @Test
    public void test_parse_multiple_assignments_last_with_name() {
        parser.add("opt1", "o1", Parser.INTEGER);
        int parsed = parser.parse("--opt1=1 -o1=2 --opt1=3");
        assertEquals(0, parsed);
        assertEquals(3, parser.getInteger("opt1"));
    }

    @Test
    public void test_parse_multiple_assignments_last_with_shortcut() {
        parser.add("opt1", "o1", Parser.INTEGER);
        int parsed = parser.parse("--opt1=1 -o1=2 --opt1=3 -o1=4");
        assertEquals(0, parsed);
        assertEquals(4, parser.getInteger("opt1"));
    }

    /**
     * Specification 5.7
     */
    @Test
    public void test_parse_no_value_integer() {
        parser.add("opt1", "o1", Parser.INTEGER);
        parser.add("opt2", "o2", Parser.INTEGER);
        int parsed = parser.parse("--opt1=7");
        assertEquals(0, parsed);
        assertEquals(0, parser.getInteger("opt2"));
    }

    @Test
    public void test_parse_no_value_boolean() {
        parser.add("opt1", "o1", Parser.BOOLEAN);
        parser.add("opt2", "o2", Parser.BOOLEAN);
        int parsed = parser.parse("--opt1=true");
        assertEquals(0, parsed);
        assertFalse(parser.getBoolean("opt2"));
    }
    @Test
    public void test_parse_no_value_string() {
        parser.add("opt1", "o1", Parser.STRING);
        parser.add("opt2", "o2", Parser.STRING);
        int parsed = parser.parse("--opt1=output.txt");
        assertEquals(0, parsed);
        assertEquals("", parser.getString("opt2"));
    }
    @Test
    public void test_parse_no_value_char() {
        parser.add("opt1", "o1", Parser.CHAR);
        parser.add("opt2", "o2", Parser.CHAR);
        int parsed = parser.parse("--opt1=a");
        assertEquals(0, parsed);
        assertEquals('\0', parser.getChar("opt2"));
    }

    /**
     * Specification 5.8
     */
    @Test
    public void test_parse_multiple_times() {
        parser.add("opt1", "o1", Parser.INTEGER);
        parser.add("opt2", "o2", Parser.INTEGER);
        parser.add("opt3", "o3", Parser.INTEGER);
        int parsed = parser.parse("--opt1=1");
        assertEquals(0, parsed);
        parsed = parser.parse("--opt2=2 --opt3=3");
        assertEquals(0, parsed);
        parsed = parser.parse("--opt1=4");
        assertEquals(0, parsed);
        assertEquals(4, parser.getInteger("opt1"));
        assertEquals(2, parser.getInteger("opt2"));
        assertEquals(3, parser.getInteger("opt3"));
    }

    /**
     * Specification 6.1
     */
    @Test
    public void test_retrieve_name_first() {
        parser.add("opt1", "opt2", Parser.INTEGER);
        parser.add("opt2", "opt1", Parser.INTEGER);
        int parsed = parser.parse("--opt1=1 -opt1=2");
        assertEquals(0, parsed);
        assertEquals(1, parser.getInteger("opt1"));
        assertEquals(2, parser.getInteger("opt2"));
    }

    /**
     * Specification 6.2
     */

    @Test
    public void test_retrieve_integer_not_defined() {
        assertEquals(0, parser.getInteger("opt1"));
    }

    @Test
    public void test_retrieve_boolean_not_defined() {
        assertFalse(parser.getBoolean("opt1"));
    }

    @Test
    public void test_retrieve_string_not_defined() {
        assertEquals("", parser.getString("opt1"));
    }

    @Test
    public void test_retrieve_char_not_defined() {
        assertEquals('\0', parser.getChar("opt1"));
    }

    @Test
    public void test_retrieve_integer_no_value_provided() {
        parser.add("opt1", "o1", Parser.INTEGER);
        parser.add("opt2", "o2", Parser.INTEGER);
        int parsed = parser.parse("--opt2=1");
        assertEquals(0, parsed);
        assertEquals(0, parser.getInteger("opt1"));
    }

    @Test
    public void test_retrieve_boolean_no_value_provided() {
        parser.add("opt1", "o1", Parser.BOOLEAN);
        parser.add("opt2", "o2", Parser.BOOLEAN);
        int parsed = parser.parse("--opt2=true");
        assertEquals(0, parsed);
        assertFalse(parser.getBoolean("opt1"));
    }

    @Test
    public void test_retrieve_string_no_value_provided() {
        parser.add("opt1", "o1", Parser.STRING);
        parser.add("opt2", "o2", Parser.STRING);
        int parsed = parser.parse("--opt2=val");
        assertEquals(0, parsed);
        assertEquals("", parser.getString("opt1"));
    }

    @Test
    public void test_retrieve_char_no_value_provided() {
        parser.add("opt1", "o1", Parser.CHAR);
        parser.add("opt2", "o2", Parser.CHAR);
        int parsed = parser.parse("--opt2=a");
        assertEquals(0, parsed);
        assertEquals('\0', parser.getChar("opt1"));
    }

    /**
     * Extra tests for task2
     */

    @Test
    public void test_retrieve_big_integer_returns_0() {
        parser.add("opt1", "o1", Parser.INTEGER);
        int parsed = parser.parse("--opt1=2432902008176640000");
        assertEquals(0, parsed);
        assertEquals(0, parser.getInteger("opt1"));
    }

    @Test
    public void test_retrieve_integer_from_boolean_true() {
        parser.add("opt1", "o1", Parser.BOOLEAN);
        int parsed = parser.parse("--opt1=true");
        assertEquals(0, parsed);
        assertEquals(1, parser.getInteger("opt1"));
    }

    @Test
    public void test_retrieve_integer_from_boolean_false() {
        parser.add("opt1", "o1", Parser.BOOLEAN);
        int parsed = parser.parse("--opt1=false");
        assertEquals(0, parsed);
        assertEquals(0, parser.getInteger("opt1"));
    }

    @Test
    public void test_retrieve_integer_from_digit_string() {
        parser.add("opt1", "o1", Parser.STRING);
        int parsed = parser.parse("--opt1=123");
        assertEquals(0, parsed);
        assertEquals(123, parser.getInteger("opt1"));
    }

    @Test
    public void test_retrieve_integer_from_alpha_string() {
        parser.add("opt1", "o1", Parser.STRING);
        int parsed = parser.parse("--opt1=asd");
        assertEquals(0, parsed);
        assertEquals(0, parser.getInteger("opt1"));
    }

    @Test
    public void test_retrieve_integer_from_char() {
        parser.add("opt1", "o1", Parser.CHAR);
        int parsed = parser.parse("--opt1=7");
        assertEquals(0, parsed);
        // Getting int from char returns ASCII value
        assertEquals(55, parser.getInteger("opt1"));
    }

    @Test
    public void test_parse_null_arg() {
        int parsed = parser.parse(null);
        assertEquals(-1, parsed);
    }

    @Test
    public void test_parse_empty_arg() {
        int parsed = parser.parse("");
        assertEquals(-2, parsed);
    }

    @Test
    public void test_parse_start_with_empty_arg() {
        parser.add("opt1", "o1", Parser.INTEGER);
        int parsed = parser.parse("  --opt1=7");
        assertEquals(0, parsed);
        assertEquals(7, parser.getInteger("opt1"));
    }

    @Test
    public void test_parse_no_dash_name() {
        parser.add("opt1", "o1", Parser.INTEGER);
        int parsed = parser.parse("  opt1=7");
        assertEquals(-3, parsed);
    }

    @Test
    public void test_parse_no_value_for_non_boolean() {
        parser.add("opt1", "o1", Parser.INTEGER);
        int parsed = parser.parse("--opt1");
        assertEquals(-3, parsed);
    }

    @Test
    public void test_parser_to_string() {
        parser.add("opt1", "o1", Parser.INTEGER);
        int parsed = parser.parse("--opt1=7");
        assertEquals(0, parsed);
        assertEquals("OptionMap [options=\n" +
                "\t{name=opt1, shortcut=o1, type=1, value=7}\n" +
                "]", parser.toString());
    }

    @Test(expected = Exception.class)
    public void test_add_name_null() {
        parser.add(null, Parser.INTEGER);
    }

    @Test(expected = Exception.class)
    public void test_add_shortcut_shortcut_null() {
        parser.add("output", null, Parser.INTEGER);
    }

    @Test(expected = Exception.class)
    public void test_add_invalid_type_less() {
        parser.add("output", "o", 0);
    }

    @Test(expected = Exception.class)
    public void test_add_invalid_type_greater() {
        parser.add("output", "o", 5);
    }

    @Test
    public void test_parse_boolean_last_char_space() {
        parser.add("opt1", "o1", Parser.BOOLEAN);
        int parsed = parser.parse("-o1 ");
        assertEquals(0, parsed);
        assertTrue(parser.getBoolean("opt1"));
    }

    @Test
    public void test_parse_space() {
        parser.add("opt1", "o1", Parser.BOOLEAN);
        int parsed = parser.parse(" ");
        assertEquals(-3, parsed);
    }

}
