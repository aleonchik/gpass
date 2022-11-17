/*
 * @author Alexey Leonchik
 * <alexey@leonchik.ru>
 * 
 * Сб. 23 июля 2016
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Password {
	private String strSrc;
	private String strPass;
	private int    lenPass;
	
	Password(String strSrc, int lenPass) {
		this.strSrc =  strSrc;
		this.lenPass = lenPass;
	}
	
	private void generatePass() {
		Random rand = new Random(System.nanoTime());
		strPass = "";
		int r, k;
		
		k = strSrc.length();
		
		for(int i = 0; i < lenPass; i++) {
			r = rand.nextInt(k);
			strPass = strPass + strSrc.charAt(r);
			// System.out.println(strSrc.charAt(r));
			// System.out.println("r: " + r + " r + 1: " + (r + 1));
		}
	}
	
	public String getPass() {
		generatePass();
		return strPass;
	}

}
