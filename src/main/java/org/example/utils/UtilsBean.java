package org.example.utils;

import java.math.BigInteger;
import java.util.Random;

public class UtilsBean {

	public BigInteger generateSSN (){
	    BigInteger bigInteger = new BigInteger("9349988899999");
	    BigInteger bigInteger1 = bigInteger.subtract(new BigInteger("1"));
		return randomBigInteger(bigInteger1);
	}

	private static BigInteger randomBigInteger(BigInteger n) {
		Random rnd = new Random();
		int maxNumBitLength = n.bitLength();
		BigInteger aRandomBigInt;
		do {
			aRandomBigInt = new BigInteger(maxNumBitLength, rnd);
			// compare random number lessthan ginven number
		} while (aRandomBigInt.compareTo(n) > 0);
		return aRandomBigInt;
	}

}
