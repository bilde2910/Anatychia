/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.varden.anatychia;

import java.util.HashMap;
import org.jnbt.ByteTag;
import org.jnbt.DoubleTag;
import org.jnbt.FloatTag;
import org.jnbt.IntTag;
import org.jnbt.ShortTag;
import org.jnbt.StringTag;
import org.jnbt.Tag;

/**
 *
 * @author Marius
 */
public class CommonNameDictionary {
    private static final HashMap<MaterialType, HashMap<String, NBTNodeDefinition>> guiPresets = new HashMap<MaterialType, HashMap<String, NBTNodeDefinition>>() {{
        put(MaterialType.ITEM, new HashMap<String, NBTNodeDefinition>() {{
            put("Is unbreakable? (0=no, 1=yes)", new NBTNodeDefinition("/tag/Unbreakable", ByteTag.class));
            put("Item count", new NBTNodeDefinition("/Count", ByteTag.class));
            put("Item ID (1.7 and below)", new NBTNodeDefinition("/id", ShortTag.class));
            put("Item ID (1.8 and above)", new NBTNodeDefinition("/id", StringTag.class));
            put("Item name", new NBTNodeDefinition("/tag/display/Name", StringTag.class));
            put("Repair cost", new NBTNodeDefinition("/tag/RepairCost", IntTag.class));
            put("Slot number", new NBTNodeDefinition("/Slot", ByteTag.class));
            put("Tool/weapon damage or meta-value", new NBTNodeDefinition("/Damage", ShortTag.class));
            put("Book: Author", new NBTNodeDefinition("/tag/author", StringTag.class));
            put("Book: Originality (0=original, 1=copy of original, 2=copy of copy, 3=tattered)", new NBTNodeDefinition("/tag/generation", IntTag.class));
            put("Book: Title", new NBTNodeDefinition("/tag/title", StringTag.class));
            put("Firework rocket: Flight duration (1-3)", new NBTNodeDefinition("/tag/Fireworks/Flight", ByteTag.class));
            put("Firework star: Leaves a trail? (0=no, 1=yes)", new NBTNodeDefinition("/tag/Explosion/Trail", ByteTag.class));
            put("Firework star: Twinkles? (0=no, 1=yes)", new NBTNodeDefinition("/tag/Explosion/Flicker", ByteTag.class));
            put("Firework star: Type (0=small, 1=large, 2=star, 3=creeper, 4=burst)", new NBTNodeDefinition("/tag/Explosion/Type", ByteTag.class));
            put("Leather armor: Color", new NBTNodeDefinition("/tag/display/color", IntTag.class));
            put("Map: Is scaled? (0=no, 1=yes)", new NBTNodeDefinition("/tag/map_is_scaling", ByteTag.class));
            put("Player skull: Owner (1.7 and below)", new NBTNodeDefinition("/tag/SkullOwner", StringTag.class));
            put("Player skull: Owner (1.8 and above)", new NBTNodeDefinition("/tag/SkullOwner/Name", StringTag.class));
            put("Player skull: UUID of owner", new NBTNodeDefinition("/tag/SkullOwner/Id", StringTag.class));
        }});
        put(MaterialType.ENTITY, new HashMap<String, NBTNodeDefinition>() {{
            put("Entity ID", new NBTNodeDefinition("/id", StringTag.class));
            put("Distance fallen", new NBTNodeDefinition("/FallDistance", FloatTag.class));
            put("Ticks left on fire (-1=not on fire)", new NBTNodeDefinition("/Fire", ShortTag.class));
            put("Air left in ticks (0=drowning if underwater)", new NBTNodeDefinition("/Air", ShortTag.class));
            put("On ground? (0=in the air, 1=on the ground)", new NBTNodeDefinition("/OnGround", ByteTag.class));
            put("Dimension (-1=The Nether, 0=The Overworld, 1=The End)", new NBTNodeDefinition("/Dimension", IntTag.class));
            put("Invurnerable? (0=no, 1=yes)", new NBTNodeDefinition("/Invurnerable", ByteTag.class));
            put("Ticks remaining until portal teleportation is allowed (900=in 45 seconds, 0=any time)", new NBTNodeDefinition("/PortalCooldown", IntTag.class));
            put("Entity UUID", new NBTNodeDefinition("/UUID", StringTag.class));
            put("Mobs: Health (1 heart = 2 health)", new NBTNodeDefinition("/Health", ShortTag.class));
            put("Mobs: Extra health from Absorption effect", new NBTNodeDefinition("/AbsorptionAmount", FloatTag.class));
            put("Mobs: Ticks until invincibility shield wears off (0=not recently attacked)", new NBTNodeDefinition("/AttackTime", ShortTag.class));
            put("Mobs: Ticks until damage indicator wears off (0=not recently damaged)", new NBTNodeDefinition("/HurtTime", ShortTag.class));
            put("Mobs: Can pick up/equip items? (0=no, 1=yes)", new NBTNodeDefinition("/CanPickUpLoot", ByteTag.class));
            put("Mobs: Is immune to despawning? (0=no, 1=yes)", new NBTNodeDefinition("/PersistenceRequired", ByteTag.class));
            put("Mobs: Name", new NBTNodeDefinition("/CustomName", StringTag.class));
            put("Mobs: Name always visible? (0=no, 1=yes)", new NBTNodeDefinition("/CustomNameVisible", ByteTag.class));
            put("Mobs: Leashed? (0=no, 1=yes)", new NBTNodeDefinition("/Leashed", ByteTag.class));
            put("Mobs: X coordinate of fence leash is attached to", new NBTNodeDefinition("/Leash/X", IntTag.class));
            put("Mobs: Y coordinate of fence leash is attached to", new NBTNodeDefinition("/Leash/Y", IntTag.class));
            put("Mobs: Z coordinate of fence leash is attached to", new NBTNodeDefinition("/Leash/Z", IntTag.class));
            put("Breedables: Ticks until love mode wears off (0=not in love mode)", new NBTNodeDefinition("/InLove", IntTag.class));
            put("Breedables: Age in ticks, and ticks before mob can breed (<-1=baby, >0=adult)", new NBTNodeDefinition("/Age", IntTag.class));
            put("Breedables: Forced age. See \"Age in ticks\" for help", new NBTNodeDefinition("/ForcedAge", IntTag.class));
            put("Breedables: Owner (1.7 and below)", new NBTNodeDefinition("/Owner", StringTag.class));
            put("Breedables: Owner's UUID (1.8 and above)", new NBTNodeDefinition("/OwnerUUID", StringTag.class));
            put("Breedables: Is mob sitting? (0=no, 1=yes)", new NBTNodeDefinition("", ByteTag.class));
            put("Bat: Hanging upside down from a block? (0=no, 1=yes)", new NBTNodeDefinition("/BatFlags", ByteTag.class));
            put("Chicken: Is chicken jockey? (0=no, 1=yes)", new NBTNodeDefinition("/IsChickenJockey", ByteTag.class));
            put("Creeper: Charged? (0=no, 1=yes)", new NBTNodeDefinition("/powered", ByteTag.class));
            put("Creeper: Explosion radius (3=default)", new NBTNodeDefinition("/ExplosionRadius", ByteTag.class));
            put("Creeper: Remaining fuse time in ticks (30=default)", new NBTNodeDefinition("/Fuse", ShortTag.class));
            put("Creeper: Ignited by Flint and Steel? (0=no, 1=yes)", new NBTNodeDefinition("/ignited", ByteTag.class));
            put("Enderman: ID of carried block (0=not carrying a block)", new NBTNodeDefinition("/carried", ShortTag.class));
            put("Enderman: Metadata of carried block (0=not carrying a block)", new NBTNodeDefinition("/carriedData", ShortTag.class));
            put("Enderman: Number of Endermites spawned (0=default)", new NBTNodeDefinition("/EndermiteCount", IntTag.class));
            put("Endermite: Lifetime in ticks (despawns at 2400)", new NBTNodeDefinition("/Lifetime", IntTag.class));
            put("Horse: Has chests? (0=no, 1=yes)", new NBTNodeDefinition("/ChestedHorse", ByteTag.class));
            put("Horse: Grazing? (0=no, 1=yes)", new NBTNodeDefinition("/EatingHaystack", ByteTag.class));
            put("Horse: Tamed? (0=no, 1=yes)", new NBTNodeDefinition("/Tame", ByteTag.class));
            put("Horse: Breeding chance (0=none, 100=always)", new NBTNodeDefinition("/Temper", IntTag.class));
            put("Horse: Type (0=horse, 1=donkey, 2=mule, 3=zombie, 4=skeleton)", new NBTNodeDefinition("/Type", IntTag.class));
            put("Horse: Variant", new NBTNodeDefinition("/Variant", IntTag.class));
            put("Horse: Name of the player who tamed the horse", new NBTNodeDefinition("/OwnerName", StringTag.class));
            //put("", new NBTNodeDefinition("", )); // ArmorItem
            //put("", new NBTNodeDefinition("", )); // SaddleItem
            put("Horse: Has saddle? (0=no, 1=yes)", new NBTNodeDefinition("/Saddle", ByteTag.class));
            put("Ghast: Explosion radius (1=default)", new NBTNodeDefinition("/ExplosionPower", IntTag.class));
            put("Ozelot: Cat type (0=wild, 1=tuxuedo, 2=tabby, 3=siamese)", new NBTNodeDefinition("/CatType", IntTag.class));
            put("Pig: Has saddle? (0=no, 1=yes)", new NBTNodeDefinition("/Saddle", ByteTag.class));
            put("Sheep: Has been sheared? (0=no, 1=yes)", new NBTNodeDefinition("/Sheared", ByteTag.class));
            put("Sheep: Wool color (0-15)", new NBTNodeDefinition("/Color", ByteTag.class));
            put("Skeleton: Type (0=normal, 1=wither skeleton)", new NBTNodeDefinition("/SkeletonType", ByteTag.class));
            put("Slime: Size (0=small, 1=larger, 2=even larger etc.)", new NBTNodeDefinition("", IntTag.class));
            put("Wither: Ticks left of invurnerability (0=not invurnerable)", new NBTNodeDefinition("/Invul", IntTag.class));
            put("Wolf: Is angry? (0=no, 1=yes)", new NBTNodeDefinition("/Angry", ByteTag.class));
            put("Wolf: Collar color (14=default)", new NBTNodeDefinition("/CollarColor", ByteTag.class));
            put("Villager: Profession (see wiki article \"Villager\")", new NBTNodeDefinition("/Profession", IntTag.class));
            put("Villager: Number of emeralds received from players in total", new NBTNodeDefinition("/Riches", IntTag.class));
            put("Villager: Career (see wiki article \"Villager\")", new NBTNodeDefinition("/Career", IntTag.class));
            put("Villager: Career level", new NBTNodeDefinition("/CareerLevel", IntTag.class));
            put("Villager: Willing to mate? (0=no, 1=yes)", new NBTNodeDefinition("/Willing", ByteTag.class));
            put("Iron Golem: Created by player? (0=no, 1=yes)", new NBTNodeDefinition("/PlayerCreated", ByteTag.class));
            put("Zombie: Villager zombie? (0=no, 1=yes) [experimental]", new NBTNodeDefinition("/IsVillager", ByteTag.class));
            put("Zombie: Is baby? (0=no, 1=yes) [experimental]", new NBTNodeDefinition("/IsBaby", ByteTag.class));
            put("Zombie: Time left until healed in ticks (-1=not healing)", new NBTNodeDefinition("/ConversionTime", IntTag.class));
            put("Zombie: Can break doors? (0=no, 1=yes)", new NBTNodeDefinition("/CanBreakDoors", ByteTag.class));
            put("Zombie Pigman: Anger level (0=neutral)", new NBTNodeDefinition("/Anger", ShortTag.class));
            put("Projectiles: X position in chunk", new NBTNodeDefinition("/xTile", ShortTag.class));
            put("Projectiles: Y position in chunk", new NBTNodeDefinition("/yTile", ShortTag.class));
            put("Projectiles: Z position in chunk", new NBTNodeDefinition("/zTile", ShortTag.class));
            put("Projectiles: ID of current tile", new NBTNodeDefinition("/inTile", StringTag.class));
            put("Projectiles: Hit the ground? (0=no, 1=yes)", new NBTNodeDefinition("/inGround", ByteTag.class));
            put("Projectiles: Shaking? (used for arrows; 0=no, 1=yes)", new NBTNodeDefinition("/shake", ByteTag.class));
            put("Arrow: Metadata of current tile", new NBTNodeDefinition("/inData", ByteTag.class));
            put("Arrow: Can be picked up? (0=no, 1=yes, 2=creative players only) [experimental]", new NBTNodeDefinition("/pickup", ByteTag.class));
            put("Arrow: Can be picked up? (0=no, 1=yes) [experimental]", new NBTNodeDefinition("/player", ByteTag.class));
            put("Arrow: Lifetime in ticks (despawns at 1200)", new NBTNodeDefinition("/life", ShortTag.class));
            put("Arrow: Damage level (2=normal, increases with Power enchantment)", new NBTNodeDefinition("/damage", DoubleTag.class));
            put("Fireball: Explosion power (1=default)", new NBTNodeDefinition("/ExplosionPower", IntTag.class));
            put("Throwables: Name of player who threw projectile", new NBTNodeDefinition("/ownerName", StringTag.class));
            put("Item/XP orb: Age in ticks (despawns at 6000, -32768=won't despawn)", new NBTNodeDefinition("/Age", ShortTag.class));
            put("Item: Health (5=default)", new NBTNodeDefinition("/Health", ShortTag.class));
            put("Item: Ticks left before item can be picked up (32767=can't be picked up)", new NBTNodeDefinition("/PickupDelay", ShortTag.class));
            put("Item: Can only be picked up by player name", new NBTNodeDefinition("/Owner", StringTag.class));
            put("Item: Name of player who threw item", new NBTNodeDefinition("/Thrower", StringTag.class));
            //put("", new NBTNodeDefinition("", )); // Item
            put("XP orb: Health (5=default) [experimental]", new NBTNodeDefinition("/Health", ByteTag.class));
            put("XP orb: Experience value contained", new NBTNodeDefinition("/Value", ShortTag.class));
            put("Vehicles: Displays custom block? (0=no, 1=yes) [experimental]", new NBTNodeDefinition("/CustomDisplayTile", ByteTag.class));
            put("Vehicles: Custom block ID", new NBTNodeDefinition("/DisplayTile", StringTag.class));
            put("Vehicles: Custom block metadata", new NBTNodeDefinition("/DisplayData", IntTag.class));
            put("Vehicles: Custom block vertical offset (16=+1 block height)", new NBTNodeDefinition("/DisplayOffset", IntTag.class));
            put("Vehicles: Name", new NBTNodeDefinition("/CustomName", StringTag.class));
            put("Minecart Furnace: X-axis momentum", new NBTNodeDefinition("/PushX", DoubleTag.class));
            put("Minecart Furnace: Z-axis momentum", new NBTNodeDefinition("/PushZ", DoubleTag.class));
            put("Minecart Furnace: Remaining fuel in ticks", new NBTNodeDefinition("/Fuel", ShortTag.class));
            put("Minecart Hopper: Time until next transfer (1-8; 0=no transfer)", new NBTNodeDefinition("/TransferCooldown", IntTag.class));
            put("Minecart TNT: Time until explosion (-1=deactivated)", new NBTNodeDefinition("/TNTFuse", IntTag.class));
            //put("Minecart Spawner:", new NBTNodeDefinition("", )); // MinecraftSpawner fields
            put("Minecart Command Block: Command", new NBTNodeDefinition("/Command", StringTag.class));
            put("Minecart Command Block: Redstone strength", new NBTNodeDefinition("/SuccessCount", IntTag.class));
            put("Minecart Command Block: Last output", new NBTNodeDefinition("/LastOutput", StringTag.class));
            put("Primed TNT: Ticks left until explosion (80=default, 4 seconds)", new NBTNodeDefinition("/Fuse", ByteTag.class));
            put("Moving blocks: Block ID [deprecated]", new NBTNodeDefinition("/Tile", ByteTag.class));
            put("Moving blocks: Block ID (extended; 1.7 and below)", new NBTNodeDefinition("/TileID", IntTag.class));
            put("Moving blocks: Block ID (1.8 and above)", new NBTNodeDefinition("/Block", StringTag.class));
            //put("", new NBTNodeDefinition("", )); TileEntityData
            put("Moving blocks: Block metadata", new NBTNodeDefinition("/Data", ByteTag.class));
            put("Moving blocks: Lifetime in ticks", new NBTNodeDefinition("/Time", ByteTag.class));
            put("Moving blocks: Drops item? (0=no, 1=yes)", new NBTNodeDefinition("/DropItem", ByteTag.class));
            put("Moving blocks: Hurts entities? (0=no, 1=yes)", new NBTNodeDefinition("/HurtEntities", ByteTag.class));
            put("Moving blocks: Maximum damage inflictable when hitting another entity (40=default, 20 hearts)", new NBTNodeDefinition("/FallHurtMax", IntTag.class));
            put("Moving blocks: Damage inflicted when hitting another entity (per fallen block, 2=default)", new NBTNodeDefinition("/FallHurtAmount", FloatTag.class));
            put("Firework rocket: Flying time in ticks", new NBTNodeDefinition("/Life", IntTag.class));
            put("Firework rocket: Time until explosion in ticks", new NBTNodeDefinition("/LifeTime", IntTag.class));
            //put("", new NBTNodeDefinition("", )); // FireworksItem
            put("Painting/item frame: X-coordinate of block placed on", new NBTNodeDefinition("/TileX", IntTag.class));
            put("Painting/item frame: Y-coordinate of block placed on", new NBTNodeDefinition("/TileY", IntTag.class));
            put("Painting/item frame: Z-coordinate of block placed on", new NBTNodeDefinition("/TileZ", IntTag.class));
            put("Painting/item frame: Facing (1.7 and below) (0=S, 1=W, 2=N, 3=E) [experimental]", new NBTNodeDefinition("/Facing", ByteTag.class));
            put("Painting/item frame: Facing (1.8 and above) (0=S, 1=W, 2=N, 3=E)", new NBTNodeDefinition("/Direction", ByteTag.class));
            //put("Item frame:", new NBTNodeDefinition("", )); // Item
            put("Item frame: Item drop chance when frame broken (1.0=default)", new NBTNodeDefinition("/ItemDropChance", FloatTag.class));
            put("Item frame: Item rotation (number of times rotated 45 degrees CW)", new NBTNodeDefinition("/ItemRotation", ByteTag.class));
            put("Painting: Motive name", new NBTNodeDefinition("/Motive", StringTag.class));
            /*put("Tile entities: ID", new NBTNodeDefinition("/id", StringTag.class));
            put("Tile entities: X coordinate", new NBTNodeDefinition("/x", IntTag.class));
            put("Tile entities: Y coordinate", new NBTNodeDefinition("/y", IntTag.class));
            put("Tile entities: Z coordinate", new NBTNodeDefinition("/z", IntTag.class));
            put("", new NBTNodeDefinition("", ));
            put("", new NBTNodeDefinition("", ));
            put("", new NBTNodeDefinition("", ));
            put("", new NBTNodeDefinition("", ));
            put("", new NBTNodeDefinition("", ));
            put("", new NBTNodeDefinition("", ));
            put("", new NBTNodeDefinition("", ));
            put("", new NBTNodeDefinition("", ));
            put("", new NBTNodeDefinition("", ));
            put("", new NBTNodeDefinition("", ));
            put("", new NBTNodeDefinition("", ));
            put("", new NBTNodeDefinition("", ));*/
            
        }});
    }};
    
    private static HashMap<String, NBTNodeDefinition> getForMaterialType(MaterialType type) {
        for (MaterialType t : guiPresets.keySet()) {
            if (t == type) {
                return guiPresets.get(t);
            }
        }
        return new HashMap<String, NBTNodeDefinition>();
    }
    
    private static HashMap<String, NBTNodeDefinition> reverseDictionary(HashMap<String, NBTNodeDefinition> dict) {
        HashMap<String, NBTNodeDefinition> ret = new HashMap<String, NBTNodeDefinition>();
        for (String s : dict.keySet()) {
            NBTNodeDefinition def = dict.get(s);
            ret.put(def.getNBTPath(), new NBTNodeDefinition(s, def.getTagType()));
        }
        return ret;
    }
    
    private final HashMap<String, NBTNodeDefinition> dict;
    private final HashMap<String, NBTNodeDefinition> reverse;
    
    public CommonNameDictionary(MaterialType type) {
        this.dict = getForMaterialType(type);
        this.reverse = reverseDictionary(this.dict);
    }
    
    public CommonNameDictionary(HashMap<String, NBTNodeDefinition> dict) {
        this.dict = dict;
        this.reverse = reverseDictionary(dict);
    }
    
    public String lookupName(String nbtPath) {
        if (this.reverse.containsKey(nbtPath)) {
            return this.reverse.get(nbtPath).getNBTPath();
        }
        return nbtPath;
    }
    
    public NBTNodeDefinition getNodeDefinitionForName(String name) {
        if (this.dict.containsKey(name)) {
            return this.dict.get(name);
        }
        return null;
    }
    
    public NBTNodeDefinition getNodeDefinitionForPath(String path) {
        return getNodeDefinitionForPath(path, null);
    }
    
    public NBTNodeDefinition getNodeDefinitionForPath(String path, Class<? extends Tag> fallbackClass) {
        if (this.reverse.containsKey(path)) {
            return this.dict.get(this.reverse.get(path).getNBTPath());
        }
        return new NBTNodeDefinition(path, fallbackClass);
    }
    
    public String lookupPath(String commonName) {
        if (this.dict.containsKey(commonName)) {
            return this.dict.get(commonName).getNBTPath();
        }
        return null;
    }
    
    public String[] getCommonNameList() {
        return this.dict.keySet().toArray(new String[0]);
    }
}
