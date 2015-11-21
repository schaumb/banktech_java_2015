
package eu.loxon.centralcontrol;

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
 *         &lt;element name="cord" type="{http://www.loxon.eu/CentralControl/}wsCoordinate"/>
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
    "cord",
    "result"
})
@XmlRootElement(name = "getSpaceShuttlePosResponse")
public class GetSpaceShuttlePosResponse implements Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected WsCoordinate cord;
    @XmlElement(required = true)
    protected CommonResp result;

    /**
     * Default no-arg constructor
     * 
     */
    public GetSpaceShuttlePosResponse() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public GetSpaceShuttlePosResponse(final WsCoordinate cord, final CommonResp result) {
        this.cord = cord;
        this.result = result;
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
            WsCoordinate theCord;
            theCord = this.getCord();
            strategy.appendField(locator, this, "cord", buffer, theCord);
        }
        {
            CommonResp theResult;
            theResult = this.getResult();
            strategy.appendField(locator, this, "result", buffer, theResult);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof GetSpaceShuttlePosResponse)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final GetSpaceShuttlePosResponse that = ((GetSpaceShuttlePosResponse) object);
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
            WsCoordinate theCord;
            theCord = this.getCord();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "cord", theCord), currentHashCode, theCord);
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
