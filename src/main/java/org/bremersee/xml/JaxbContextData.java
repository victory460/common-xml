/*
 * Copyright 2018-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bremersee.xml;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlSchema;

/**
 * Meta data to describe a xml model generated by xjb.
 *
 * @author Christian Bremer
 */
public class JaxbContextData implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nameSpace;

  private String schemaLocation;

  private String packageName;

  /**
   * Instantiates new jaxb meta data.
   *
   * @param packageName the package name
   */
  public JaxbContextData(final String packageName) {
    this(Package.getPackage(packageName), null, null);
  }

  /**
   * Instantiates new jaxb meta data.
   *
   * @param packageName the package name
   * @param nameSpace the name space (can be empty or {@code null})
   * @param schemaLocation the schema location (can be {@code null})
   */
  public JaxbContextData(
      final String packageName,
      final String nameSpace,
      final String schemaLocation) {
    this(Package.getPackage(packageName), nameSpace, schemaLocation);
  }

  /**
   * Instantiates new jaxb meta data.
   *
   * @param pakkage the package
   */
  public JaxbContextData(final Package pakkage) {
    this(pakkage, null, null);
  }

  /**
   * Instantiates new jaxb meta data.
   *
   * @param pakkage the package
   * @param schemaLocation the schema location (can be {@code null})
   */
  public JaxbContextData(
      final Package pakkage,
      final String schemaLocation) {
    this(pakkage, null, schemaLocation);
  }

  /**
   * Instantiates new jaxb meta data.
   *
   * @param pakkage the package
   * @param nameSpace the name space (can be empty or {@code null})
   * @param schemaLocation the schema location (can be {@code null})
   */
  JaxbContextData(
      final Package pakkage,
      final String nameSpace,
      final String schemaLocation) {

    if (pakkage == null) {
      throw new IllegalArgumentException("Package must be present.");
    }
    this.packageName = pakkage.getName();
    final XmlSchema xmlSchema = pakkage.getAnnotation(XmlSchema.class);
    if (nameSpace != null) {
      this.nameSpace = nameSpace.trim();
    } else if (xmlSchema != null) {
      this.nameSpace = xmlSchema.namespace().trim();
    } else {
      this.nameSpace = "";
    }
    if (schemaLocation != null) {
      this.schemaLocation = XmlSchema.NO_LOCATION.equals(schemaLocation.trim())
          ? null
          : schemaLocation.trim().length() == 0 ? null : schemaLocation.trim();
    } else if (xmlSchema != null) {
      this.schemaLocation = XmlSchema.NO_LOCATION.equals(xmlSchema.location().trim())
          ? null
          : xmlSchema.location().trim().length() == 0 ? null : xmlSchema.location().trim();
    } else {
      this.schemaLocation = null;
    }
  }

  /**
   * Gets name space.
   *
   * @return the name space
   */
  public String getNameSpace() {
    return nameSpace;
  }

  /**
   * Gets schema location.
   *
   * @return the schema location
   */
  public String getSchemaLocation() {
    return schemaLocation;
  }

  /**
   * Get the package name of the xml model.
   *
   * @return the package name of the xml model
   */
  public String getPackageName() {
    return packageName;
  }

  @Override
  public String toString() {
    return "JaxbContextData {"
        + "nameSpace='" + nameSpace + '\''
        + ", schemaLocation='" + schemaLocation + '\''
        + ", package=" + nameSpace
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof JaxbContextData)) {
      return false;
    }
    JaxbContextData that = (JaxbContextData) o;
    return Objects.equals(packageName, that.packageName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(packageName);
  }
}
