/*
 * This file is part of Commodus.
 *
 * Commodus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Commodus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Commodus.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dsh105.commodus.configuration;

import com.dsh105.commodus.Affirm;
import com.dsh105.commodus.GeneralUtil;
import com.dsh105.commodus.StringUtil;
import com.dsh105.commodus.Transformer;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractConfig implements Config {

    private ConfigManager<?> manager;
    private File file;
    private Map<String, Object> locked = new HashMap<>();
    protected int totalComments;

    public AbstractConfig(ConfigManager<?> manager, File file, int totalComments) {
        this.manager = manager;
        this.file = file;
        this.totalComments = totalComments;
    }

    @Override
    public ConfigManager<?> getManager() {
        return manager;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public boolean isLocked(String path) {
        return locked.containsKey(path);
    }

    @Override
    public Object getLockedValue(String path) {
        return locked.get(path);
    }

    @Override
    public void lock(String path, Object value) {
        Affirm.notNull(path);
        locked.put(path, value);
    }

    @Override
    public void unlock(String path) {
        locked.remove(path);
    }

    @Override
    public Map<String, Object> getLockedValues() {
        return Collections.unmodifiableMap(locked);
    }

    @Override
    public void setHeader(String... header) {
        try {
            manager.writeHeader(this, header);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write header to configuration file.", e);
        }
    }

    @Override
    public void save() {
        manager.save(this);
        reload();
    }

    @Override
    public void reload() {
        try {
            reloadConfig();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration from file.", e);
        }
    }

    public abstract void reloadConfig() throws IOException;

    public abstract void set(String path, Object value);

    @Override
    public void set(String path, Object value, boolean save, String... comments) {
        if (!exists(path)) {
            for (String comment : comments) {
                set("CONFIG_COMMENT_" + this.totalComments++, " " + comment);
            }
        }

        set(path, value);
        if (save) {
            save();
        }
    }

    @Override
    public void set(String path, Object value, String... comments) {
        set(path, value, true, comments);
    }

    @Override
    public boolean exists(String path) {
        return get(path) != null;
    }

    @Override
    public Object get(String path) {
        return get(path, null);
    }

    @Override
    public <T> T get(String path, T defaultValue) {
        Object result = isLocked(path) ? getLockedValue(path) : getObject(path);
        try {
            return (T) result;
        } catch (ClassCastException ignored) {
        }
        return defaultValue;
    }

    @Override
    public String getString(String path) {
        return get(path, null);
    }

    @Override
    public int getInt(String path) {
        return get(path, 0);
    }

    @Override
    public boolean getBoolean(String path) {
        return get(path, false);
    }

    @Override
    public double getDouble(String path) {
        return get(path, 0.0D);
    }

    @Override
    public float getFloat(String path) {
        return get(path, 0.0F);
    }

    @Override
    public long getLong(String path) {
        return get(path, 0L);
    }

    @Override
    public List<?> getList(String path) {
        return get(path, null);
    }

    @Override
    public <T> List<T> getList(String path, Transformer<Object, T> transformer) {
        List<?> result = getList(path);
        if (result == null) {
            return null;
        }
        return GeneralUtil.transform((List<Object>) result, transformer);
    }

    @Override
    public List<String> getStringList(String path) {
        return getList(path, new Transformer<Object, String>() {
            @Override
            public String transform(Object transmutable) {
                return String.valueOf(transmutable);
            }
        });
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        return getList(path, new Transformer<Object, Integer>() {
            @Override
            public Integer transform(Object transmutable) {
                try {
                    return GeneralUtil.toInteger(String.valueOf(transmutable));
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        });
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        return getList(path, new Transformer<Object, Boolean>() {
            @Override
            public Boolean transform(Object transmutable) {
                if (transmutable instanceof String) {
                    return StringUtil.toBoolean((String) transmutable);
                } else if (transmutable instanceof Boolean) {
                    return (Boolean) transmutable;
                }
                return null;
            }
        });
    }

    @Override
    public List<Double> getDoubleList(String path) {
        return getList(path, new Transformer<Object, Double>() {
            @Override
            public Double transform(Object transmutable) {
                try {
                    return GeneralUtil.toDouble(String.valueOf(transmutable));
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        });
    }

    @Override
    public List<Float> getFloatList(String path) {
        return getList(path, new Transformer<Object, Float>() {
            @Override
            public Float transform(Object transmutable) {
                try {
                    return (float) GeneralUtil.toDouble(String.valueOf(transmutable));
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        });
    }

    @Override
    public List<Long> getLongList(String path) {
        return getList(path, new Transformer<Object, Long>() {
            @Override
            public Long transform(Object transmutable) {
                try {
                    return (long) GeneralUtil.toDouble(String.valueOf(transmutable));
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        });
    }

    @Override
    public List<Byte> getByteList(String path) {
        return getList(path, new Transformer<Object, Byte>() {
            @Override
            public Byte transform(Object transmutable) {
                if (transmutable instanceof Byte) {
                    return (byte) transmutable;
                } else if (transmutable instanceof Number) {
                    return ((Number) transmutable).byteValue();
                }

                try {
                    return Byte.valueOf(transmutable.toString());
                } catch (Exception e) {
                    return null;
                }
            }
        });
    }

    @Override
    public List<Character> getCharacterList(String path) {
        return getList(path, new Transformer<Object, Character>() {
            @Override
            public Character transform(Object transmutable) {
                if (transmutable instanceof Character) {
                    return (char) transmutable;
                } else if (transmutable instanceof Number) {
                    return (char) ((Number) transmutable).intValue();
                }

                return transmutable.toString().charAt(0);
            }
        });
    }

    @Override
    public List<Short> getShortList(String path) {
        return getList(path, new Transformer<Object, Short>() {
            @Override
            public Short transform(Object transmutable) {
                try {
                    return (short) GeneralUtil.toInteger(transmutable.toString());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        });
    }

    @Override
    public List<Map<?, ?>> getMapList(String path) {
        return getList(path, new Transformer<Object, Map<?, ?>>() {
            @Override
            public Map<?, ?> transform(Object transmutable) {
                if (transmutable instanceof Map) {
                    return (Map<?, ?>) transmutable;
                }
                return null;
            }
        });
    }
}