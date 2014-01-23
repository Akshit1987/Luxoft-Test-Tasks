import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LuxoftTask2 {
	private static final int RECORDS_COUNT = 10;

	public static void main(String[] args) {

		ArrayList<StudentRecord> records = null;

		try {
			records = getStudentRecords(RECORDS_COUNT);
		} catch (IOException err) {
			System.out.printf("Error: %s, exiting..", err.getMessage());
			System.exit(1);
		}
		Collections.sort(records);
		removeDublicatesRecords(records);
		printRecords(records);
		System.exit(0);
	}

	public static ArrayList<StudentRecord> getStudentRecords(int recordsCount)
			throws IOException {
		ArrayList<StudentRecord> ret = new ArrayList<StudentRecord>(
				recordsCount);
		InputStreamReader istream = new InputStreamReader(System.in);
		BufferedReader bufRead = new BufferedReader(istream);
		String record = null;
		do {
			System.out.printf("Enter %d student record, or 0 to exit: ",
					ret.size() + 1);

			try {
				record = bufRead.readLine();
				if (record.equals("0"))
					break;
				StudentRecord sr = new StudentRecord(record);
				ret.add(sr);
			} catch (IOException err) {
				System.out.println("Error reading line");
			} catch (InputMismatchException err) {
				System.out.println("Error: Bad format of input");
			}

		} while (ret.size() < recordsCount);

		if (ret.size() < recordsCount)
			throw new IOException("User canceled input");

		return ret;
	}

	public static class StudentRecord implements Comparable<StudentRecord> {
		private int id;
		private int score;
		private static Pattern pattern = null;

		public StudentRecord(String input) throws InputMismatchException {
			if (pattern == null)
				pattern = Pattern
						.compile("^([0-9]{4})-([1-9]|[1-9][0-9]|100)$");

			try {
				setRecordFromString(input);
			} catch (InputMismatchException err) {
				throw err;
			}
		}

		private void setRecordFromString(String input)
				throws InputMismatchException {
			if (!isCorrectStringFormat(input)) {
				throw new InputMismatchException();
			} else {
				setIdAndScoreFromString(input);
			}
		}

		private boolean isCorrectStringFormat(String input) {
			Matcher m = pattern.matcher(input);
			return m.matches();
		}

		private void setIdAndScoreFromString(String input) {
			Matcher m = pattern.matcher(input);
			m.find();
			id = Integer.parseInt(m.group(1));
			score = Integer.parseInt(m.group(2));
		}

		public int getHash() {
			return id;
		}

		@Override
		public String toString() {
			return String.format("%d-%d", id, score);
		}

		@Override
		public int compareTo(StudentRecord o) {
			return score == o.score ? 0 : o.score - score;
		}

	}

	public static void removeDublicatesRecords(ArrayList<StudentRecord> records) {
		ArrayList<Integer> hashes = new ArrayList<Integer>();
		Iterator<StudentRecord> itRec = records.iterator();
		while (itRec.hasNext()) {
			Integer recordHash = itRec.next().getHash();
			if (hashes != null && hashes.contains(recordHash)) {
				itRec.remove();
			} else {
				hashes.add(recordHash);
			}
		}
	}

	public static void printRecords(ArrayList<StudentRecord> records) {
		System.out.println("Students sorted by the top score:");
		for (StudentRecord record : records) {
			System.out.println(record);
		}
	}

}
