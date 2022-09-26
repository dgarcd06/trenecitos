

import java.io.InputStream;
import java.io.PrintStream;
import org.junit.*;

public class TrenecitosTest {

	private static InputStream in;
	private static PrintStream out;
	
@Before
public void setUp() throws Exception {
	System.setIn(in);
	System.setOut(out);
}


/*2
I 5 10 10
D 7 10 11


2
A 10 10 25
D 10 14 20


2
I 4 25 10
D 5 25 7

8
I 4 25 10
D 5 25 7
A 10 10 25
D 10 14 20
I 5 10 10
D 7 10 11
I 8 9 13
D 8 20 16




*/
}
