package com.cyspan.tap.commons;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConfigMessages {
	private static final String BUNDLE_NAME = "config";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private ConfigMessages() {
    }

    public static String getString(String key) {
       
        try {
            return RESOURCE_BUNDLE.getString(key);
			
        } 
		catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
