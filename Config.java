//
//

import javax.swing.border.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
// import javax.swing.plaf.basic.*;
import java.io.*;
import java.sql.*;

/************************************************************
 * Сохраняем, восстанавливаем и работаем с конфигом программы
 ***********************************************************/
public class Config {
	private String FCF = "GenPass.cfg";
	// Параметры по умолчанию
	private String PASS_COUNT         = "10";
	private String PASS_LEN           = "8";
	private String BIG_CHAR_ENABLED   = "false";
	private String SMALL_CHAR_ENABLED = "false";
	private String DIGITS_ENABLED     = "true";
	private String SPEC_SYMB_ENABLED  = "false";
	
	/**
	 * Конструктор
	 */
	public Config() {
		File file = new File(FCF);
		// Ищем файл конфига - если нет, создаем, пишим туда знач по умолч.
		if(!file.exists() && !file.isFile()) {
			saveParam();
		}
		// Загружаем параметры
		loadParam();
	}

	/**
	 * Сохраняем конфиг программы в файл
	 */
	public void saveParam() {
		Properties prop = new Properties();
		OutputStream output = null;

		try {
			output = new FileOutputStream(FCF);
			prop.setProperty("PASS_COUNT",         PASS_COUNT);
			prop.setProperty("PASS_LEN",           PASS_LEN);
			prop.setProperty("BIG_CHAR_ENABLED",   BIG_CHAR_ENABLED);
			prop.setProperty("SMALL_CHAR_ENABLED", SMALL_CHAR_ENABLED);
			prop.setProperty("DIGITS_ENABLED",     DIGITS_ENABLED);
			prop.setProperty("SPEC_SYMB_ENABLED",  SPEC_SYMB_ENABLED);
			prop.store(output, null);
		} catch (IOException io) { io.printStackTrace(); }
		finally {
			if(output != null) {
				try {
					output.close();
				} catch(IOException e) { e.printStackTrace(); }
			}
		}
	} // END saveParam
	
	/**
	 * Читаем конфиг рограммы из файла
	 */
	 private void loadParam() {
		Properties prop = new Properties();
		InputStream input = null;
		 
		try {
			input = new FileInputStream(FCF);
			prop.load(input);
			PASS_COUNT         = prop.getProperty("PASS_COUNT");
			PASS_LEN           = prop.getProperty("PASS_LEN");
			BIG_CHAR_ENABLED   = prop.getProperty("BIG_CHAR_ENABLED");
			SMALL_CHAR_ENABLED = prop.getProperty("SMALL_CHAR_ENABLED");
			DIGITS_ENABLED     = prop.getProperty("DIGITS_ENABLED");
			SPEC_SYMB_ENABLED  = prop.getProperty("SPEC_SYMB_ENABLED");
		} catch(IOException io) { io.printStackTrace(); }
		finally {
			if(input != null) {
				try {
					input.close();
				} catch(IOException e) { e.printStackTrace(); }
			}
		}
	} // END loadParam

	// * * * * * *
	
	public String getPassCount() {
		return PASS_COUNT;
	}
	
	public String getPassLen() {
		return PASS_LEN;
	}
	
	public String getBigCharEnabled() {
		return BIG_CHAR_ENABLED;
	}
	
	public String getSmallCharEnabled() {
		return SMALL_CHAR_ENABLED;
	}
	
	public String getDigitsEnabled() {
		return DIGITS_ENABLED;
	}
	
	public String getSpecSymbEnabled() {
		return SPEC_SYMB_ENABLED;
	}
	
	// * * * * * *
	
	public void setPassCount(String passCount) {
		this.PASS_COUNT = passCount;
	}
	
	public void setPassLen(String passLen) {
		this.PASS_LEN = passLen;
	}
	
	public void setBigCharEnabled(String bigCharEnabled) {
		this.BIG_CHAR_ENABLED = bigCharEnabled;
	}
	
	public void setSmallCharEnabled(String smallCharEnabled) {
		this.SMALL_CHAR_ENABLED = smallCharEnabled;
	}
	
	public void setDigitsEnabled(String digitsEnabled) {
		this.DIGITS_ENABLED = digitsEnabled;
	}
	
	public void setSpecSymbEnabled(String specSymbEnabled) {
		this.SPEC_SYMB_ENABLED = specSymbEnabled;
	}
} // ConfParam
