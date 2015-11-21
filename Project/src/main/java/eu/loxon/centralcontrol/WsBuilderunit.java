
package eu.loxon.centralcontrol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 * <p>Java class for wsBuilderunit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsBuilderunit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cord" type="{http://www.loxon.eu/CentralControl/}wsCoordinate"/>
 *         &lt;element name="unitid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsBuilderunit", propOrder = {
    "cord",
    "unitid"
})
public class WsBuilderunit implements Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected WsCoordinate cord;
    protected int unitid;

    /**
     * Default no-arg constructor
     * 
     */
    public WsBuilderunit() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WsBuilderunit(final WsCoordinate cord, final int unitid) {
        this.cord = cord;
        this.unitid = unitid;
    }

    /**
     * Gets the value of the cord property.
     * 
     * @return
     *     possible object is
     *     {@link WsCoordinate }
     *     
     */
    public WsCoordinate getCord() {
        return cord;
    }

    /**
     * Sets the value of the cord property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsCoordinate }
     *     
     */
    public void setCord(WsCoordinate value) {
        this.cord = value;
    }

    /**
     * Gets the value of the unitid property.
     * 
     */
    public int getUnitid() {
        return unitid;
    }

    /**
     * Sets the value of the unitid property.
     * 
     */
    public void setUnitid(int value) {
        this.unitid = value;
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
            WsCoordinate theCord;
            theCord = this.getCord();
            strategy.appendField(locator, this, "cord", buffer, theCord);
        }
        {
            int theUnitid;
            theUnitid = this.getUnitid();
            strategy.appendField(locator, this, "unitid", buffer, theUnitid);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof WsBuilderunit)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final WsBuilderunit that = ((WsBuilderunit) object);
        {
            WsCoordinate lhsCord;
            lhsCord = this.getCord();
            WsCoordinate rhsCord;
            rhsCord = that.getCord();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "cord", lhsCord), LocatorUtils.property(thatLocator, "cord", rhsCord), lhsCord, rhsCord)) {
                return false;
            }
        }
        {
            int lhsUnitid;
            lhsUnitid = this.getUnitid();
            int rhsUnitid;
            rhsUnitid = that.getUnitid();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "unitid", lhsUnitid), LocatorUtils.property(thatLocator, "unitid", rhsUnitid), lhsUnitid, rhsUnitid)) {
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
            WsCoordinate theCord;
            theCord = this.getCord();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "cord", theCord), currentHashCode, theCord);
        }
        {
            int theUnitid;
            theUnitid = this.getUnitid();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "unitid", theUnitid), currentHashCode, theUnitid);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
