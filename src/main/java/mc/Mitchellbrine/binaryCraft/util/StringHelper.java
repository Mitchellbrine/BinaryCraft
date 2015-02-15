package mc.Mitchellbrine.binaryCraft.util;

import org.apache.commons.codec.binary.BinaryCodec;

/**
 * Created by Mitchellbrine on 2015.
 */
public class StringHelper {

	public static char[] toCharArray(String string) {
		char[] result = new char[string.length()];
		for (int i = 0; i < result.length;i++) {
			result[i] = string.charAt(i);
		}

		return result;
	}

	public static String toBinary(String stringToConvert) {
		return BinaryCodec.toAsciiString(new StringBuffer(stringToConvert).reverse().toString().getBytes());
	}

	public static byte[] toBinaryBytes(String stringToConvert) {
		return BinaryCodec.toAsciiBytes(new StringBuffer(stringToConvert).reverse().toString().getBytes());
	}

	public static String fromBinary(byte[] asciiString) {
		return new StringBuffer(new String(BinaryCodec.fromAscii(asciiString))).reverse().toString();
	}


}
