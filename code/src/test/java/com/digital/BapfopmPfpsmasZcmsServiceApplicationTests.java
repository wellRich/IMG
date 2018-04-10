package com.digital;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BapfopmPfpsmasZcmsServiceApplicationTests {

	@Test
	public void contextLoads() {
          BigInteger bigInteger = BigInteger.valueOf(10);
          System.out.println(bigInteger);

          BigInteger dd = bigInteger.add(BigInteger.valueOf(12));
          System.out.println(dd);
      }

}
