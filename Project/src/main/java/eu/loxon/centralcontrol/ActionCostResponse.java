
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
 *         &lt;element name="drill" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="move" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="radar" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="explode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="watch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="availableActionPoints" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="availableExplosives" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "drill",
    "move",
    "radar",
    "explode",
    "watch",
    "availableActionPoints",
    "availableExplosives",
    "result"
})
@XmlRootElement(name = "actionCostResponse")
public class ActionCostResponse implements Equals, HashCode, ToString
{

    protected int drill;
    protected int move;
    protected int radar;
    protected int explode;
    protected int watch;
    protected int availableActionPoints;
    protected int availableExplosives;
    @XmlElement(required = true)
    protected CommonResp result;

    /**
     * Default no-arg constructor
     * 
     */
    public ActionCostResponse() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public ActionCostResponse(final int drill, final int move, final int radar, final int explode, final int watch, final int availableActionPoints, final int availableExplosives, final CommonResp result) {
        this.drill = drill;
        this.move = move;
        this.radar = radar;
        this.explode = explode;
        this.watch = watch;
        this.availableActionPoints = availableActionPoints;
        this.availableExplosives = availableExplosives;
        this.result = result;
    }

    /**
     * Gets the value of the drill property.
     * 
     */
    public int getDrill() {
        return drill;
    }

    /**
     * Sets the value of the drill property.
     * 
     */
    public void setDrill(int value) {
        this.drill = value;
    }

    /**
     * Gets the value of the move property.
     * 
     */
    public int getMove() {
        return move;
    }

    /**
     * Sets the value of the move property.
     * 
     */
    public void setMove(int value) {
        this.move = value;
    }

    /**
     * Gets the value of the radar property.
     * 
     */
    public int getRadar() {
        return radar;
    }

    /**
     * Sets the value of the radar property.
     * 
     */
    public void setRadar(int value) {
        this.radar = value;
    }

    /**
     * Gets the value of the explode property.
     * 
     */
    public int getExplode() {
        return explode;
    }

    /**
     * Sets the value of the explode property.
     * 
     */
    public void setExplode(int value) {
        this.explode = value;
    }

    /**
     * Gets the value of the watch property.
     * 
     */
    public int getWatch() {
        return watch;
    }

    /**
     * Sets the value of the watch property.
     * 
     */
    public void setWatch(int value) {
        this.watch = value;
    }

    /**
     * Gets the value of the availableActionPoints property.
     * 
     */
    public int getAvailableActionPoints() {
        return availableActionPoints;
    }

    /**
     * Sets the value of the availableActionPoints property.
     * 
     */
    public void setAvailableActionPoints(int value) {
        this.availableActionPoints = value;
    }

    /**
     * Gets the value of the availableExplosives property.
     * 
     */
    public int getAvailableExplosives() {
        return availableExplosives;
    }

    /**
     * Sets the value of the availableExplosives property.
     * 
     */
    public void setAvailableExplosives(int value) {
        this.availableExplosives = value;
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
            int theDrill;
            theDrill = this.getDrill();
            strategy.appendField(locator, this, "drill", buffer, theDrill);
        }
        {
            int theMove;
            theMove = this.getMove();
            strategy.appendField(locator, this, "move", buffer, theMove);
        }
        {
            int theRadar;
            theRadar = this.getRadar();
            strategy.appendField(locator, this, "radar", buffer, theRadar);
        }
        {
            int theExplode;
            theExplode = this.getExplode();
            strategy.appendField(locator, this, "explode", buffer, theExplode);
        }
        {
            int theWatch;
            theWatch = this.getWatch();
            strategy.appendField(locator, this, "watch", buffer, theWatch);
        }
        {
            int theAvailableActionPoints;
            theAvailableActionPoints = this.getAvailableActionPoints();
            strategy.appendField(locator, this, "availableActionPoints", buffer, theAvailableActionPoints);
        }
        {
            int theAvailableExplosives;
            theAvailableExplosives = this.getAvailableExplosives();
            strategy.appendField(locator, this, "availableExplosives", buffer, theAvailableExplosives);
        }
        {
            CommonResp theResult;
            theResult = this.getResult();
            strategy.appendField(locator, this, "result", buffer, theResult);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof ActionCostResponse)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final ActionCostResponse that = ((ActionCostResponse) object);
        {
            int lhsDrill;
            lhsDrill = this.getDrill();
            int rhsDrill;
            rhsDrill = that.getDrill();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "drill", lhsDrill), LocatorUtils.property(thatLocator, "drill", rhsDrill), lhsDrill, rhsDrill)) {
                return false;
            }
        }
        {
            int lhsMove;
            lhsMove = this.getMove();
            int rhsMove;
            rhsMove = that.getMove();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "move", lhsMove), LocatorUtils.property(thatLocator, "move", rhsMove), lhsMove, rhsMove)) {
                return false;
            }
        }
        {
            int lhsRadar;
            lhsRadar = this.getRadar();
            int rhsRadar;
            rhsRadar = that.getRadar();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "radar", lhsRadar), LocatorUtils.property(thatLocator, "radar", rhsRadar), lhsRadar, rhsRadar)) {
                return false;
            }
        }
        {
            int lhsExplode;
            lhsExplode = this.getExplode();
            int rhsExplode;
            rhsExplode = that.getExplode();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "explode", lhsExplode), LocatorUtils.property(thatLocator, "explode", rhsExplode), lhsExplode, rhsExplode)) {
                return false;
            }
        }
        {
            int lhsWatch;
            lhsWatch = this.getWatch();
            int rhsWatch;
            rhsWatch = that.getWatch();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "watch", lhsWatch), LocatorUtils.property(thatLocator, "watch", rhsWatch), lhsWatch, rhsWatch)) {
                return false;
            }
        }
        {
            int lhsAvailableActionPoints;
            lhsAvailableActionPoints = this.getAvailableActionPoints();
            int rhsAvailableActionPoints;
            rhsAvailableActionPoints = that.getAvailableActionPoints();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "availableActionPoints", lhsAvailableActionPoints), LocatorUtils.property(thatLocator, "availableActionPoints", rhsAvailableActionPoints), lhsAvailableActionPoints, rhsAvailableActionPoints)) {
                return false;
            }
        }
        {
            int lhsAvailableExplosives;
            lhsAvailableExplosives = this.getAvailableExplosives();
            int rhsAvailableExplosives;
            rhsAvailableExplosives = that.getAvailableExplosives();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "availableExplosives", lhsAvailableExplosives), LocatorUtils.property(thatLocator, "availableExplosives", rhsAvailableExplosives), lhsAvailableExplosives, rhsAvailableExplosives)) {
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
            int theDrill;
            theDrill = this.getDrill();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "drill", theDrill), currentHashCode, theDrill);
        }
        {
            int theMove;
            theMove = this.getMove();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "move", theMove), currentHashCode, theMove);
        }
        {
            int theRadar;
            theRadar = this.getRadar();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "radar", theRadar), currentHashCode, theRadar);
        }
        {
            int theExplode;
            theExplode = this.getExplode();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "explode", theExplode), currentHashCode, theExplode);
        }
        {
            int theWatch;
            theWatch = this.getWatch();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "watch", theWatch), currentHashCode, theWatch);
        }
        {
            int theAvailableActionPoints;
            theAvailableActionPoints = this.getAvailableActionPoints();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "availableActionPoints", theAvailableActionPoints), currentHashCode, theAvailableActionPoints);
        }
        {
            int theAvailableExplosives;
            theAvailableExplosives = this.getAvailableExplosives();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "availableExplosives", theAvailableExplosives), currentHashCode, theAvailableExplosives);
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
