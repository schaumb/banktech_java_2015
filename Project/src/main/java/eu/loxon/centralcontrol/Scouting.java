
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
 * <p>Java class for scouting complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="scouting">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cord" type="{http://www.loxon.eu/CentralControl/}wsCoordinate"/>
 *         &lt;element name="object" type="{http://www.loxon.eu/CentralControl/}objectType"/>
 *         &lt;element name="team" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scouting", propOrder = {
    "cord",
    "object",
    "team"
})
public class Scouting implements Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected WsCoordinate cord;
    @XmlElement(required = true)
    protected ObjectType object;
    @XmlElement(required = true)
    protected String team;

    /**
     * Default no-arg constructor
     * 
     */
    public Scouting() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public Scouting(final WsCoordinate cord, final ObjectType object, final String team) {
        this.cord = cord;
        this.object = object;
        this.team = team;
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
     * Gets the value of the object property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectType }
     *     
     */
    public ObjectType getObject() {
        return object;
    }

    /**
     * Sets the value of the object property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectType }
     *     
     */
    public void setObject(ObjectType value) {
        this.object = value;
    }

    /**
     * Gets the value of the team property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeam() {
        return team;
    }

    /**
     * Sets the value of the team property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeam(String value) {
        this.team = value;
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
            ObjectType theObject;
            theObject = this.getObject();
            strategy.appendField(locator, this, "object", buffer, theObject);
        }
        {
            String theTeam;
            theTeam = this.getTeam();
            strategy.appendField(locator, this, "team", buffer, theTeam);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof Scouting)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final Scouting that = ((Scouting) object);
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
            ObjectType lhsObject;
            lhsObject = this.getObject();
            ObjectType rhsObject;
            rhsObject = that.getObject();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "object", lhsObject), LocatorUtils.property(thatLocator, "object", rhsObject), lhsObject, rhsObject)) {
                return false;
            }
        }
        {
            String lhsTeam;
            lhsTeam = this.getTeam();
            String rhsTeam;
            rhsTeam = that.getTeam();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "team", lhsTeam), LocatorUtils.property(thatLocator, "team", rhsTeam), lhsTeam, rhsTeam)) {
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
            ObjectType theObject;
            theObject = this.getObject();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "object", theObject), currentHashCode, theObject);
        }
        {
            String theTeam;
            theTeam = this.getTeam();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "team", theTeam), currentHashCode, theTeam);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
