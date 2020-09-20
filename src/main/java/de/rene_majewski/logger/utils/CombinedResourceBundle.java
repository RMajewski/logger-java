package de.rene_majewski.logger.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Loads different property files and puts them together in a bundle.
 * 
 * @version 1.0
 * @since 0.1.0
 * @author René Majewski
 */
public class CombinedResourceBundle extends ResourceBundle {
  /**
   * Saves the loaded properties.
   */
  private Map<String, String> combindedResources;
  
  /**
   * List with all names of the property files to be loaded.
   */
  private List<String> bundleNames;

  /**
   * Initializes the class.
   * 
   * @param bundleNames List with all names of the property files to be loaded.
   */
  public CombinedResourceBundle(List<String> bundleNames) {
    this.combindedResources = new HashMap<>();
    this.bundleNames = bundleNames;
    load();
  }

  /**
   * Loads the properties from the specified files.
   */
  public void load() {
    bundleNames.forEach(bundleName -> {
      ResourceBundle bundle = Utf8ResourceBundle.getBundle(bundleName);
      Enumeration<String> keys = bundle.getKeys();
      ArrayList<String> keyList = Collections.list(keys);
      keyList.forEach(key -> {
        combindedResources.put(key, bundle.getString(key));
      });
    });
  }

  @Override
  public Object handleGetObject(String key) {
    return combindedResources.get(key);
  }

  @Override
  public Enumeration<String> getKeys() {
    return Collections.enumeration(combindedResources.keySet());
  }
}
