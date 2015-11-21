
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
 *         &lt;element name="size" type="{http://www.loxon.eu/CentralControl/}wsCoordinate"/>
 *         &lt;element name="result" type="{http://www.loxon.eu/CentralControl/}commonResp"/>
 *         &lt;element name="units" type="{http://www.loxon.eu/CentralControl/}wsBuilderunit" maxOccurs="unbounded"/>
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
    "size",
    "result",
    "units"
})
@XmlRootElement(name = "startGameResponse")
public class StartGameResponse implements Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected WsCoordinate size;
    @XmlElement(required = true)
    protected CommonResp result;
    @XmlElement(required = true)
    protected List<WsBuilderunit> units;

    /**
     * Default no-arg constructor
     * 
     */
    public StartGameResponse() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public StartGameResponse(final WsCoordinate size, final CommonResp result, final List<WsBuilderunit> units) {
        this.size = size;
        this.result = result;
        this.units = units;
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link WsCoordinate }
     *     
     */
    public WsCoordinate getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsCoordinate }
     *     
     */
    public void setSize(WsCoordinate value) {
        this.size = value;
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

    /**
     * Gets the value of the units property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the units property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnits().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsBuilderunit }
     * 
     * 
     */
    public List<WsBuilderunit> getUnits() {
        if (units == null) {
            units = new ArrayList<WsBuilderunit>();
        }
        return this.units;
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
            WsCoordinate theSize;
            theSize = this.getSize();
            strategy.appendField(locator, this, "size", buffer, theSize);
        }
        {
            CommonResp theResult;
            theResult = this.getResult();
            strategy.appendField(locator, this, "result", buffer, theResult);
        }
        {
            List<WsBuilderunit> theUnits;
            theUnits = (((this.units!= null)&&(!this.units.isEmpty()))?this.getUnits():null);
            strategy.appendField(locator, this, "units", buffer, theUnits);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof StartGameResponse)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final StartGameResponse that = ((StartGameResponse) object);
        {
            WsCoordinate lhsSize;
            lhsSize = this.getSize();
            WsCoordinate rhsSize;
            rhsSize = that.getSize();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "size", lhsSize), LocatorUtils.property(thatLocator, "size", rhsSize), lhsSize, rhsSize)) {
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
        {
            List<WsBuilderunit> lhsUnits;
            lhsUnits = (((this.units!= null)&&(!this.units.isEmpty()))?this.getUnits():null);
            List<WsBuilderunit> rhsUnits;
            rhsUnits = (((that.units!= null)&&(!that.units.isEmpty()))?that.getUnits():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "units", lhsUnits), LocatorUtils.property(thatLocator, "units", rhsUnits), lhsUnits, rhsUnits)) {
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
            WsCoordinate theSize;
            theSize = this.getSize();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "size", theSize), currentHashCode, theSize);
        }
        {
            CommonResp theResult;
            theResult = this.getResult();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "result", theResult), currentHashCode, theResult);
        }
        {
            List<WsBuilderunit> theUnits;
            theUnits = (((this.units!= null)&&(!this.units.isEmpty()))?this.getUnits():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "units", theUnits), currentHashCode, theUnits);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
