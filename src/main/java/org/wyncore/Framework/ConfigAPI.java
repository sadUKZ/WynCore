package org.wyncore.Framework;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ConfigAPI {

    private final JavaPlugin plugin;
    private final File folder;
    private final File file;
    private FileConfiguration config;

    private ConfigAPI(JavaPlugin plugin, String folderName, String fileName, boolean createEmpty) {
        this.plugin = plugin;

        if (!fileName.endsWith(".yml")) {
            fileName += ".yml";
        }

        this.folder = folderName == null || folderName.isEmpty()
                ? plugin.getDataFolder()
                : new File(plugin.getDataFolder(), folderName);

        this.file = new File(folder, fileName);

        createFolder();

        if (createEmpty) {
            createFile();
        }

        reload();
    }

    // Cria arquivo vazio
    public static ConfigAPI create(JavaPlugin plugin, String folderName, String fileName) {
        return new ConfigAPI(plugin, folderName, fileName, true);
    }

    // Cria arquivo vazio na pasta principal
    public static ConfigAPI create(JavaPlugin plugin, String fileName) {
        return new ConfigAPI(plugin, "", fileName, true);
    }

    // Apenas carrega arquivo existente, NÃO cria vazio
    public static ConfigAPI load(JavaPlugin plugin, String folderName, String fileName) {
        return new ConfigAPI(plugin, folderName, fileName, false);
    }

    // Apenas carrega arquivo existente na pasta principal
    public static ConfigAPI load(JavaPlugin plugin, String fileName) {
        return new ConfigAPI(plugin, "", fileName, false);
    }

    // Carrega do resources e copia o default se não existir
    public static ConfigAPI loadDefault(JavaPlugin plugin, String folderName, String fileName, String resourcePath) {
        ConfigAPI configAPI = new ConfigAPI(plugin, folderName, fileName, false);
        configAPI.saveDefaultFromResource(resourcePath);
        return configAPI;
    }

    // Carrega default da pasta principal
    public static ConfigAPI loadDefault(JavaPlugin plugin, String fileName) {
        ConfigAPI configAPI = new ConfigAPI(plugin, "", fileName, false);
        configAPI.saveDefaultFromResource(fileName);
        return configAPI;
    }

    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void createFile() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Erro ao criar arquivo " + file.getName());
                e.printStackTrace();
            }
        }
    }

    public void saveDefaultFromResource(String resourcePath) {
        if (resourcePath == null || resourcePath.trim().isEmpty()) {
            throw new IllegalArgumentException("resourcePath não pode ser vazio.");
        }

        resourcePath = resourcePath.replace("\\", "/");

        if (!file.exists()) {
            plugin.saveResource(resourcePath, false);
        }

        reload();
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Erro ao salvar " + file.getName());
            e.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Path não pode ser vazio.");
        }

        config.set(path, value);
    }

    public void setAndSave(String path, Object value) {
        set(path, value);
        save();
    }

    public Object get(String path) {
        return config.get(path);
    }

    public String getString(String path) {
        return config.getString(path);
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public double getDouble(String path) {
        return config.getDouble(path);
    }

    public boolean contains(String path) {
        return config.contains(path);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }

    public File getFolder() {
        return folder;
    }
}