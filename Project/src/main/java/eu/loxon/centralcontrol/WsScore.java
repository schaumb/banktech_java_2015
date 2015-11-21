
package eu.loxon.centralcontrol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 * <p>Java class for wsScore complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsScore">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reward" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="bonus" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="penalty" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsScore", propOrder = {
    "reward",
    "bonus",
    "penalty",
    "total"
})
public class WsScore implements Equals, HashCode, ToString
{

    protected long reward;
    protected long bonus;
    protected long penalty;
    protected long total;

    /**
     * Default no-arg constructor
     * 
     */
    public WsScore() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WsScore(final long reward, final long bonus, final long penalty, final long total) {
        this.reward = reward;
        this.bonus = bonus;
        this.penalty = penalty;
        this.total = total;
    }

    /**
     * Gets the value of the reward property.
     * 
     */
    public long getReward() {
        return reward;
    }

    /**
     * Sets the value of the reward property.
     * 
     */
    public void setReward(long value) {
        this.reward = value;
    }

    /**
     * Gets the value of the bonus property.
     * 
     */
    public long getBonus() {
        return bonus;
    }

    /**
     * Sets the value of the bonus property.
     * 
     */
    public void setBonus(long value) {
        this.bonus = value;
    }

    /**
     * Gets the value of the penalty property.
     * 
     */
    public long getPenalty() {
        return penalty;
    }

    /**
     * Sets the value of the penalty property.
     * 
     */
    public void setPenalty(long value) {
        this.penalty = value;
    }

    /**
     * Gets the value of the total property.
     * 
     */
    public long getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     */
    public void setTotal(long value) {
        this.total = value;
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
            long theReward;
            theReward = this.getReward();
            strategy.appendField(locator, this, "reward", buffer, theReward);
        }
        {
            long theBonus;
            theBonus = this.getBonus();
            strategy.appendField(locator, this, "bonus", buffer, theBonus);
        }
        {
            long thePenalty;
            thePenalty = this.getPenalty();
            strategy.appendField(locator, this, "penalty", buffer, thePenalty);
        }
        {
            long theTotal;
            theTotal = this.getTotal();
            strategy.appendField(locator, this, "total", buffer, theTotal);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof WsScore)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final WsScore that = ((WsScore) object);
        {
            long lhsReward;
            lhsReward = this.getReward();
            long rhsReward;
            rhsReward = that.getReward();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "reward", lhsReward), LocatorUtils.property(thatLocator, "reward", rhsReward), lhsReward, rhsReward)) {
                return false;
            }
        }
        {
            long lhsBonus;
            lhsBonus = this.getBonus();
            long rhsBonus;
            rhsBonus = that.getBonus();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "bonus", lhsBonus), LocatorUtils.property(thatLocator, "bonus", rhsBonus), lhsBonus, rhsBonus)) {
                return false;
            }
        }
        {
            long lhsPenalty;
            lhsPenalty = this.getPenalty();
            long rhsPenalty;
            rhsPenalty = that.getPenalty();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "penalty", lhsPenalty), LocatorUtils.property(thatLocator, "penalty", rhsPenalty), lhsPenalty, rhsPenalty)) {
                return false;
            }
        }
        {
            long lhsTotal;
            lhsTotal = this.getTotal();
            long rhsTotal;
            rhsTotal = that.getTotal();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "total", lhsTotal), LocatorUtils.property(thatLocator, "total", rhsTotal), lhsTotal, rhsTotal)) {
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
            long theReward;
            theReward = this.getReward();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "reward", theReward), currentHashCode, theReward);
        }
        {
            long theBonus;
            theBonus = this.getBonus();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "bonus", theBonus), currentHashCode, theBonus);
        }
        {
            long thePenalty;
            thePenalty = this.getPenalty();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "penalty", thePenalty), currentHashCode, thePenalty);
        }
        {
            long theTotal;
            theTotal = this.getTotal();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "total", theTotal), currentHashCode, theTotal);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
