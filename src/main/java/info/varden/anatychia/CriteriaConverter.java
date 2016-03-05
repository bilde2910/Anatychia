/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.varden.anatychia;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.DataFormatException;
import org.jnbt.ByteTag;
import org.jnbt.CompoundTag;
import org.jnbt.CompressionMode;
import org.jnbt.DoubleTag;
import org.jnbt.FloatTag;
import org.jnbt.IntTag;
import org.jnbt.ListTag;
import org.jnbt.LongTag;
import org.jnbt.NBTInputStream;
import org.jnbt.NBTOutputStream;
import org.jnbt.ShortTag;
import org.jnbt.StringTag;
import org.jnbt.Tag;

/**
 *
 * @author Marius
 */
public class CriteriaConverter {
    public static CompoundTag toNBT(HashMap<String, MaterialData> filters) {
        final ArrayList<Tag> filterList = new ArrayList<Tag>();
        Set<Entry<String, MaterialData>> es = filters.entrySet();
        for (Entry<String, MaterialData> e : es) {
            final String key = e.getKey();
            final MaterialData value = e.getValue();
            NBTCriterion[] criteria = value.getEntityCriteria();
            ArrayList<Tag> tfTags = new ArrayList<Tag>();
            for (final NBTCriterion c : criteria) {
                CompoundTag ct = new CompoundTag("", new HashMap<String, Tag>() {{
                    put("nbtPath", new StringTag("nbtPath", c.getNBTPath()));
                    put("compareMode", new IntTag("compareMode", c.getMatchMode().getCompareMode()));
                    try {
                        if (c.getTagType() == ByteTag.class) {
                            put("value", new ByteTag("value", Byte.parseByte(c.getValue())));
                        } else if (c.getTagType() == ShortTag.class) {
                            put("value", new ShortTag("value", Short.parseShort(c.getValue())));
                        } else if (c.getTagType() == IntTag.class) {
                            put("value", new IntTag("value", Integer.parseInt(c.getValue())));
                        } else if (c.getTagType() == LongTag.class) {
                            put("value", new LongTag("value", Long.parseLong(c.getValue())));
                        } else if (c.getTagType() == FloatTag.class) {
                            put("value", new FloatTag("value", Float.parseFloat(c.getValue())));
                        } else if (c.getTagType() == DoubleTag.class) {
                            put("value", new DoubleTag("value", Double.parseDouble(c.getValue())));
                        } else {
                            put("value", new StringTag("value", c.getValue()));
                        }
                    } catch (Exception e) {
                        put("value", new StringTag("value", c.getValue()));
                    }
                }});
                tfTags.add(ct);
            }
            final ListTag lt = new ListTag("criteria", CompoundTag.class, tfTags);
            CompoundTag ct = new CompoundTag("", new HashMap<String, Tag>() {{
                put("type", new IntTag("type", value.getType().getID()));
                put("name", new StringTag("name", key));
                put("probability", new DoubleTag("probability", value.getRemovalChance()));
                put("criteria", lt);
            }});
            filterList.add(ct);
        }
        HashMap<String, Tag> filtersAsNBT = new HashMap<String, Tag>() {{
            put("filters", new ListTag("filters", CompoundTag.class, filterList));
        }};
        return new CompoundTag("root", filtersAsNBT);
    }
    
    public static byte[] toNBTBytes(HashMap<String, MaterialData> filters, CompressionMode mode) throws IOException, DataFormatException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        toNBTStream(filters, baos, mode);
        return baos.toByteArray();
    }
    
    public static void toNBTFile(HashMap<String, MaterialData> filters, File target, CompressionMode mode) throws FileNotFoundException, IOException, DataFormatException {
        FileOutputStream fos = new FileOutputStream(target);
        toNBTStream(filters, fos, mode);
    }
    
    public static void toNBTStream(final HashMap<String, MaterialData> filters, OutputStream stream, CompressionMode mode) throws IOException, DataFormatException {
        NBTOutputStream nbot = new NBTOutputStream(stream, mode);
        nbot.writeTag(toNBT(filters));
        nbot.close();
    }
    
    public static HashMap<String, MaterialData> fromNBT(CompoundTag tag) {
        ArrayList<String> triedNames = new ArrayList<String>();
        HashMap<String, MaterialData> filters = new HashMap<String, MaterialData>();
        Map<String, Tag> filtersAsNBT = tag.getValue();
        if (!filtersAsNBT.containsKey("filters")) return new HashMap<String, MaterialData>();
        if (!(filtersAsNBT.get("filters") instanceof ListTag)) return new HashMap<String, MaterialData>();
        if (!((ListTag) filtersAsNBT.get("filters")).getType().equals(CompoundTag.class)) return new HashMap<String, MaterialData>();
        List<Tag> filterList = ((ListTag) filtersAsNBT.get("filters")).getValue();
        for (Tag e : filterList) {
            Map<String, Tag> value = ((CompoundTag) e).getValue();
            if (!value.containsKey("criteria") || !value.containsKey("type")) continue;
            if (!(value.get("criteria") instanceof ListTag) || !(value.get("type") instanceof IntTag)) continue;
            if (!((ListTag) value.get("criteria")).getType().equals(CompoundTag.class)) continue;
            MaterialType type = MaterialType.getFromID(((IntTag) value.get("type")).getValue());
            if (type == null) continue;
            List<Tag> criteriaList = ((ListTag) value.get("criteria")).getValue();
            String key = null;
            if (value.containsKey("name")) {
                if (value.get("name") instanceof StringTag) {
                    key = ((StringTag) value.get("name")).getValue();
                }
            }
            if (key == null) {
                key = "Untitled " + type.getName() + " filter";
                int attempt = 2;
                while (triedNames.contains(key)) {
                    key = String.format("Untitled " + type.getName() + " filter (%s)", attempt);
                    attempt++;
                }
            }
            triedNames.add(key);
            ArrayList<NBTCriterion> nc = new ArrayList<NBTCriterion>();
            for (Tag t : criteriaList) {
                Map<String, Tag> ctt = ((CompoundTag) t).getValue();
                if (!ctt.containsKey("nbtPath") || !ctt.containsKey("compareMode") || !ctt.containsKey("value")) continue;
                if (!(ctt.get("nbtPath") instanceof StringTag) || !(ctt.get("compareMode") instanceof IntTag)) continue;
                nc.add(new NBTCriterion(
                        ((StringTag) ctt.get("nbtPath")).getValue(),
                        (Class<? extends Tag>) ctt.get("value").getClass(),
                        String.valueOf(ctt.get("value").getValue()),
                        MatchMode.getModeFromIntMode(((IntTag) ctt.get("compareMode")).getValue())
                ));
            }
            MaterialData md = new MaterialData(type, "*", 0, nc.toArray(new NBTCriterion[0]), 1D);
            if (value.containsKey("probability") && value.get("probability") instanceof DoubleTag) {
                md.setRemovalChance(((DoubleTag) value.get("probability")).getValue());
            };
            filters.put(key, md);
        }
        return filters;
    }
    
    public static HashMap<String, MaterialData> fromNBTBytes(byte[] data, CompressionMode mode) throws IOException, DataFormatException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        HashMap<String, MaterialData> filters = fromNBTStream(bais, mode);
        return filters;
    }
    
    public static HashMap<String, MaterialData> fromNBTFile(File source, CompressionMode mode) throws FileNotFoundException, IOException, DataFormatException {
        FileInputStream fis = new FileInputStream(source);
        HashMap<String, MaterialData> filters = fromNBTStream(fis, mode);
        return filters;
    }
    
    public static HashMap<String, MaterialData> fromNBTStream(InputStream is, CompressionMode mode) throws IOException, DataFormatException {
        NBTInputStream nbit = new NBTInputStream(is, mode);
        Tag rt = nbit.readTag();
        nbit.close();
        if (rt instanceof CompoundTag) {
            return fromNBT((CompoundTag) rt);
        } else {
            return new HashMap<String, MaterialData>();
        }
    }
    
    public static String toJSON(HashMap<String, MaterialData> filters) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append(tabs(1F)).append("\"filters\": [\n");
        Set<Entry<String, MaterialData>> es = filters.entrySet();
        for (Entry<String, MaterialData> e : es) {
            String key = e.getKey();
            MaterialData value = e.getValue();
            sb.append(tabs(2F)).append("{\n");
            sb.append(tabs(3F)).append("\"type\": ").append(value.getType().getID()).append(",\n");
            sb.append(tabs(3F)).append("\"name\": \"").append(Main.jsonEncode(key)).append("\",\n");
            sb.append(tabs(3F)).append("\"probability\": ").append(value.getRemovalChance()).append(",\n");
            sb.append(tabs(3F)).append("\"criteria\": [\n");
            NBTCriterion[] cs = value.getEntityCriteria();
            for (NBTCriterion c : cs) {
                sb.append(tabs(4F)).append("{\n");
                sb.append(tabs(5F)).append("\"compareMode\": ").append(c.getMatchMode().getCompareMode()).append(",\n");
                sb.append(tabs(5F)).append("\"nbtPath\": \"").append(Main.jsonEncode(c.getNBTPath())).append("\",\n");
                sb.append(tabs(5F)).append("\"type\": \"").append(Main.jsonEncode(c.getTagType().getName())).append("\",\n");
                sb.append(tabs(5F)).append("\"value\": \"").append(Main.jsonEncode(c.getValue())).append("\"\n");
                sb.append(tabs(4F)).append("},\n");
            }
            sb.setLength(sb.length() - 2);
            sb.append("\n").append(tabs(3F)).append("]\n");
            sb.append(tabs(2F)).append("},\n");
        }
        sb.setLength(sb.length() - 2);
        sb.append("\n").append(tabs(1F)).append("]\n");
        sb.append("}\n");
        return sb.toString();
    }
    
    public static byte[] toJSONBytes(HashMap<String, MaterialData> filters, Charset charset) {
        return toJSON(filters).getBytes(charset);
    }
    
    public static void toJSONFile(HashMap<String, MaterialData> filters, File target, Charset charset) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(target);
        toJSONStream(filters, fos, charset);
    }
    
    public static void toJSONStream(final HashMap<String, MaterialData> filters, OutputStream stream, Charset charset) throws IOException {
        byte[] b = toJSONBytes(filters, charset);
        stream.write(b, 0, b.length);
        stream.flush();
        stream.close();
    }
    
    public static String toXML(HashMap<String, MaterialData> filters, Charset charset) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"").append(charset.name()).append("\"?>\n");
        sb.append("<data>\n");
        sb.append(tabs(1F)).append("<filters>\n");
        Set<Entry<String, MaterialData>> es = filters.entrySet();
        for (Entry<String, MaterialData> e : es) {
            String key = e.getKey();
            MaterialData value = e.getValue();
            sb.append(tabs(2F)).append("<filter>\n");
            sb.append(tabs(3F)).append("<type>").append(value.getType().getID()).append("</type>\n");
            sb.append(tabs(3F)).append("<name>").append(Main.xmlEncode(key)).append("</name>\n");
            sb.append(tabs(3F)).append("<probability>").append(value.getRemovalChance()).append("</probability>\n");
            sb.append(tabs(3F)).append("<criteria>\n");
            NBTCriterion[] cs = value.getEntityCriteria();
            for (NBTCriterion c : cs) {
                sb.append(tabs(4F)).append("<criterion>\n");
                sb.append(tabs(5F)).append("<compareMode>").append(c.getMatchMode().getCompareMode()).append("</compareMode>\n");
                sb.append(tabs(5F)).append("<nbtPath>").append(Main.xmlEncode(c.getNBTPath())).append("</nbtPath>\n");
                sb.append(tabs(5F)).append("<type>").append(Main.xmlEncode(c.getTagType().getName())).append("</type>\n");
                sb.append(tabs(5F)).append("<value>").append(Main.xmlEncode(c.getValue())).append("</value>\n");
                sb.append(tabs(4F)).append("</criterion>\n");
            }
            sb.append(tabs(3F)).append("</criteria>\n");
            sb.append(tabs(2F)).append("</filter>\n");
        }
        sb.append(tabs(1F)).append("</filters>\n");
        sb.append("</data>\n");
        return sb.toString();
    }
    
    public static byte[] toXMLBytes(HashMap<String, MaterialData> filters, Charset charset) {
        return toXML(filters, charset).getBytes(charset);
    }
    
    public static void toXMLFile(HashMap<String, MaterialData> filters, File target, Charset charset) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(target);
        toXMLStream(filters, fos, charset);
    }
    
    public static void toXMLStream(final HashMap<String, MaterialData> filters, OutputStream stream, Charset charset) throws IOException {
        byte[] b = toXMLBytes(filters, charset);
        stream.write(b, 0, b.length);
        stream.flush();
        stream.close();
    }
    
    public static String toYAML(HashMap<String, MaterialData> filters) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- \n");
        sb.append("filters: \n");
        Set<Entry<String, MaterialData>> es = filters.entrySet();
        for (Entry<String, MaterialData> e : es) {
            String key = e.getKey();
            MaterialData value = e.getValue();
            sb.append(tabs(1F)).append("-\n");
            sb.append(tabs(2F)).append("type: ").append(value.getType().getID()).append("\n");
            sb.append(tabs(2F)).append("name: \"").append(Main.yamlEncode(key)).append("\"\n");
            sb.append(tabs(2F)).append("probability: ").append(value.getRemovalChance()).append("\n");
            sb.append(tabs(2F)).append("criteria: \n");
            NBTCriterion[] cs = value.getEntityCriteria();
            for (NBTCriterion c : cs) {
                sb.append(tabs(3F)).append("- \n");
                sb.append(tabs(4F)).append("compareMode: ").append(c.getMatchMode().getCompareMode()).append("\n");
                sb.append(tabs(4F)).append("nbtPath: \"").append(Main.yamlEncode(c.getNBTPath())).append("\"\n");
                sb.append(tabs(4F)).append("type: \"").append(Main.yamlEncode(c.getTagType().getName())).append("\"\n");
                sb.append(tabs(4F)).append("value: \"").append(Main.yamlEncode(c.getValue())).append("\"\n");
            }
        }
        return sb.toString();
    }
    
    public static byte[] toYAMLBytes(HashMap<String, MaterialData> filters, Charset charset) {
        return toYAML(filters).getBytes(charset);
    }
    
    public static void toYAMLFile(HashMap<String, MaterialData> filters, File target, Charset charset) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(target);
        toYAMLStream(filters, fos, charset);
    }
    
    public static void toYAMLStream(final HashMap<String, MaterialData> filters, OutputStream stream, Charset charset) throws IOException {
        byte[] b = toYAMLBytes(filters, charset);
        stream.write(b, 0, b.length);
        stream.flush();
        stream.close();
    }
    
    private static String tabs(float count) {
        String s = "";
        for (int i = 0; i < count * 2; i++) {
            s += " ";
        }
        return s;
    }
}
