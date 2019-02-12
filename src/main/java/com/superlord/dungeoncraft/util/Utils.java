package com.superlord.dungeoncraft.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
	
	private static Logger logger;
	
	public static Logger getLogger() {

		if (logger == null) {

			logger = LogManager.getFormatterLogger(Reference.MOD_ID);

		}

		return logger;

	}
	
}
