package ffiras.firas;

import java.util.ArrayList;
import java.util.Scanner;

public class ArithmeticApp {

	static boolean isOp(char c) { // checks if the char is an operation
		return c == '+' || c == '-' || c == '*' || c == '/';
	}
	static boolean isNumber(char c) {// check whether c is DEC digit

		return ((c <= 'F' && c >= 'A') || (c - '0' >= 0 && c - '9' <= 0));

	}
	public static boolean checkSynt(String str, ArrayList<String> err) {// quick syntax check
		for (int i = 0; i < str.length(); i++) {
			if (isNumber(str.charAt(i)) || isOp(str.charAt(i)) || (str.charAt(i) == ' ') || (str.charAt(i) == '\n')) {
				continue;
			} else {
				int j = i;
				String temp = "";
				while (++i < str.length() && isNumber(str.charAt(i))) {
				}

				while (--j > -1 && isNumber(str.charAt(j))) {
				}

				for (; j <= i; j++) {
					temp += str.charAt(j);
				}
				temp.trim();
				err.add(temp);
				return false;
			}
		}
		return true;
	}
	public static int convert(char c) {// conver to DEX
		switch (c) {
		case 'A':
			return 10;
		case 'B':
			return 11;
		case 'C':
			return 12;
		case 'D':
			return 13;
		case 'E':
			return 14;
		case 'F':
			return 15;
		}
		return c - '0';
	}

	public static int dexTohex(String str, int s, int t) {// as the name implies
		int temp = 0;
		while ((s <= t)) {
			temp += (convert(str.charAt(s)) * Math.pow(16, t - s));
			s++;
		}
		return temp;
	}

	public static boolean checkNumbers(String str) {// check the validity of the numbers
		for (int i = 0; i < str.length(); i++) {
			if (isNumber(str.charAt(i))) {
				while (isNumber(str.charAt(i))) {

					i++;
					if (i >= str.length())
						return true;
				}
				while (str.charAt(i) == ' ') {

					i++;
					if (i == str.length())
						return true;
				}
				if (!isOp(str.charAt(i))) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean checkOp(String str, ArrayList<String> err) {// check that every op has two args
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '+' || str.charAt(i) == '-') {
				i++;
				while (i < str.length() && str.charAt(i) == ' ')
					i++;
				if (isNumber(str.charAt(i))) {
					return true;
				} else {
					if (str.charAt(i) == '+' || str.charAt(i) == '-') {
						// maybe to 5s++ 1 if (!nextIsNumber(str, i + 1))
						err.add("");
						return false;
					}
				}
			} else if (str.charAt(i) == '/' || str.charAt(i) == '*') {
				i++;
				while (i < str.length() && str.charAt(i) == ' ')
					i++;
				if (str.charAt(i) == '+' || str.charAt(i) == '-') {
					i++;

					while (i < str.length() && str.charAt(i) == ' ')
						i++;
				}
				if (i >= str.length())
					return false;
				if (!isNumber(str.charAt(i)))
					return false;
			}
		}

		return true;
	}
	public static boolean checkLogic(String str, ArrayList<String> err) {
		if (('/' == str.charAt(0)) || ('*' == str.charAt(0)) || isOp(str.charAt(str.length() - 1)))
			return false;
		return checkNumbers(str) && checkOp(str, err);
	}

	public static int number(String str) {
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			if (isNumber(str.charAt(i))) {
				counter++;
				i++;
				if (i == str.length())
					return counter;
				while (isNumber(str.charAt(i)) || (' ' == str.charAt(i))) {
					i++;
					if (i == str.length())
						return counter;
				}
			}

		}
		return counter;
	}
	static public class cord {
		int start;
		int finish;
		int index;
		cord(int s, int t, int i) {
			start = s;
			finish = t;
			index = i;
		};
	}
	static public void getCords(String str, ArrayList<cord> arrli) {

		int counter = 0;
		int s = 0;
		for (int i = 0; i < str.length(); i++) {
			if (isNumber(str.charAt(i))) {
				s = i;
				while (isNumber(str.charAt(i))) {

					i++;
					if (i == str.length()) {
						break;
					}
				}
				arrli.add(new cord(s, i - 1, counter));
				counter++;
			}

		}

	}
	static public ArrayList<Integer> getArguments(String str, ArrayList<cord> arrli) {
		ArrayList<Integer> arr = new ArrayList<Integer>(arrli.size());
		for (cord points : arrli) {
			arr.add(dexTohex(str, points.start, points.finish));
		}
		return arr;
	}
	static boolean beforeIsop(String temp, int k) {
		for (int i = k - 2; i > 0; i--) {
			if (temp.charAt(i) == ' ')
				continue;
			if (isNumber(temp.charAt(i)))
				return false;
			if (temp.charAt(i) == '/' || temp.charAt(i) == '*' || temp.charAt(i) == '+' || temp.charAt(i) == '-')
				return true;
		}
		return false;
	}
	static boolean nextIsNumber(String str, int j) {
		for (int i = j; i < str.length(); i++) {
			if (str.charAt(i) == ' ')
				continue;
			if (isNumber(str.charAt(i)))
				return true;
			if (str.charAt(i) == '-' || str.charAt(i) == '+' || str.charAt(i) == '/' || str.charAt(i) == '*')
				return false;
		}
		return false;
	}
	static boolean dividedbyzero(String str, int index) {
		int temp = 1;
		int i = index;
		char op = ' ';
		int start = 0;
		while (str.charAt(i) == ' ' || str.charAt(i) == '+' || str.charAt(i) == '-')
			i++;
		start = i;
		while (i < str.length() && isNumber(str.charAt(i)))
			i++;
		temp = dexTohex(str, start, i - 1);
		if (temp == 0) {
			while (i < str.length() && (isNumber(str.charAt(i)) || str.charAt(i) == '/' || str.charAt(i) == '*'
					|| str.charAt(i) == ' '))
				i++;
			System.out.println("Error: trying to divide by 0 (evaluated: \"" + str.substring(index, i).trim() + "\")");
			return true;
		}
		for (; i < str.length(); i++) {

			if (str.charAt(i) == ' ' || str.charAt(i) == '+' || str.charAt(i) == '-')
				continue;
			if (str.charAt(i) == '/' || str.charAt(i) == '*') {
				op = str.charAt(i) == '/' ? '/' : '*';
			}
			if (isNumber(str.charAt(i))) {
				start = i;
				while (i < str.length() && isNumber(str.charAt(i)))
					i++;
				if (op == '*') {
					temp *= dexTohex(str, start, i - 1);
				} else {
					temp /= dexTohex(str, start, i);
				}
			}
			if (temp == 0) {
				while (i < str.length() && (isNumber(str.charAt(i)) || str.charAt(i) == '/' || str.charAt(i) == '*'
						|| str.charAt(i) == ' '))
					i++;

				System.out.println(
						"Error: trying to divide by 0 (evaluated: \"" + str.substring(index, i).trim() + "\")");
				return true;
			}
		}
		return false;
	}
	public static int calcu(String str, ArrayList<Integer> flag) {
		int n = number(str);
		ArrayList<cord> arrli = new ArrayList<cord>(n);
		getCords(str, arrli);
		int chapters = 0;
		ArrayList<String> queue = new ArrayList<String>(n);
	
		int sign = 1;
		for (int i = 0; i < str.length(); i++) {
			String temp = new String();
			if (i == 0 && str.charAt(0) == '-') {
				sign = -1;
				continue;
			} else if (i == 0 && str.charAt(0) == '+') {
				continue;
			}
			int k = 0;
			while (i < str.length()) {
				temp += str.charAt(i);
				k++;
				if (temp.charAt(temp.length() - 1) == '+' || temp.charAt(temp.length() - 1) == '-') {
					if (beforeIsop(temp, k)) {
						i++;
						continue;
					} else {
						temp = (String) temp.subSequence(0, temp.length() - 1);
						break;
					}
				}
				i++;
			}
			if (!temp.isEmpty() && !temp.isBlank()) {
				queue.add(temp);
				chapters++;
			}
		}
		ArrayList<Integer> actual = new ArrayList<Integer>(chapters);
		int temp = 0;
		int i = 0;
		for (i = 0; i < queue.size(); i++) {
			String tstr = queue.get(i);
			int j = 0;
			int start = -1;
			int finish = -1;
			while (j < tstr.length() && tstr.charAt(j) != '\n') {

				if (tstr.charAt(j) == ' ') {
					j++;
					continue;
				}
				if (isNumber(tstr.charAt(j))) {
					start = finish = j;
					while (finish < tstr.length() && (tstr.charAt(j) != ' ' && !isOp(tstr.charAt(j)))) {
						finish++;
						j++;
					}

					temp = dexTohex(queue.get(i), start, finish - 1);
					if (sign == -1) {
						temp *= -1;
						sign = 1;
					}
					continue;
				} else if (isOp(tstr.charAt(j))) {
					if (tstr.charAt(j) == '*') {
						while (j < tstr.length() && !(isNumber(tstr.charAt(j)))) {
							if (tstr.charAt(j) == '-') {
								sign = -1;
							}
							j++;

						}
						start = finish = j;
						while (finish < tstr.length() && (tstr.charAt(j) != ' ' && !isOp(tstr.charAt(j)))) {
							finish++;
							j++;
						}
						temp *= (dexTohex(queue.get(i), start, finish - 1) * sign);
						continue;
					} else if (tstr.charAt(j) == '/') {
						if (dividedbyzero(queue.get(i), j + 1)) {

							flag.add(1);
							return 0;

						}
						while (j < tstr.length() && !(isNumber(tstr.charAt(j)))) {

							if (tstr.charAt(j) == '-') {
								sign = -1;
							}
							j++;

						}
						start = finish = j;
						while (finish < tstr.length() && (tstr.charAt(j) != ' ' && !isOp(tstr.charAt(j)))) {
							finish++;
							j++;
						}
						if (dexTohex(queue.get(i), start, finish - 1) == 0) {

							flag.add(1);
							return 0;
						}
						temp /= (dexTohex(queue.get(i), start, finish - 1) * sign);
						if (sign == -1) {

							sign = 1;
						}
						continue;
					}
				}
				j++;
			}
			actual.add(temp);
			temp = 0;
		}
		int j = 0;
		sign = 1;
		temp += (actual.get(0));
		j = queue.get(0).length();
		for (i = 1; i < actual.size(); i++) {
			if (actual.get(i) == 0 || actual.get(i) == null)
				continue;
			while (j < str.length()) {
				if (str.charAt(j) == '+' || str.charAt(j) == '-') {
					if (!beforeIsop(str, j) && nextIsNumber(str, j)) {
						sign = str.charAt(j) == '+' ? 1 : -1;
						break;

					} else if (beforeIsop(str, j)) {
						if (str.charAt(j) == '+') {
							j++;
							continue;
						}
						if (str.charAt(j) == '-')
							sign = -1;
						j++;
						break;

					} else if (nextIsNumber(str, j + 1)) {
						sign = (str.charAt(j) == '+' ? 1 : -1);
						j++;
						break;
					} else {
						sign = str.charAt(j) == '+' ? 1 : -1;
						j++;
						break;
					}
				}
				j++;
			}
			temp += (actual.get(i) * sign);
		}
		flag.add(0);
		return temp;
	}
	public static void main(String[] args) {
		Scanner myObj = new Scanner(System.in);
		System.out.println("Enter expression:");
		String str/* =args[0] */;
		str = myObj.nextLine();
		str = str.trim();
		ArrayList<String> err = new ArrayList<String>(1);
		if (false == checkSynt(str, err) || false == checkLogic(str, err)) {
			System.out.println("Error: invalid expression: \"" + err.get(0).trim() + "\"");
			myObj.close();
		} else {
			ArrayList<Integer> flag = new ArrayList<Integer>(1);
			int ans = calcu(str, flag);
			if (flag.get(0) == 1) {
				// System.out.println("Error: trying to divide sdfby 0 (evaluated: \"0\")");
			} else {// The value of expression 1C + 2D + 3F - 4E is: 3A ( 58 )
				System.out.println("The value of expression " + str + " is: "
						+ Integer.toHexString(ans).toUpperCase().toUpperCase() + " ( "
						+ (Integer.toString(ans, 10)).toUpperCase() + " )");

			}
			myObj.close();
		}
	}

}
