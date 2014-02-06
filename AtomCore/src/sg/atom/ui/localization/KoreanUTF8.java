package sg.atom.ui.localization;

import java.io.UnsupportedEncodingException;

/**
 * 유니코드 한글 변환표 http://www.unicode.org/charts/PDF/U1100.pdf
 * @author mulova
 *
 */
public class KoreanUTF8 {
	
	public static boolean isHangul(char c) {
//		return Character.getType(c) == 5;
		return isJamo(c) || (c >= 0xAC00 && c <= 0xD7A3);
	}
	
	public static boolean isHangul(String str) {
		if (str == null) return false;
		for (int i = 0; i < str.length(); i++) {
			if (!isHangul(str.charAt(i)))
				return false;
		}
		return true;
	}
	
	/**
	 * 한글 음절인가
	 * @param c
	 * @return
	 * @author mulova
	 */
	public static boolean isHangulSyllables(char c) {
		return c >= 0xAC00 && c <= 0xD7A3;
	}
	
	/**
	 * 한글 음절인가
	 * @param str
	 * @return
	 * @author mulova
	 */
	public static boolean isHangulSyllables(String str) {
		if (str == null) return false;
		for (int i = 0; i < str.length(); i++) {
			if (isHangulSyllables(str.charAt(i)))
				return true;
		}
		return false;
	}
	
	public static boolean isHangulJamo(char c) {
		return c >= 0x3131 && c <= 0x318E;
	}
	
	/**
	 * 한글 음절인가
	 * @param str
	 * @return
	 * @author mulova
	 */
	public static boolean isHangulJamo(String str) {
		if (str == null) return false;
		for (int i = 0; i < str.length(); i++) {
			if (isHangulJamo(str.charAt(i)))
				return true;
		}
		return false;
	}
	
	/**
     * 한글은 2글자로 count한다.
     * @param cursor
     * @return
     * @author mulova
     */
    public static int getLength(String text) {
    	int length = 0;
    	for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (isHangul(c)) {
				length += 2;
			} else {
				length++;
			}
		}
		return length;
	}
	
	public static boolean isJamo(char c) {
		if (c >= 0x1100 && c <= 0x11F9) { // 한글 자모
			return true;
		}
		if (c >= 0x3131 && c <= 0x318E) { // 한글 호환 자모
			return true;
		}
		return false;
	}
	
	/*public static boolean isHangul(int keyCode){
		if(keyCode == 65521 || keyCode == 65520){
			return true;
		}else{
			return false;
		}
	}*/
	
	/**
	 * 초성을 얻어온다.
	 * 
	 * UTF-8 한글 코드값 = 0xAC00 + (초성값*21*28) + (중성값*28) + 종성값
	 * @param c
	 * @return 없을 경우 Charater.MIN_VALUE 반환한다.
	 * @author mulova
	 */
	public static char getInitialConsonant(char c) {
		final int code = (c - 0xAC00)/(21*28);
		switch (code) {
		case 0:
			return 'ㄱ';
		case 1:
			return 'ㄲ';
		case 2:
			return 'ㄴ';
		case 3:
			return 'ㄷ';
		case 4:
			return 'ㄸ';
		case 5:
			return 'ㄹ';
		case 6:
			return 'ㅁ';
		case 7:
			return 'ㅂ';
		case 8:
			return 'ㅃ';
		case 9:
			return 'ㅅ';
		case 0x0a:
			return 'ㅆ';
		case 0x0b:
			return 'ㅇ';
		case 0x0c:
			return 'ㅈ';
		case 0x0d:
			return 'ㅉ';
		case 0x0e:
			return 'ㅊ';
		case 0x0f:
			return 'ㅋ';
		case 0x10:
			return 'ㅌ';
		case 0x11:
			return 'ㅍ';
		case 0x12:
			return 'ㅎ';
		}
		return Character.MIN_VALUE;
	}
	
	/**
	 * 중성을 얻어온다.
	 * UTF-8 한글 코드값 = 0xAC00 + (초성값*21*28) + (중성값*28) + 종성값
	 * @param c
	 * @return
	 * @author mulova
	 */
	public static char getMedialVowel(char c) {
		final int code = ((c - 0xAC00)/28)%21;
		switch (code) {
		case 0:
			return 'ㅏ';
		case 1:
			return 'ㅐ';
		case 2:
			return 'ㅑ';
		case 3:
			return 'ㅒ';
		case 4:
			return 'ㅓ';
		case 5:
			return 'ㅔ';
		case 6:
			return 'ㅕ';
		case 7:
			return 'ㅖ';
		case 8:
			return 'ㅗ';
		case 9:
			return 'ㅘ';
		case 0x0a:
			return 'ㅙ';
		case 0x0b:
			return 'ㅚ';
		case 0x0c:
			return 'ㅛ';
		case 0x0d:
			return 'ㅜ';
		case 0x0e:
			return 'ㅝ';
		case 0x0f:
			return 'ㅞ';
		case 0x10:
			return 'ㅟ';
		case 0x11:
			return 'ㅠ';
		case 0x12:
			return 'ㅡ';
		case 0x13:
			return 'ㅢ';
		case 0x14:
			return 'ㅣ';
		case 0x15:
			return 'ㅘ';
		}
		return Character.MIN_VALUE;
	}
	
	/**
	 * 종성을 얻어온다.
	 * UTF-8 한글 코드값 = 0xAC00 + (초성값*21*28) + (중성값*28) + 종성값
	 * @param c
	 * @return
	 * @author mulova
	 */
	public static char getFinalConsonant(char c) {
		final int code = (c - 0xAC00)%28;
		switch (code) {
		case 1:
			return 'ㄱ';
		case 2:
			return 'ㄲ';
		case 3:
			return 'ㄳ';
		case 4:
			return 'ㄴ';
		case 5:
			return 'ㄵ';
		case 6:
			return 'ㄶ';
		case 7:
			return 'ㄷ';
		case 8:
			return 'ㄹ';
		case 9:
			return 'ㄺ';
		case 0x0a:
			return 'ㄻ';
		case 0x0b:
			return 'ㄼ';
		case 0x0c:
			return 'ㄽ';
		case 0x0d:
			return 'ㄾ';
		case 0x0e:
			return 'ㄿ';
		case 0x0f:
			return 'ㅀ';
		case 0x10:
			return 'ㅁ';
		case 0x11:
			return 'ㅂ';
		case 0x12:
			return 'ㅄ';
		case 0x13:
			return 'ㅅ';
		case 0x14:
			return 'ㅆ';
		case 0x15:
			return 'ㅇ';
		case 0x16:
			return 'ㅈ';
		case 0x17:
			return 'ㅊ';
		case 0x18:
			return 'ㅋ';
		case 0x19:
			return 'ㅌ';
		case 0x1a:
			return 'ㅍ';
		case 0x1b:
			return 'ㅎ';
		}
		return Character.MIN_VALUE;
	}
	
	
    public static String convUnicode(char character) {
		byte high = (byte) ((character & 0xFF00) >> 8);
		byte low = (byte) (character & 0xFF);

		String unicode = null;
		try {
			unicode = new String(new byte[] { (byte) high, (byte) low },
					"MS949");
		} catch (UnsupportedEncodingException e) {
		}

		return unicode;
	}
}
