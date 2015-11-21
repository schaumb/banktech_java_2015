
package eu.loxon.centralcontrol;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cord" type="{http://www.loxon.eu/CentralControl/}wsCoordinate" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "unit",
    "cord"
})
@XmlRootElement(name = "radarRequest")
public class RadarRequest implements Equals, HashCode, ToString
{

    protected int unit;
    @XmlElement(required = true)
    protected List<WsCoordinate> cord;

    /**
     * Default no-arg constructor
     * 
     */
    public RadarRequest() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public RadarRequest(final int unit, final List<WsCoordinate> cord) {
        this.unit = unit;
        this.cord = cord;
    }

    /**
     * Gets the value of the unit property.
     * 
     */
    public int getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     */
    public void setUnit(int value) {
        this.unit = value;
    }

    /**
     * Gets the value of the cord property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cord property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCord().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsCoordinate }
     * 
     * 
     */
    public List<WsCoordinate> getCord() {
        if (cord == null) {
            cord = new ArrayList<WsCoordinate>();
        }
        return this.cord;
    }

    public String toString() {
        final ToStringStrategy strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        {
            int theUnit;
            theUnit = this.getUnit();
            strategy.appendField(locator, this, "unit", buffer, theUnit);
        }
        {
            List<WsCoordinate> theCord;
            theCord = (((this.cord!= null)&&(!this.cord.isEmpty()))?this.getCord():null);
            strategy.appendField(locator, this, "cord", buffer, theCord);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof RadarRequest)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final RadarRequest that = ((RadarRequest) object);
        {
            int lhsUnit;
            lhsUnit = this.getUnit();
            int rhsUnit;
            rhsUnit = that.getUnit();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "unit", lhsUnit), LocatorUtils.property(thatLocator, "unit", rhsUnit), lhsUnit, rhsUnit)) {
                return false;
            }
        }
        {
            List<WsCoordinate> lhsCord;
            lhsCord = (((this.cord!= null)&&(!this.cord.isEmpty()))?this.getCord():null);
            List<WsCoordinate> rhsCord;
            rhsCord = (((that.cord!= null)&&(!that.cord.isEmpty()))?that.getCord():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "cord", lhsCord), LocatorUtils.property(thatLocator, "cord", rhsCord), lhsCord, rhsCord)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            int theUnit;
            theUnit = this.getUnit();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "unit", theUnit), currentHashCode, theUnit);
        }
        {
            List<WsCoordinate> theCord;
            theCord = (((this.cord!= null)&&(!this.cord.isEmpty()))?this.getCord():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "cord", theCord), currentHashCode, theCord);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
