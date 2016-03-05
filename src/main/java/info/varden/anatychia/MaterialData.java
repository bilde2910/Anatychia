/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.varden.anatychia;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jnbt.ByteArrayTag;
import org.jnbt.ByteTag;
import org.jnbt.CompoundTag;
import org.jnbt.DoubleTag;
import org.jnbt.FloatTag;
import org.jnbt.IntArrayTag;
import org.jnbt.IntTag;
import org.jnbt.ListTag;
import org.jnbt.LongTag;
import org.jnbt.ShortTag;
import org.jnbt.StringTag;
import org.jnbt.Tag;

/**
 *
 * @author Marius
 */
public class MaterialData {
    private final MaterialType type;
    private final String name;
    private long quantity;
    private CompoundTag nbtRequirements = null;
    private NBTCriterion[] nbtCriteria = null;
    private double removalChance = 100D;
    
    public MaterialData(MaterialType type, String name, long quantity) {
        this.type = type;
        this.name = name;
        this.quantity = quantity;
    }
    
    public MaterialData(MaterialType type, String name, long quantity, NBTCriterion[] criteria, double removalChance) {
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.nbtCriteria = criteria;
        this.removalChance = removalChance;
    }
    
    public MaterialData(MaterialType type, String name, long quantity, CompoundTag reqs, double removalChance) {
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.nbtRequirements = reqs;
        this.removalChance = removalChance;
    }
    
    public MaterialType getType() {
        return this.type;
    }
    
    public String getName() {
        return this.name;
    }
    
    public long getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
    
    public double getRemovalChance() {
        return this.removalChance;
    }
    
    public void setRemovalChance(double chance) {
        this.removalChance = chance;
    }
    
    public CompoundTag getEntityRequirements() {
        return this.nbtRequirements;
    }
    
    public NBTCriterion[] getEntityCriteria() {
        return this.nbtCriteria;
    }
    
    public boolean fulfillsRequirements(CompoundTag match) {
        if (this.nbtCriteria != null) {
            return tagMeetsCriteriaList(match, this.nbtCriteria);
        } else if (this.nbtRequirements != null) {
            return tagContainsOther(this.nbtRequirements, match);
        }
        return true;
    }
    
    private static boolean tagMeetsCriteriaList(Tag t, NBTCriterion[] criteriaList) {
        if (t instanceof ListTag || t instanceof ByteArrayTag || t instanceof IntArrayTag) {
            return false;
        }
        for (NBTCriterion c : criteriaList) {
            if (c.getTagType().equals(ByteArrayTag.class) || c.getTagType().equals(IntArrayTag.class) || c.getTagType().equals(CompoundTag.class) || c.getTagType().equals(ListTag.class)) {
                return false;
            }
            String[] nbtParts = c.getNBTPath().split("\\/");
            Tag tag = t;
            for (int i = 0; i < nbtParts.length; i++) {
                if (i < nbtParts.length - 1) {
                    if (tag instanceof CompoundTag) {
                        Map<String, Tag> n = ((CompoundTag) tag).getValue();
                        if (!n.containsKey(nbtParts[i + 1])) {
                            return false;
                        }
                        tag = n.get(nbtParts[i + 1]);
                    } else {
                        return false;
                    }
                } else {
                    if (tag instanceof CompoundTag || !tag.getClass().equals(c.getTagType())) {
                        return false;
                    } else if (tag instanceof ByteTag) {
                        switch (c.getMatchMode()) {
                            case EQUALS:
                            case NOT_EQUALS:
                                if (!(((ByteTag) tag).getValue() == Byte.parseByte(c.getValue()) ^ c.getMatchMode() == MatchMode.NOT_EQUALS)) {
                                    return false;
                                }
                                break;
                            case SMALLER_THAN:
                            case EQUALS_OR_GREATER:
                                if (!(((ByteTag) tag).getValue() < Byte.parseByte(c.getValue()) ^ c.getMatchMode() == MatchMode.EQUALS_OR_GREATER)) {
                                    return false;
                                }
                                break;
                            case GREATER_THAN:
                            case EQUALS_OR_SMALLER:
                                if (!(((ByteTag) tag).getValue() > Byte.parseByte(c.getValue()) ^ c.getMatchMode() == MatchMode.EQUALS_OR_SMALLER)) {
                                    return false;
                                }
                                break;
                        }
                    } else if (tag instanceof ShortTag) {
                        switch (c.getMatchMode()) {
                            case EQUALS:
                            case NOT_EQUALS:
                                if (!(((ShortTag) tag).getValue() == Short.parseShort(c.getValue()) ^ c.getMatchMode() == MatchMode.NOT_EQUALS)) {
                                    return false;
                                }
                                break;
                            case SMALLER_THAN:
                            case EQUALS_OR_GREATER:
                                if (!(((ShortTag) tag).getValue() < Short.parseShort(c.getValue()) ^ c.getMatchMode() == MatchMode.EQUALS_OR_GREATER)) {
                                    return false;
                                }
                                break;
                            case GREATER_THAN:
                            case EQUALS_OR_SMALLER:
                                if (!(((ShortTag) tag).getValue() > Short.parseShort(c.getValue()) ^ c.getMatchMode() == MatchMode.EQUALS_OR_SMALLER)) {
                                    return false;
                                }
                                break;
                        }
                    } else if (tag instanceof IntTag) {
                        switch (c.getMatchMode()) {
                            case EQUALS:
                            case NOT_EQUALS:
                                if (!(((IntTag) tag).getValue() == Integer.parseInt(c.getValue()) ^ c.getMatchMode() == MatchMode.NOT_EQUALS)) {
                                    return false;
                                }
                                break;
                            case SMALLER_THAN:
                            case EQUALS_OR_GREATER:
                                if (!(((IntTag) tag).getValue() < Integer.parseInt(c.getValue()) ^ c.getMatchMode() == MatchMode.EQUALS_OR_GREATER)) {
                                    return false;
                                }
                                break;
                            case GREATER_THAN:
                            case EQUALS_OR_SMALLER:
                                if (!(((IntTag) tag).getValue() > Integer.parseInt(c.getValue()) ^ c.getMatchMode() == MatchMode.EQUALS_OR_SMALLER)) {
                                    return false;
                                }
                                break;
                        }
                    } else if (tag instanceof IntTag) {
                        switch (c.getMatchMode()) {
                            case EQUALS:
                            case NOT_EQUALS:
                                if (!(((IntTag) tag).getValue() == Integer.parseInt(c.getValue()) ^ c.getMatchMode() == MatchMode.NOT_EQUALS)) {
                                    return false;
                                }
                                break;
                            case SMALLER_THAN:
                            case EQUALS_OR_GREATER:
                                if (!(((IntTag) tag).getValue() < Integer.parseInt(c.getValue()) ^ c.getMatchMode() == MatchMode.EQUALS_OR_GREATER)) {
                                    return false;
                                }
                                break;
                            case GREATER_THAN:
                            case EQUALS_OR_SMALLER:
                                if (!(((IntTag) tag).getValue() > Integer.parseInt(c.getValue()) ^ c.getMatchMode() == MatchMode.EQUALS_OR_SMALLER)) {
                                    return false;
                                }
                                break;
                        }
                    } else if (tag instanceof LongTag) {
                        switch (c.getMatchMode()) {
                            case EQUALS:
                            case NOT_EQUALS:
                                if (!(((LongTag) tag).getValue() == Long.parseLong(c.getValue()) ^ c.getMatchMode() == MatchMode.NOT_EQUALS)) {
                                    return false;
                                }
                                break;
                            case SMALLER_THAN:
                            case EQUALS_OR_GREATER:
                                if (!(((LongTag) tag).getValue() < Long.parseLong(c.getValue()) ^ c.getMatchMode() == MatchMode.EQUALS_OR_GREATER)) {
                                    return false;
                                }
                                break;
                            case GREATER_THAN:
                            case EQUALS_OR_SMALLER:
                                if (!(((LongTag) tag).getValue() > Long.parseLong(c.getValue()) ^ c.getMatchMode() == MatchMode.EQUALS_OR_SMALLER)) {
                                    return false;
                                }
                                break;
                        }
                    } else if (tag instanceof FloatTag) {
                        switch (c.getMatchMode()) {
                            case EQUALS:
                            case NOT_EQUALS:
                                if (!(((FloatTag) tag).getValue() == Float.parseFloat(c.getValue()) ^ c.getMatchMode() == MatchMode.NOT_EQUALS)) {
                                    return false;
                                }
                                break;
                            case SMALLER_THAN:
                            case EQUALS_OR_GREATER:
                                if (!(((FloatTag) tag).getValue() < Float.parseFloat(c.getValue()) ^ c.getMatchMode() == MatchMode.EQUALS_OR_GREATER)) {
                                    return false;
                                }
                                break;
                            case GREATER_THAN:
                            case EQUALS_OR_SMALLER:
                                if (!(((FloatTag) tag).getValue() > Float.parseFloat(c.getValue()) ^ c.getMatchMode() == MatchMode.EQUALS_OR_SMALLER)) {
                                    return false;
                                }
                                break;
                        }
                    } else if (tag instanceof DoubleTag) {
                        switch (c.getMatchMode()) {
                            case EQUALS:
                            case NOT_EQUALS:
                                if (!(((DoubleTag) tag).getValue() == Double.parseDouble(c.getValue()) ^ c.getMatchMode() == MatchMode.NOT_EQUALS)) {
                                    return false;
                                }
                                break;
                            case SMALLER_THAN:
                            case EQUALS_OR_GREATER:
                                if (!(((DoubleTag) tag).getValue() < Double.parseDouble(c.getValue()) ^ c.getMatchMode() == MatchMode.EQUALS_OR_GREATER)) {
                                    return false;
                                }
                                break;
                            case GREATER_THAN:
                            case EQUALS_OR_SMALLER:
                                if (!(((DoubleTag) tag).getValue() > Double.parseDouble(c.getValue()) ^ c.getMatchMode() == MatchMode.EQUALS_OR_SMALLER)) {
                                    return false;
                                }
                                break;
                        }
                    } else if (tag instanceof StringTag) {
                        switch (c.getMatchMode()) {
                            case EQUALS:
                            case NOT_EQUALS:
                                if (!(((StringTag) tag).getValue().equals(c.getValue()) ^ c.getMatchMode() == MatchMode.NOT_EQUALS)) {
                                    return false;
                                }
                                break;
                            case SMALLER_THAN:
                            case EQUALS_OR_GREATER:
                            case GREATER_THAN:
                            case EQUALS_OR_SMALLER:
                                return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private static boolean tagContainsOther(Tag host, Tag guest) {
        if (!host.getClass().equals(guest.getClass())) {
            return false;
        }
        if (host instanceof CompoundTag) {
            Map<String, Tag> hostTags = ((CompoundTag) host).getValue();
            Map<String, Tag> guestTags = ((CompoundTag) guest).getValue();
            for (String s : guestTags.keySet()) {
                if (!hostTags.containsKey(s)) {
                    return false;
                }
                if (!tagContainsOther(hostTags.get(s), guestTags.get(s))) {
                    return false;
                }
            }
        } else if (host instanceof ListTag) {
            List<Tag> hostTags = ((ListTag) host).getValue();
            List<Tag> guestTags = ((ListTag) guest).getValue();
            for (Tag gt : guestTags) {
                boolean foundInHost = false;
                for (Tag ht : hostTags) {
                    if (tagContainsOther(ht, gt)) {
                        foundInHost = true;
                    }
                }
                if (!foundInHost) {
                    return false;
                }
            }
        } else {
            return host.getValue().equals(guest.getValue());
        }
        return true;
    }
    
    private static boolean fulfillsRequirements(CompoundTag reqs, CompoundTag match) {
        boolean isOK = true;
        Map<String, Tag> reqTags = reqs.getValue();
        Map<String, Tag> matchTags = match.getValue();
        Set<String> reqKeys = reqTags.keySet();
        for (String s : reqKeys) {
            if (matchTags.containsKey(s)) {
                Tag rv = reqTags.get(s);
                Tag mv = matchTags.get(s);
                if (!mv.getClass().equals(rv.getClass())) {
                    return false;
                }
                if (mv instanceof CompoundTag) {
                    if (!fulfillsRequirements((CompoundTag) rv, (CompoundTag) mv)) {
                        return false;
                    }
                } else if (mv instanceof ListTag) {
                    ListTag mvl = (ListTag) mv;
                    ListTag rvl = (ListTag) rv;
                    if (!mvl.getType().equals(rvl.getType())) {
                        return false;
                    }
                    List<Tag> rval = rvl.getValue();
                    
                } else {
                    if (!mv.getValue().equals(rv.getValue())) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return isOK;
    }
    
    @Override
    public String toString() {
        return "MaterialData{type=MaterialType." + this.type.toString() + ", name=" + this.name + ", quantity=" + this.quantity + "}";
    }
}
