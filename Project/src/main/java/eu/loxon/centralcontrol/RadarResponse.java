
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
 *         &lt;element name="scout" type="{http://www.loxon.eu/CentralControl/}scouting" maxOccurs="unbounded"/>
 *         &lt;element name="result" type="{http://www.loxon.eu/CentralControl/}commonResp"/>
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
    "scout",
    "result"
})
@XmlRootElement(name = "radarResponse")
public class RadarResponse implements Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected List<Scouting> scout;
    @XmlElement(required = true)
    protected CommonResp result;

    /**
     * Default no-arg constructor
     * 
     */
    public RadarResponse() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public RadarResponse(final List<Scouting> scout, final CommonResp result) {
        this.scout = scout;
        this.result = result;
    }

    /**
     * Gets the value of the scout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Scouting }
     * 
     * 
     */
    public List<Scouting> getScout() {
        if (scout == null) {
            scout = new ArrayList<Scouting>();
        }
        return this.scout;
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link CommonResp }
     *     
     */
    public CommonResp getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommonResp }
     *     
     */
    public void setResult(CommonResp value) {
        this.result = value;
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
            List<Scouting> theScout;
            theScout = (((this.scout!= null)&&(!this.scout.isEmpty()))?this.getScout():null);
            strategy.appendField(locator, this, "scout", buffer, theScout);
        }
        {
            CommonResp theResult;
            theResult = this.getResult();
            strategy.appendField(locator, this, "result", buffer, theResult);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof RadarResponse)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final RadarResponse that = ((RadarResponse) object);
        {
            List<Scouting> lhsScout;
            lhsScout = (((this.scout!= null)&&(!this.scout.isEmpty()))?this.getScout():null);
            List<Scouting> rhsScout;
            rhsScout = (((that.scout!= null)&&(!that.scout.isEmpty()))?that.getScout():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "scout", lhsScout), LocatorUtils.property(thatLocator, "scout", rhsScout), lhsScout, rhsScout)) {
                return false;
            }
        }
        {
            CommonResp lhsResult;
            lhsResult = this.getResult();
            CommonResp rhsResult;
            rhsResult = that.getResult();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "result", lhsResult), LocatorUtils.property(thatLocator, "result", rhsResult), lhsResult, rhsResult)) {
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
            List<Scouting> theScout;
            theScout = (((this.scout!= null)&&(!this.scout.isEmpty()))?this.getScout():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "scout", theScout), currentHashCode, theScout);
        }
        {
            CommonResp theResult;
            theResult = this.getResult();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "result", theResult), currentHashCode, theResult);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
