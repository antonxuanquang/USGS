import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Model {
	public Model() {
	}

	public void fetchData(BufferedWriter bw, String site, String firstPart, String secondPart) throws Exception {
		URL url = new URL(firstPart + site + secondPart);
		URLConnection conn = url.openConnection();

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		int count = 0;
		String inputLine;
		while ((inputLine = br.readLine()) != null) {
			inputLine = preProcess(inputLine);
			if (inputLine.length() > 0) {
				inputLine = postProcess(inputLine, 9);
				count++;
				bw.write(inputLine);
			}
		}
		br.close();
	}

	public void fetchDescription(BufferedWriter bw, String site, String firstPart, String secondPart) throws Exception {
		URL url = new URL(firstPart + site + secondPart);
		URLConnection conn = url.openConnection();

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		int count = 0;
		String inputLine;
		while ((inputLine = br.readLine()) != null) {
			inputLine = preProcess(inputLine);
			if (inputLine.length() > 0) {
				inputLine = postProcess(inputLine, 56);
				count++;

				bw.write(inputLine);
			}
		}
		br.close();
	}

	private String postProcess(String inputLine, int column) {
		int count = 0;
		String[] components = inputLine.split("@");
		String[] result = new String[column];
		while (count < column) {
			if ((count < components.length) && (components[count].length() > 0)) {
				String component = components[count].replaceAll(",", "");
				result[count] = component;
			} else {
				result[count] = "null";
			}
			count++;
		}
		return String.join(",", result) + "\n";
	}

	private String preProcess(String inputLine) {
		String[] components = inputLine.split("\t");
		if (!components[0].equals("USGS"))
			return "";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < inputLine.length(); i++) {
			char c = inputLine.charAt(i);
			if (c == '\t')
				builder.append("@");
			else
				builder.append(c);
		}
		return builder.toString();
	}
}