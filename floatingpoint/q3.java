package ca.utoronto.utm.floatingpoint;


public class q3 {
	/**
	 * YOUR ANSWER GOES HERE!!!
	 * 
	 * 
	 * s[eeeeeeee]mmmmmmmmmmmmmmmmmmmmmmm
	 * 
	 * s - the sign (+/-) represents the positive or negative of the number, 0 if positive, 1 if negative.
	 * 
	 * e - the exponent written in excess of 127, therefore the real exponent should be (eeeeeeee - 127).
	 * 
	 * m - the mantissa represents the 23 bit number written after the first "1".
	 *  
	 * 
	 * a) -6.8 (base 10)
	 * To make it easier, we can split 6.8 into 6.0 and 0.8, and do base 2 conversions for each of them separately.
	 * 
	 * 6 (base 10) is 110 (base 2) since [(1 x 2^2) + (1 x 2^1) + (0 x 2^0)] is 6
	 * 					  ....																				.
	 * 0.8 (base 10) is 0.1100 (base 2) since [(1 x 2^-1) + (1 x 2^-2) + (0 x 2^-3) + (0 x 2^-4)...] is 0.79 however we 
	 * will round it up later.
	 * 
	 * and add both together to get the binary representation of 6.8,  110.1100110011001100110011001100.
	 * 
	 * increment the decimal point to the most left "1",  1.101100110011001100110011001100 x 2^(2)
	 * 
	 * Also, make sure the binary representation is 24 digits in total since the left most "1" is ignored, and convert that 
	 * right most "1" to "0" since we are rounding up, we take the digits after the decimals as the mantissa.
	 * 1.10110011001100110011000 x 2^(2)
	 * 
	 * so the real exponent is 2, then the exponent should be 129 since it should be written in excess of 127.
	 * 
	 * convert the exponent to binary  (10000001)
	 * 
	 * lastly, the sign is 1 since -6.8 is a negative number.
	 * 
	 * 
	 *  -6.8 (base 10)
	 * =110.1100110011001100110011001100
	 * =1.101100110011001100110011001100 x 2^(2)
	 * =1.101100110011001100110011001100 x 2^(129 - 127)
	 * =1.101100110011001100110011001100 x 2^(10000001 - 127)
	 * 
	 * finally, -6.8 as an IEEE754 single is:
	 * 1[10000001]10110011001100110011000
	 * 
	 * 
	 * 
	 * b) 23.1 (base 10)
	 * Again, we can split 23.1 into 23.0 and 0.1, and do base 2 conversions for each of them separately.
	 * 
	 * 23 = [(1 x 2^4) + (1 x 2^2) + (1 x 2^1) + (1 x 2^0)]
	 * 
	 * 0.1 is roughly represented as [(1 x 2^-4) + (1 x 2^-5) + (1 x 2^-8) + (1 x 2^-9)...], however this representation
	 * never reaches 0.1 and is instead 0.0999... again, we round up later.
	 * 
	 * and add both together to get the binary representation of 23.1,  10111.0001100110011001100110011.
	 * 
	 * increment the decimal point to the most left "1",  1.01110001100110011001100110011 x 2^(4)
	 * 
	 * Again, make sure the binary representation is 24 digits in total since the left most "1" is ignored, and convert that 
	 * right most "0" to "1" since we are rounding up, we take the digits after the decimals as the mantissa.
	 * 1.01110001100110011001101 x 2^(4)
	 * 
	 * so the real exponent is 4, then the exponent should be 129 since it should be written in excess of 131.
	 * 
	 * convert the exponent to binary  (10000011)
	 * 
	 * lastly, the sign is 0 since 23.1 is a positive number.
	 * 
	 *  23.1 (base 10)
	 * =10111.0001100110011001100110011
	 * =1.01110001100110011001100110011 x 2^(4)
	 * =1.01110001100110011001100110011 x 2^(131 - 127)
	 * =1.01110001100110011001100110011 x 2^(10000011 - 127)
	 * 
	 * finally, 23.1 as an IEEE754 single is:
	 * 0[10000011]01110001100110011001101
	 * 
	 * 
	 * 
	 * 
	 * 
	 *
	 */
}
