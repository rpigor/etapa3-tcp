package app.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UIUtils {

	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final UIUtils INSTANCE = new UIUtils();
	public static final String PROPERTY_RESOURCE_BUNDLE = "bank.resources.globalMessages";

	private final BufferedReader reader;
	private final SimpleDateFormat sdf;
	private final SimpleDateFormat sdtf;

	private UIUtils() {
		this.reader = new BufferedReader(new InputStreamReader(System.in));
		this.sdf = new SimpleDateFormat(DATE_FORMAT);
		this.sdtf = new SimpleDateFormat(DATE_TIME_FORMAT);
	}

	public String formatDateTime(Date value) {
		return sdtf.format(value);
	}

	public void handleUnexceptedError(Exception e) {
		e.printStackTrace();
		System.exit(-1);
	}

	public Date readDate() {
		return readDate(false);
	}

	public Date readDate(boolean allowsEmpty) {
		Date value = null;
		while (value == null) {
			try {
				String str = reader.readLine();
				if ((str == null || str.isEmpty()) && allowsEmpty) {
					return null;
				} else {
					value = sdf.parse(str);
				}
			} catch (ParseException pe) {
				System.out.println("Uma data é esperada.");
			} catch (Exception e) {
				handleUnexceptedError(e);
			}
		}
		return value;
	}

	public Double readDouble() {
		Double value = null;
		while (value == null) {
			try {
				value = new Double(reader.readLine());
			} catch (NumberFormatException nfe) {
				System.out.println("Um valor numérico é esperado.");
			} catch (Exception e) {
				handleUnexceptedError(e);
			}
		}
		return value;
	}

	public Integer readInteger() {
		Integer value = null;
		while (value == null) {
			try {
				value = new Integer(reader.readLine());
			} catch (NumberFormatException nfe) {
				System.out.println("Um valor numérico inteiro é esperado.");
			} catch (Exception e) {
				handleUnexceptedError(e);
			}
		}
		return value;
	}

	public Integer readInteger(int min, int max) {
		Integer value = null;
		while (value == null) {
			value = readInteger();
			if (value < min || value > max) {
				value = null;
				System.out.println("O inteiro deve ser entre " + min + " e " + max + ".");
			}
		}
		return value;
	}

	public Long readLong() {
		Long value = null;
		while (value == null) {
			try {
				value = new Long(reader.readLine());
			} catch (NumberFormatException nfe) {
				System.out.println("Um valor numérico inteiro é esperado.");
			} catch (Exception e) {
				handleUnexceptedError(e);
			}
		}
		return value;
	}

	public String readString() {
		String value = null;
		while (value == null) {
			try {
				value = reader.readLine();
			} catch (Exception e) {
				handleUnexceptedError(e);
			}
		}
		return value;
	}

}