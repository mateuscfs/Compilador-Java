import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aloha {

	static String num = "01234567890";
	static String letra = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static String[] keywords = { "private", "protected", "public", "abstract", "class", "extends", "final",
			"implements", "interface", "native", "new", "static", "strictfp", "synchronized", "transient", "volatile",
			"break", "case", "continue", "default", "do", "else", "for", "if", "instanceof", "return", "switch",
			"while", "assert", "catch", "finally", "throw", "throws", "try", "import", "package", "boolean", "byte",
			"String", "char", "double", "float", "int", "long", "short", "super", "this", "void", "const", "goto",
			"null", "true", "false" };
	// Length Array 53

	public static void main(String[] args) {
		Scanner leia = new Scanner(System.in);

		String nomeArquivo;
		System.out.println("Arquivo para leitura -> Testes_ScannerAAI.txt\n");
		nomeArquivo = "src/Testes_ScannerAAI.txt";

		leia.close();

		lerTexto(nomeArquivo);

	}

	public static void lerTexto(String nomeArquivo) {
		try {
			File arquivo = new File(nomeArquivo);
			FileReader fileReader = new FileReader(arquivo);
			BufferedReader reader = new BufferedReader(fileReader);
			PrintWriter writer = new PrintWriter("src/saida.txt", "UTF-8");
			String data = null;
			data = reader.readLine();

			while (data != null) {
				verifica(data, writer);
				data = reader.readLine();
			}

			writer.close();
			fileReader.close();
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean contem(String data, String reservada) {

		String pattern = "\\b" + reservada + "\\b";
		String pattern2 = "\\s+" + reservada + "\\s+";
		String pattern3 = "//" + reservada + "\\b";
		String pattern4 = "//" + reservada;
		String pattern5 = "//.*\\b" + reservada + "\\b";
		String pattern6 = "//\\s+" + reservada;
		String pattern7 = "/<" + reservada + "</";
		String pattern8 = "/<\\s+" + reservada + "</";
		String pattern9 = "/<" + reservada + "\\s+</";
		String pattern10 = "/<.*\\s+" + reservada + ".*\\s+</";

		Pattern pat = Pattern.compile(pattern);
		Pattern pat2 = Pattern.compile(pattern2);
		Pattern pat3 = Pattern.compile(pattern3);
		Pattern pat4 = Pattern.compile(pattern4);
		Pattern pat5 = Pattern.compile(pattern5);
		Pattern pat6 = Pattern.compile(pattern6);
		Pattern pat7 = Pattern.compile(pattern7);
		Pattern pat8 = Pattern.compile(pattern8);
		Pattern pat9 = Pattern.compile(pattern9);
		Pattern pat10 = Pattern.compile(pattern10);

		String data2 = data.replace('*', '<');

		Matcher mat = pat.matcher(data2);
		Matcher mat2 = pat2.matcher(data2);
		Matcher mat3 = pat3.matcher(data2);
		Matcher mat4 = pat4.matcher(data2);
		Matcher mat5 = pat5.matcher(data2);
		Matcher mat6 = pat6.matcher(data2);
		Matcher mat7 = pat7.matcher(data2);
		Matcher mat8 = pat8.matcher(data2);
		Matcher mat9 = pat9.matcher(data2);
		Matcher mat10 = pat10.matcher(data2);

		boolean find = false;

		if ((mat.find() || mat2.find()) && !mat3.find() && !mat4.find() && !mat5.find() && !mat6.find() && !mat7.find()
				&& !mat8.find() && !mat9.find() && !mat10.find()) {
			find = true;
		}

		return find;

	}

	public static boolean verifica(String data, PrintWriter writer)
			throws FileNotFoundException, UnsupportedEncodingException {

		int i = 0, no = 0;

		System.out.println(data);
		writer.println(data);

		for (int x = 0; x < keywords.length; x++) {
			if (contem(data, keywords[x])) {
				System.out.println("String inválida!!! Possui palavra reservada!\n");
				writer.println("String invalida!!! Possui palavra reservada!\n");
				return false;
			}
		}

		while (no != 30) {
			switch (no) {
			case 0:
				while (i < data.length() && data.charAt(i) == ' ') {
					i++;
				}

				if (i == data.length()) {
					no = 30;
					System.out.println("String válida!!!\n");
					writer.println("String válida!!!\n");
				} else if (data.charAt(i) == '/') {
					i++;
					no = 7;
				} else if (valida(data.charAt(i), letra)) {
					i++;
					no = 1;
				} else {
					System.out.println("String invalida!!! Estado 0\n");
					writer.println("String invalida!!! Estado 0\n");
					return false;
				}

				break;

			case 1:
				while (i < data.length()
						&& (valida(data.charAt(i), letra) == true || valida(data.charAt(i), num) == true)) {
					i++;
				}
				if (i < data.length() && data.charAt(i) == ' ') {
					i++;
					no = 2;
				} else if (i < data.length() && data.charAt(i) == '=') {
					i++;
					no = 3;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 10;
				} else {
					System.out.println("String invalida!!! Estado 1\n");
					writer.println("String invalida!!! Estado 1\n");
					return false;
				}

				break;

			case 2:
				while (i < data.length() && data.charAt(i) == ' ') {
					i++;
				}
				if (i < data.length() && data.charAt(i) == '=') {
					i++;
					no = 3;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 10;
				} else {
					System.out.println("String invalida!!! Estado 2\n");
					writer.println("String invalida!!! Estado 2\n");
					return false;
				}

				break;

			case 3:
				while (i < data.length() && data.charAt(i) == ' ') {
					i++;
				}
				if (i < data.length() && valida(data.charAt(i), num)) {
					i++;
					no = 4;
				} else if (i < data.length() && valida(data.charAt(i), letra)) {
					i++;
					no = 5;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 13;
				} else if (i < data.length() && data.charAt(i) == '+') {
					i++;
					no = 24;
				} else if (i < data.length() && data.charAt(i) == '-') {
					i++;
					no = 24;
				} else {
					System.out.println("String invalida!!! Estado 3\n");
					writer.println("String invalida!!! Estado 3\n");
					return false;
				}

				break;

			case 4:
				while (i < data.length() && valida(data.charAt(i), num)) {
					i++;
				}
				if (i < data.length() && data.charAt(i) == ' ') {
					i++;
					no = 6;
				} else if (i < data.length() && data.charAt(i) == '*') {
					i++;
					no = 25;
				} else if (i < data.length() && data.charAt(i) == '+') {
					i++;
					no = 26;
				} else if (i < data.length() && data.charAt(i) == '-') {
					i++;
					no = 27;
				} else if (i < data.length() && data.charAt(i) == ';') {
					i++;
					no = 19;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 16;
				} else if (i < data.length() && data.charAt(i) == '.') {
					i++;
					no = 28;
				} else if (i < data.length() && data.charAt(i) == '%') {
					i++;
					no = 3;
				} else {
					System.out.println("String invalida!!! Estado 4\n");
					writer.println("String invalida!!! Estado 4\n");
					return false;
				}

				break;

			case 5:
				while (i < data.length() && (valida(data.charAt(i), letra) || valida(data.charAt(i), num))) {
					i++;
				}
				if (i < data.length() && data.charAt(i) == ' ') {
					i++;
					no = 6;
				} else if (i < data.length() && data.charAt(i) == '*') {
					i++;
					no = 25;
				} else if (i < data.length() && data.charAt(i) == '+') {
					i++;
					no = 26;
				} else if (i < data.length() && data.charAt(i) == '-') {
					i++;
					no = 27;
				} else if (i < data.length() && data.charAt(i) == ';') {
					i++;
					no = 19;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 16;
				} else if (i < data.length() && data.charAt(i) == '%') {
					i++;
					no = 3;
				} else {
					System.out.println("String invalida!!! Estado 5\n");
					writer.println("String invalida!!! Estado 5\n");
					return false;
				}

				break;

			case 6:

				if (i < data.length() && data.charAt(i) == ' ') {
					i++;
					no = 6;
				} else if (i < data.length() && data.charAt(i) == '*') {
					i++;
					no = 25;
				} else if (i < data.length() && data.charAt(i) == '+') {
					i++;
					no = 26;
				} else if (i < data.length() && data.charAt(i) == '-') {
					i++;
					no = 27;
				} else if (i < data.length() && data.charAt(i) == ';') {
					i++;
					no = 19;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 16;
				} else if (i < data.length() && data.charAt(i) == '%') {
					i++;
					no = 3;
				} else {
					System.out.println("String invalida!!! Estado 6\n");
					writer.println("String invalida!!! Estado 6\n");
					return false;
				}

				break;

			case 7:
				if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 21;
				} else if (i < data.length() && data.charAt(i) == '*') {
					i++;
					no = 8;
				} else {
					System.out.println("String invalida!!! Estado 7\n");
					writer.println("String invalida!!! Estado 7\n");
					return false;
				}

				break;

			case 8:
				while (i < data.length() && data.charAt(i) != '*') {
					i++;
				}
				if (i < data.length() && data.charAt(i) == '*') {
					i++;
					no = 9;
				} else {
					System.out.println("String invalida!!! Estado 8\n");
					writer.println("String invalida!!! Estado 8\n");
					return false;
				}

				break;

			case 9:
				if (i < data.length() && data.charAt(i) != '/') {
					i++;
					no = 8;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 0;
				} else {
					System.out.println("String invalida!!! Estado 9\n");
					writer.println("String invalida!!! Estado 9\n");
					return false;
				}

				break;

			case 10:
				if (i < data.length() && data.charAt(i) == '*') {
					i++;
					no = 11;
				} else {
					System.out.println("String invalida!!! Estado 10\n");
					writer.println("String invalida!!! Estado 10\n");
					return false;
				}

				break;

			case 11:
				while (i < data.length() && data.charAt(i) != '*') {
					i++;
				}
				if (i < data.length() && data.charAt(i) == '*') {
					i++;
					no = 12;
				} else {
					System.out.println("String invalida!!! Estado 11\n");
					writer.println("String invalida!!! Estado 11\n");
					return false;
				}

				break;

			case 12:
				if (i < data.length() && data.charAt(i) != '/') {
					i++;
					no = 11;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 2;
				} else {
					System.out.println("String invalida!!! Estado 12\n");
					writer.println("String invalida!!! Estado 12\n");
					return false;
				}

				break;

			case 13:
				if (i < data.length() && data.charAt(i) == '*') {
					i++;
					no = 14;
				} else {
					System.out.println("String invalida!!! Estado 13\n");
					writer.println("String invalida!!! Estado 13\n");
					return false;
				}

				break;

			case 14:
				while (i < data.length() && data.charAt(i) != '*') {
					i++;
				}
				if (i < data.length() && data.charAt(i) == '*') {
					i++;
					no = 15;
				} else {
					System.out.println("String invalida!!! Estado 14\n");
					writer.println("String invalida!!! Estado 14\n");
					return false;
				}

				break;

			case 15:
				if (i < data.length() && data.charAt(i) != '/') {
					i++;
					no = 14;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 3;
				} else {
					System.out.println("String invalida!!! Estado 15\n");
					writer.println("String invalida!!! Estado 15\n");
					return false;
				}

				break;

			case 16:
				if (i < data.length() && valida(data.charAt(i), num)) {
					i++;
					no = 4;
				} else if (i < data.length() && valida(data.charAt(i), letra)) {
					i++;
					no = 5;
				} else if (i < data.length() && data.charAt(i) == ' ') {
					i++;
					no = 3;
				} else if (i < data.length() && data.charAt(i) == '*') {
					i++;
					no = 17;
				} else if (i < data.length() && data.charAt(i) == '+') {
					i++;
					no = 24;
				} else if (i < data.length() && data.charAt(i) == '-') {
					i++;
					no = 24;
				} else {
					System.out.println("String invalida!!! Estado 16\n");
					writer.println("String invalida!!! Estado 16\n");
					return false;
				}

				break;

			case 17:
				while (i < data.length() && data.charAt(i) != '*') {
					i++;
				}
				if (i < data.length() && data.charAt(i) == '*') {
					i++;
					no = 18;
				} else {
					System.out.println("String invalida!!! Estado 17\n");
					writer.println("String invalida!!! Estado 17\n");
					return false;
				}

				break;

			case 18:
				if (i < data.length() && data.charAt(i) != '/') {
					i++;
					no = 17;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 6;
				} else {
					System.out.println("String invalida!!! Estado 18\n");
					writer.println("String invalida!!! Estado 18\n");
					return false;
				}

				break;

			case 19:
				if (data.length() == i) {
					no = 30;
					System.out.println("String válida!!!\n");
					writer.println("String válida!!!\n");
				} else if (data.charAt(i) == '/') {
					i++;
					no = 20;
				} else if (data.charAt(i) == ' ') {
					i++;
					no = 19;
				} else {
					System.out.println("String invalida!!! Estado 19\n");
					writer.println("String invalida!!! Estado 19\n");
					return false;
				}

				break;

			case 20:
				if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 21;
				} else if (i < data.length() && data.charAt(i) == '*') {
					i++;
					no = 22;
				} else {
					System.out.println("String invalida!!! Estado 20\n");
					writer.println("String invalida!!! Estado 20\n");
					return false;
				}

				break;

			case 21:
				while (data.length() != i) {
					i++;
				}
				no = 30;
				System.out.println("String válida!!!\n");
				writer.println("String válida!!!\n");
				break;

			case 22:
				while (i < data.length() && data.charAt(i) != '*') {
					i++;
				}
				if (i < data.length() && data.charAt(i) == '*') {
					i++;
					no = 23;
				} else {
					System.out.println("String invalida!!! Estado 22\n");
					writer.println("String invalida!!! Estado 22\n");
					return false;
				}

				break;

			case 23:
				if (i < data.length() && data.charAt(i) != '/') {
					i++;
					no = 22;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 19;
				} else {
					System.out.println("String invalida!!! Estado 23\n");
					writer.println("String invalida!!! Estado 23\n");
					return false;
				}

				break;

			case 24:
				if (i < data.length() && data.charAt(i) == ' ') {
					i++;
					no = 3;
				} else if (i < data.length() && valida(data.charAt(i), num)) {
					i++;
					no = 4;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 13;
				} else if (i < data.length() && valida(data.charAt(i), letra)) {
					i++;
					no = 5;
				} else {
					System.out.println("String invalida!!! Estado 24\n");
					writer.println("String invalida!!! Estado 24\n");
					return false;
				}

				break;

			case 25:
				if (i < data.length() && data.charAt(i) == '+' || data.charAt(i) == '-') {
					i++;
					no = 24;
				} else if (i < data.length() && data.charAt(i) == ' ') {
					i++;
					no = 3;
				} else if (i < data.length() && valida(data.charAt(i), num)) {
					i++;
					no = 4;
				} else if (i < data.length() && valida(data.charAt(i), letra)) {
					i++;
					no = 5;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 13;
				} else {
					System.out.println("String invalida!!! Estado 25\n");
					writer.println("String invalida!!! Estado 25\n");
					return false;
				}

				break;

			case 26:
				if (i < data.length() && data.charAt(i) == '-') {
					i++;
					no = 24;
				} else if (i < data.length() && data.charAt(i) == ' ') {
					i++;
					no = 3;
				} else if (i < data.length() && valida(data.charAt(i), num)) {
					i++;
					no = 4;
				} else if (i < data.length() && valida(data.charAt(i), letra)) {
					i++;
					no = 5;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 13;
				} else {
					System.out.println("String invalida!!! Estado 26\n");
					writer.println("String invalida!!! Estado 26\n");
					return false;
				}

				break;

			case 27:
				if (i < data.length() && data.charAt(i) == '+') {
					i++;
					no = 24;
				} else if (i < data.length() && data.charAt(i) == ' ') {
					i++;
					no = 3;
				} else if (i < data.length() && valida(data.charAt(i), num)) {
					i++;
					no = 4;
				} else if (i < data.length() && valida(data.charAt(i), letra)) {
					i++;
					no = 5;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 13;
				} else {
					System.out.println("String invalida!!! Estado 27\n");
					writer.println("String invalida!!! Estado 27\n");
					return false;
				}

				break;

			case 28:
				if (i < data.length() && valida(data.charAt(i), num)) {
					i++;
					no = 29;
				} else {
					System.out.println("String invalida!!! Estado 27\n");
					writer.println("String invalida!!! Estado 27\n");
					return false;
				}

				break;

			case 29:
				if (i < data.length() && valida(data.charAt(i), num) ) {
					i++;
					no = 29;
				} else if (i < data.length() && data.charAt(i) == '+') {
					i++;
					no = 26;
				} else if (i < data.length() && data.charAt(i) == ' ') {
					i++;
					no = 6;
				} else if (i < data.length() && data.charAt(i) == '-') {
					i++;
					no = 27;
				} else if (i < data.length() && data.charAt(i) == ';') {
					i++;
					no = 19;
				} else if (i < data.length() && data.charAt(i) == '*') {
					i++;
					no = 25;
				} else if (i < data.length() && data.charAt(i) == '/') {
					i++;
					no = 16;
				} else {
					System.out.println("String invalida!!! Estado 27\n");
					writer.println("String invalida!!! Estado 27\n");
					return false;
				}
				
				break;

			}

		}

		return false;

	}

	public static boolean valida(char letra, String str) {

		int j = 0;
		boolean ret = false;

		while (j < str.length() && !ret) {
			if (str.charAt(j) == letra) {
				ret = true;

			}
			j++;
		}

		return ret;

	}

}
