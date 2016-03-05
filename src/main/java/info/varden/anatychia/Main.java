/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.varden.anatychia;

import com.gamepedia.minecraft.ChunkFormat;
import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import net.minecraft.bootstrap.Util;
import net.minecraft.world.level.chunk.storage.RegionFile;
import org.apache.commons.io.FileUtils;
import org.jnbt.ByteArrayTag;
import org.jnbt.CompoundTag;
import org.jnbt.CompressionMode;
import org.jnbt.ListTag;
import org.jnbt.NBTInputStream;
import org.jnbt.NBTOutputStream;
import org.jnbt.StringTag;
import org.jnbt.Tag;

/**
 *
 * @author Marius
 */
public class Main {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        // SaveData sd = new SaveData(new File("C:\\Users\\Marius\\AppData\\Roaming\\.minecraft\\saves\\Craft Bandicoot World 1.0"), "Craft Bandicoot World 1.0", "Craft Bandicoot World 1.0");
        // materialList(sd);
        AnatychiaMain mn = new AnatychiaMain();
        mn.setLocationRelativeTo(null);
        mn.setVisible(true);
        //SplashScreen.main(args);
        /*final DoubleTag it = new DoubleTag("Test", 1.4D);
        final CompoundTag ct1 = new CompoundTag("someStore", new HashMap<String, Tag>() {{
            put("Test", it);
        }});
        CompoundTag root = new CompoundTag("", new HashMap<String, Tag>() {{
            put("someStore", ct1);
        }});
        MaterialData d = new MaterialData(MaterialType.BLOCK, "minecraft:air", 0, new NBTCriteria[] {
            new NBTCriteria("/someStore/Test", DoubleTag.class, "2", MatchMode.EQUALS_OR_SMALLER),
            new NBTCriteria("/someStore/Test", DoubleTag.class, "1.5", MatchMode.EQUALS_OR_GREATER)
        });
        System.out.println(d.fulfillsRequirements(root));*/
    }
    
    public static SaveData[] worldList() {
        BufferedReader br = null;
        ArrayList<String> locations = new ArrayList<String>();
        locations.add(Util.getWorkingDirectory().getAbsolutePath());
        try {
            File launcherProfiles = new File(Util.getWorkingDirectory(), "launcher_profiles.json");
            br = new BufferedReader(new FileReader(launcherProfiles));
            String content = "";
            String line;
            while ((line = br.readLine()) != null) {
                content += line + "\n";
            }
            br.close();
            
            JsonParserFactory factory = JsonParserFactory.getInstance();
            JSONParser parser = factory.newJsonParser();
            Map jsonData = parser.parseJson(content);
            Map profiles = (Map) jsonData.get("profiles");
            for (Object profile : profiles.keySet()) {
                Map profileData = (Map) profiles.get(profile);
                if (profileData.containsKey("gameDir")) {
                    locations.add((String) profileData.get("gameDir"));
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ArrayList<SaveData> worlds = new ArrayList<SaveData>();
        for (String location : locations) {
            File gameDir = new File(location);
            File savesDir = new File(gameDir, "saves");
            if (savesDir.exists()) {
                File[] saveFiles = savesDir.listFiles();
                for (File save : saveFiles) {
                    File levelDat = new File(save, "level.dat");
                    if (levelDat.exists()) {
                        try {
                            NBTInputStream nis = new NBTInputStream(new FileInputStream(levelDat), CompressionMode.GZIP);
                            CompoundTag root = (CompoundTag) nis.readTag();
                            Map<String, Tag> data = root.getValue();
                            Map<String, Tag> elements = ((CompoundTag) data.get("Data")).getValue();
                            StringTag nameTag = (StringTag) elements.get("LevelName");
                            String levelName = nameTag.getValue();
                            worlds.add(new SaveData(save, save.getName(), levelName));
                            nis.close();
                        } catch (Exception ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            worlds.add(new SaveData(save, save.getName(), "LEVEL.DAT FILE CORRUPT"));
                        }
                    } else {
                        worlds.add(new SaveData(save, save.getName(), "LEVEL.DAT FILE NOT FOUND"));
                    }
                }
            }
        }
        return worlds.toArray(new SaveData[0]);
    }
    
    public static MaterialDataList materialList(SaveData save, ProgressUpdater pu) {
        return materialList(save, pu, null);
    }
    
    public static MaterialDataList materialList(SaveData save, ProgressUpdater pu, MaterialData[] filters) {
        Random random = new Random();
        boolean filtersNull = filters == null;
        pu.updated(0, 1);
        pu.updated(-3, 1);
        MaterialDataList mdl = new MaterialDataList();
        File saveDir = save.getLocation();
        File[] regionFolders = listRegionContainers(saveDir);
        int depth = Integer.MAX_VALUE;
        File shallowest = null;
        for (File f : regionFolders) {
            String path = f.getAbsolutePath();
            Pattern p = Pattern.compile(Pattern.quote(File.separator));
            Matcher m = p.matcher(path);
            int count = 0;
            while (m.find()) {
                count++;
            }
            if (count < depth) {
                depth = count;
                if (shallowest == null || f.getName().equalsIgnoreCase("region")) {
                    shallowest = f;
                }
            }
        }
        pu.updated(-1, 1);
        ArrayList<File> regions = new ArrayList<File>();
        int tfs = 0;
        for (File f : regionFolders) {
            String dimName = f.getParentFile().getName();
            boolean deleted = false;
            if (f.equals(shallowest)) {
                dimName = "DIM0";
            }
            if (!filtersNull) {
                for (MaterialData type : filters) {
                    if (type.getType() == MaterialType.DIMENSION && type.getName().equals(dimName)) {
                        System.out.println("Deleting: " + dimName);
                        deleted = recursiveDelete(f);
                    }
                }
            }
            if (deleted) continue;
            mdl.increment(new MaterialData(MaterialType.DIMENSION, dimName, 1L));
            File[] r = f.listFiles(new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".mca");
                }
                
            });
            int max = r.length;
            int cur = 0;
            for (File valid : r) {
                cur++;
                try {
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(valid));
                    byte[] offsetHeader = new byte[4096];
                    bis.read(offsetHeader, 0, 4096);
                    bis.close();
                    ByteBuffer bb = ByteBuffer.wrap(offsetHeader);
                    IntBuffer ib = bb.asIntBuffer();
                    while (ib.remaining() > 0) {
                        if (ib.get() != 0) {
                            tfs++;
                        }
                    }
                    bb = null;
                    ib = null;
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                // tfs += Math.floor(valid.length() / 1000D);
                pu.updated(cur, max);
            }
            regions.addAll(Arrays.asList(r));
        }
        if (regions.size() <= 0) {
            pu.updated(1, 1);
            return mdl;
        }
        pu.updated(-2, 1);
        int fc = 0;
        int fs = 0;
        for (File region : regions) {
            fc++;
            //fs += Math.floor(region.length() / 1000D);
            try {
                RegionFile anvil = new RegionFile(region);
                for (int x = 0; x < 32; x++) {
                    for (int z = 0; z < 32; z++) {
                        InputStream is = anvil.getChunkDataInputStream(x, z);
                        if (is == null) continue;
                        NBTInputStream nbti = new NBTInputStream(is, CompressionMode.NONE);
                        CompoundTag root = (CompoundTag) nbti.readTag();
                        String rootName = root.getName();
                        CompoundTag level = (CompoundTag) root.getValue().get("Level");
                        Map<String, Tag> levelTags = level.getValue();
                        ListTag sectionTag = (ListTag) levelTags.get("Sections");
                        ArrayList<Tag> sections = new ArrayList<Tag>(sectionTag.getValue());
                        for (int i = 0; i < sections.size(); i++) {
                            mdl.setSectorsRelative(1);
                            CompoundTag sect = (CompoundTag) sections.get(i);
                            Map<String, Tag> sectTags = sect.getValue();
                            ByteArrayTag blockArray = (ByteArrayTag) sectTags.get("Blocks");
                            byte[] add = new byte[0];
                            boolean hasAdd = false;
                            if (sectTags.containsKey("Add")) {
                                hasAdd = true;
                                ByteArrayTag addArray = (ByteArrayTag) sectTags.get("Add");
                                add = addArray.getValue();
                            }
                            byte[] blocks = blockArray.getValue();
                            for (int j = 0; j < blocks.length; j++) {
                                short id;
                                byte aid = (byte) 0;
                                if (hasAdd) {
                                    aid = ChunkFormat.Nibble4(add, j);
                                    id = (short) ((blocks[j] & 0xFF) + (aid << 8));
                                } else {
                                    id = (short) (blocks[j] & 0xFF);
                                }
                                if (!filtersNull) {
                                    for (MaterialData type : filters) {
                                        if (type.getType() == MaterialType.BLOCK && type.getName().equals(String.valueOf(blocks[j] & 0xFF)) && (type.getRemovalChance() == 1D || random.nextDouble() < type.getRemovalChance())) {
                                            blocks[j] = (byte) 0;
                                            if (aid != 0) {
                                                add[j / 2] = (byte) (add[j / 2] & (j % 2 == 0 ? 0xF0 : 0x0F));
                                            }
                                            id = (short) 0;
                                        }
                                    }
                                }
                                mdl.increment(new MaterialData(MaterialType.BLOCK, String.valueOf(id), 1L));
                            }
                            if (!filtersNull) {
                                HashMap<String, Tag> rSectTags = new HashMap<String, Tag>();
                                rSectTags.putAll(sectTags);
                                ByteArrayTag bat = new ByteArrayTag("Blocks", blocks);
                                rSectTags.put("Blocks", bat);
                                if (hasAdd) {
                                    ByteArrayTag adt = new ByteArrayTag("Add", add);
                                    rSectTags.put("Add", adt);
                                }
                                CompoundTag rSect = new CompoundTag(sect.getName(), rSectTags);
                                sections.set(i, rSect);
                            }
                        }
                        ListTag entitiesTag = (ListTag) levelTags.get("Entities");
                        ArrayList<Tag> entities = new ArrayList<Tag>(entitiesTag.getValue());
                        for (int i = entities.size() - 1; i >= 0; i--) {
                            CompoundTag entity = (CompoundTag) entities.get(i);
                            Map<String, Tag> entityTags = entity.getValue();
                            if (entityTags.containsKey("id")) {
                                StringTag idTag = (StringTag) entityTags.get("id");
                                String id = idTag.getValue();
                                boolean removed = false;
                                if (!filtersNull) {
                                    for (MaterialData type : filters) {
                                        if (type.getType() == MaterialType.ENTITY && (type.getName().equals(id) || type.getName().equals("")) && (type.getRemovalChance() == 1D || random.nextDouble() < type.getRemovalChance())) {
                                            if (type.fulfillsRequirements(entity)) {
                                                entities.remove(i);
                                                removed = true;
                                            }
                                        }
                                    }
                                }
                                if (!removed) {
                                    mdl.increment(new MaterialData(MaterialType.ENTITY, id, 1L));
                                }
                            }
                        }
                        nbti.close();
                        is.close();
                        if (!filtersNull) {
                            HashMap<String, Tag> rLevelTags = new HashMap<String, Tag>();
                            rLevelTags.putAll(levelTags);
                            ListTag rSectionTag = new ListTag("Sections", CompoundTag.class, sections);
                            rLevelTags.put("Sections", rSectionTag);
                            ListTag rEntityTag = new ListTag("Entities", CompoundTag.class, entities);
                            rLevelTags.put("Entities", rEntityTag);
                            final CompoundTag rLevel = new CompoundTag("Level", rLevelTags);
                            HashMap<String, Tag> rRootTags = new HashMap<String, Tag>() {{
                                put("Level", rLevel);
                            }};
                            CompoundTag rRoot = new CompoundTag(rootName, rRootTags);
                            OutputStream os = anvil.getChunkDataOutputStream(x, z);
                            NBTOutputStream nbto = new NBTOutputStream(os, CompressionMode.NONE);
                            nbto.writeTag(rRoot);
                            nbto.close();
                        }
                        fs++;
                        pu.updated(fs, tfs);
                    }
                }
                anvil.close();
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        MaterialData[] data = mdl.toArray();
        System.out.println("FILES SCANNED: " + fc);
        for (MaterialData d : data) {
            System.out.println(d.getType().getName() + ": " + d.getName() + " (" + d.getQuantity() + ")");
        }
        return mdl;
    }
    
    private static File[] listRegionContainers(File base) {
        ArrayList<File> ret = new ArrayList<File>();
        File[] contents = base.listFiles();
        for (File f : contents) {
            if (f.isDirectory()) {
                ret.addAll(Arrays.asList(listRegionContainers(f)));
            } else {
                if (f.getName().endsWith(".mca") && !ret.contains(base)) {
                    ret.add(base);
                }
            }
        }
        return ret.toArray(new File[0]);
    }
    
    public static String jsonDecode(String json) {
        String p1 = json.replace("\\\"", "\"").replace("\\/", "/").replace("\\b", "\b")
                        .replace("\\f", "\f").replace("\\n", "\n").replace("\\t", "\t");
        Pattern p = Pattern.compile("\\\\u[0-9A-Fa-f]{4}");
        Matcher m = p.matcher(p1);
        while (m.find()) {
            char c = (char) Integer.parseInt(m.group().substring(2), 16);
            p1 = p1.replaceFirst(Pattern.quote(m.group()), String.valueOf(c));
        }
        p1 = p1.replace("\\\\", "\\");
        return p1;
    }
    
    public static String jsonEncode(String plain) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plain.length(); i++) {
            int c = plain.charAt(i);
            if (c > 0x7F) {
                String ics = String.valueOf(c);
                while (ics.length() < 4) {
                    ics = "0" + ics;
                }
                sb.append("\\u").append(ics);
            } else if (c == '\\') {
                sb.append("\\\\");
            } else if (c == '\"') {
                sb.append("\\\"");
            } else if (c == '/') {
                sb.append("\\/");
            } else if (c == '\b') {
                sb.append("\\b");
            } else if (c == '\f') {
                sb.append("\\f");
            } else if (c == '\n') {
                sb.append("\\n");
            } else if (c == '\t') {
                sb.append("\\t");
            } else {
                sb.append((char) c);
            }
        }
        return sb.toString();
    }
    
    public static String xmlEncode(String plain) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plain.length(); i++) {
            int c = plain.charAt(i);
            if (c < 0x20 || (c > 0x20 && c < 0x28) || (c > 0x39 && c < 0x41) || c > 0x7E) {
                String ics = String.valueOf(c);
                while (ics.length() < 4) {
                    ics = "0" + ics;
                }
                sb.append("&#").append(ics).append(";");
            } else {
                sb.append((char) c);
            }
        }
        return sb.toString();
    }
    
    public static String yamlEncode(String plain) {
        return jsonEncode(plain).replace("\\/", "/");
    }
    
    public static boolean recursiveDelete(File d) {
        System.out.println("Deleting: " + d.getAbsolutePath());
        try {
            if (d.isDirectory()) {
                File[] fs = d.listFiles();
                for (File f : fs) {
                    recursiveDelete(f);
                }
            }
            if (!FileUtils.isSymlink(d)) {
                System.out.println("Deleting file..");
                return d.delete();
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static String getTextFromJarPath(String path) {
        try {
            InputStream input = Main.class.getResourceAsStream(path);
            Scanner s = new Scanner(input, "UTF-8").useDelimiter("\\A");
            String str = s.hasNext() ? s.next() : "Error: Failed to read file " + path;
            s.close();
            input.close();
            return str;
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return "Error: Failed to read file " + path;
        }
    }
}
