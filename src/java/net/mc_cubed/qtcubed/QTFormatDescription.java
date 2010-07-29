//
//  QTFormatDescription.java
//  QTCubed
//
//  Created by Chappell Charles on 10/02/19.
//  Copyright (c) 2010 MC Cubed, Inc. All rights reserved.
//
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 2 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License along
//  with this program; if not, write to the Free Software Foundation, Inc.,
//  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
//
//
//  This is also dual licensed as proprietary software, and may be used in
//  commercial/proprietary software products by obtaining a license from:
//  MC Cubed, Inc
//  1-3-4 Kamikizaki, Urawa-ku
//  Saitama, Saitama, 330-0071
//  Japan
//
//  Email: info@mc-cubed.net
//  Website: http://www.mc-cubed.net/

package net.mc_cubed.qtcubed;

import java.util.Properties;

/**
 *
 * @author shadow
 */
public class QTFormatDescription {

    private Properties formatDescriptionAttributes = new Properties();

    public Properties getFormatDescriptionAttributes() {
        return formatDescriptionAttributes;
    }

    protected QTMediaType mediaType;

    /**
     * Get the value of mediaType
     *
     * @return the value of mediaType
     */
    public QTMediaType getMediaType() {
        return mediaType;
    }

    /**
     * Set the value of mediaType
     *
     * @param mediaType new value of mediaType
     */
    public void setMediaType(QTMediaType mediaType) {
        this.mediaType = mediaType;
    }

    protected QTCompressionFormat formatType;

    /**
     * Get the value of formatType
     *
     * @return the value of formatType
     */
    public QTCompressionFormat getFormatType() {
        return formatType;
    }

    /**
     * Set the value of formatType
     *
     * @param formatType new value of formatType
     */
    public void setFormatType(QTCompressionFormat formatType) {
        this.formatType = formatType;
    }

    protected Integer width;

    /**
     * Get the value of width
     *
     * @return the value of width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Set the value of width
     *
     * @param width new value of width
     */
    public void setWidth(int width) {
        this.width = width;
    }
    protected Integer height;

    /**
     * Get the value of height
     *
     * @return the value of height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * Set the value of height
     *
     * @param height new value of height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    public Object attributeForKey(String key) {
        return formatDescriptionAttributes.getProperty(key);
    }

    public String localizedFormatSummary() {
        throw new java.lang.UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QTFormatDescription other = (QTFormatDescription) obj;
        if (this.formatDescriptionAttributes != other.formatDescriptionAttributes && (this.formatDescriptionAttributes == null || !this.formatDescriptionAttributes.equals(other.formatDescriptionAttributes))) {
            return false;
        }
        if (this.mediaType != other.mediaType && (this.mediaType == null || !this.mediaType.equals(other.mediaType))) {
            return false;
        }
        if ((this.formatType == null) ? (other.formatType != null) : !this.formatType.equals(other.formatType)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.formatDescriptionAttributes != null ? this.formatDescriptionAttributes.hashCode() : 0);
        hash = 89 * hash + (this.mediaType != null ? this.mediaType.hashCode() : 0);
        hash = 89 * hash + (this.formatType != null ? this.formatType.hashCode() : 0);
        return hash;
    }

}
