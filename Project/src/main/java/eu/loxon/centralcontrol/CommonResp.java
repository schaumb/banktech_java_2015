
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
 * <p>Java class for commonResp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="commonResp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="actionPointsLeft" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="explosivesLeft" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="turnsLeft" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="type" type="{http://www.loxon.eu/CentralControl/}resultType"/>
 *         &lt;element name="builderUnit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="score" type="{http://www.loxon.eu/CentralControl/}wsScore"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "commonResp", propOrder = {
    "actionPointsLeft",
    "explosivesLeft",
    "turnsLeft",
    "type",
    "builderUnit",
    "message",
    "code",
    "score"
})
public class CommonResp implements Equals, HashCode, ToString
{

    protected int actionPointsLeft;
    protected int explosivesLeft;
    protected int turnsLeft;
    @XmlElement(required = true)
    protected ResultType type;
    protected int builderUnit;
    @XmlElement(required = true)
    protected String message;
    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected WsScore score;

    /**
     * Default no-arg constructor
     * 
     */
    public CommonResp() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public CommonResp(final int actionPointsLeft, final int explosivesLeft, final int turnsLeft, final ResultType type, final int builderUnit, final String message, final String code, final WsScore score) {
        this.actionPointsLeft = actionPointsLeft;
        this.explosivesLeft = explosivesLeft;
        this.turnsLeft = turnsLeft;
        this.type = type;
        this.builderUnit = builderUnit;
        this.message = message;
        this.code = code;
        this.score = score;
    }

    /**
     * Gets the value of the actionPointsLeft property.
     * 
     */
    public int getActionPointsLeft() {
        return actionPointsLeft;
    }

    /**
     * Sets the value of the actionPointsLeft property.
     * 
     */
    public void setActionPointsLeft(int value) {
        this.actionPointsLeft = value;
    }

    /**
     * Gets the value of the explosivesLeft property.
     * 
     */
    public int getExplosivesLeft() {
        return explosivesLeft;
    }

    /**
     * Sets the value of the explosivesLeft property.
     * 
     */
    public void setExplosivesLeft(int value) {
        this.explosivesLeft = value;
    }

    /**
     * Gets the value of the turnsLeft property.
     * 
     */
    public int getTurnsLeft() {
        return turnsLeft;
    }

    /**
     * Sets the value of the turnsLeft property.
     * 
     */
    public void setTurnsLeft(int value) {
        this.turnsLeft = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ResultType }
     *     
     */
    public ResultType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultType }
     *     
     */
    public void setType(ResultType value) {
        this.type = value;
    }

    /**
     * Gets the value of the builderUnit property.
     * 
     */
    public int getBuilderUnit() {
        return builderUnit;
    }

    /**
     * Sets the value of the builderUnit property.
     * 
     */
    public void setBuilderUnit(int value) {
        this.builderUnit = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the score property.
     * 
     * @return
     *     possible object is
     *     {@link WsScore }
     *     
     */
    public WsScore getScore() {
        return score;
    }

    /**
     * Sets the value of the score property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsScore }
     *     
     */
    public void setScore(WsScore value) {
        this.score = value;
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
            int theActionPointsLeft;
            theActionPointsLeft = this.getActionPointsLeft();
            strategy.appendField(locator, this, "actionPointsLeft", buffer, theActionPointsLeft);
        }
        {
            int theExplosivesLeft;
            theExplosivesLeft = this.getExplosivesLeft();
            strategy.appendField(locator, this, "explosivesLeft", buffer, theExplosivesLeft);
        }
        {
            int theTurnsLeft;
            theTurnsLeft = this.getTurnsLeft();
            strategy.appendField(locator, this, "turnsLeft", buffer, theTurnsLeft);
        }
        {
            ResultType theType;
            theType = this.getType();
            strategy.appendField(locator, this, "type", buffer, theType);
        }
        {
            int theBuilderUnit;
            theBuilderUnit = this.getBuilderUnit();
            strategy.appendField(locator, this, "builderUnit", buffer, theBuilderUnit);
        }
        {
            String theMessage;
            theMessage = this.getMessage();
            strategy.appendField(locator, this, "message", buffer, theMessage);
        }
        {
            String theCode;
            theCode = this.getCode();
            strategy.appendField(locator, this, "code", buffer, theCode);
        }
        {
            WsScore theScore;
            theScore = this.getScore();
            strategy.appendField(locator, this, "score", buffer, theScore);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof CommonResp)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final CommonResp that = ((CommonResp) object);
        {
            int lhsActionPointsLeft;
            lhsActionPointsLeft = this.getActionPointsLeft();
            int rhsActionPointsLeft;
            rhsActionPointsLeft = that.getActionPointsLeft();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "actionPointsLeft", lhsActionPointsLeft), LocatorUtils.property(thatLocator, "actionPointsLeft", rhsActionPointsLeft), lhsActionPointsLeft, rhsActionPointsLeft)) {
                return false;
            }
        }
        {
            int lhsExplosivesLeft;
            lhsExplosivesLeft = this.getExplosivesLeft();
            int rhsExplosivesLeft;
            rhsExplosivesLeft = that.getExplosivesLeft();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "explosivesLeft", lhsExplosivesLeft), LocatorUtils.property(thatLocator, "explosivesLeft", rhsExplosivesLeft), lhsExplosivesLeft, rhsExplosivesLeft)) {
                return false;
            }
        }
        {
            int lhsTurnsLeft;
            lhsTurnsLeft = this.getTurnsLeft();
            int rhsTurnsLeft;
            rhsTurnsLeft = that.getTurnsLeft();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "turnsLeft", lhsTurnsLeft), LocatorUtils.property(thatLocator, "turnsLeft", rhsTurnsLeft), lhsTurnsLeft, rhsTurnsLeft)) {
                return false;
            }
        }
        {
            ResultType lhsType;
            lhsType = this.getType();
            ResultType rhsType;
            rhsType = that.getType();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "type", lhsType), LocatorUtils.property(thatLocator, "type", rhsType), lhsType, rhsType)) {
                return false;
            }
        }
        {
            int lhsBuilderUnit;
            lhsBuilderUnit = this.getBuilderUnit();
            int rhsBuilderUnit;
            rhsBuilderUnit = that.getBuilderUnit();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "builderUnit", lhsBuilderUnit), LocatorUtils.property(thatLocator, "builderUnit", rhsBuilderUnit), lhsBuilderUnit, rhsBuilderUnit)) {
                return false;
            }
        }
        {
            String lhsMessage;
            lhsMessage = this.getMessage();
            String rhsMessage;
            rhsMessage = that.getMessage();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "message", lhsMessage), LocatorUtils.property(thatLocator, "message", rhsMessage), lhsMessage, rhsMessage)) {
                return false;
            }
        }
        {
            String lhsCode;
            lhsCode = this.getCode();
            String rhsCode;
            rhsCode = that.getCode();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "code", lhsCode), LocatorUtils.property(thatLocator, "code", rhsCode), lhsCode, rhsCode)) {
                return false;
            }
        }
        {
            WsScore lhsScore;
            lhsScore = this.getScore();
            WsScore rhsScore;
            rhsScore = that.getScore();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "score", lhsScore), LocatorUtils.property(thatLocator, "score", rhsScore), lhsScore, rhsScore)) {
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
            int theActionPointsLeft;
            theActionPointsLeft = this.getActionPointsLeft();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "actionPointsLeft", theActionPointsLeft), currentHashCode, theActionPointsLeft);
        }
        {
            int theExplosivesLeft;
            theExplosivesLeft = this.getExplosivesLeft();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "explosivesLeft", theExplosivesLeft), currentHashCode, theExplosivesLeft);
        }
        {
            int theTurnsLeft;
            theTurnsLeft = this.getTurnsLeft();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "turnsLeft", theTurnsLeft), currentHashCode, theTurnsLeft);
        }
        {
            ResultType theType;
            theType = this.getType();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "type", theType), currentHashCode, theType);
        }
        {
            int theBuilderUnit;
            theBuilderUnit = this.getBuilderUnit();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "builderUnit", theBuilderUnit), currentHashCode, theBuilderUnit);
        }
        {
            String theMessage;
            theMessage = this.getMessage();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "message", theMessage), currentHashCode, theMessage);
        }
        {
            String theCode;
            theCode = this.getCode();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "code", theCode), currentHashCode, theCode);
        }
        {
            WsScore theScore;
            theScore = this.getScore();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "score", theScore), currentHashCode, theScore);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
