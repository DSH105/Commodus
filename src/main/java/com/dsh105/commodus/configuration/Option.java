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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a type-specified configuration option that interacts with a {@link com.dsh105.commodus.configuration.Config}
 * file. Options work best in conjunction with an {@link com.dsh105.commodus.configuration.OptionSet} to provide global
 * access to configuration options for certain files.
 * <p/>
 * Paths given for an Option may be incomplete, and will therefore require input via the {@code replacements} array in
 * each of the available get/set methods in order to function correctly. Child options with completed paths can then be
 * constructed using {@link #from(Option, String...)}.
 * <p/>
 * Incomplete paths may be specified when creating an Option as follows: {@code example.%s.value.%s}. A value can then
 * be retrieved by providing values to be substituted in for %s: {@code getValue(\\"foo\\", \\"bar\\")} (the path generated
 * would be {@code example.foo.value.bar}.
 * <p/>
 * Please refer to the documentation attached to {@link #getPath(String...)} for more information on incomplete path
 * specification.
 * <p/>
 * Default values, returned when a requested value for an Option is not found, may also be specified.
 *
 * @param <V> type of object that is expected to be returned by this option
 */
public class Option<V> {

    private Config config;
    private String path;
    private V defaultValue;
    // TODO: perhaps this should be a map: replacements -> comments (Map<String, List<String>>)
    private List<String> comments = new ArrayList<>();

    /**
     * Constructs a new Option for the given {@link com.dsh105.commodus.configuration.Config} and path.
     * <p/>
     * The default value for an option created using this constructor is null, and as such will not be considered when a
     * value is requested.
     *
     * @param config   config in which the new option is nested.
     * @param path     path under which the values that may be requested for the new option can be found
     * @param comments comments that should be applied to the new option when saving to the {@code config} file
     */
    public Option(Config config, String path, String... comments) {
        this(config, path, null, comments);
    }

    /**
     * Constructs a new Option for the given {@link com.dsh105.commodus.configuration.Config} and path with a default
     * value.
     *
     * @param config       config in which the new option is nested.
     * @param path         path under which the values that may be requested for the new option can be found
     * @param defaultValue value to be returned when a value is not present in the {@code config} file
     * @param comments     comments that should be applied to the new option when saving to the {@code config} file
     */
    public Option(Config config, String path, V defaultValue, String... comments) {
        this.config = config;
        this.path = path;
        this.defaultValue = defaultValue;
        this.addComments(comments);
    }

    /**
     * Constructs a new Option for the given {@link com.dsh105.commodus.configuration.Config} and path.
     * <p/>
     * The default value for an option created using this constructor is null, and as such will not be considered when a
     * value is requested.
     *
     * @param config   config in which the new option is nested.
     * @param path     path under which the values that may be requested for the new option can be found
     * @param comments comments that should be applied to the new option when saving to the {@code config} file
     */
    public Option(Config config, String path, List<String> comments) {
        this(config, path, null, comments);
    }

    /**
     * Constructs a new Option for the given {@link com.dsh105.commodus.configuration.Config} and path with a default
     * value.
     *
     * @param config       config in which the new option is nested.
     * @param path         path under which the values that may be requested for the new option can be found
     * @param defaultValue value to be returned when a value is not present in the {@code config} file
     * @param comments     comments that should be applied to the new option when saving to the {@code config} file
     */
    public Option(Config config, String path, V defaultValue, List<String> comments) {
        this.config = config;
        this.path = path;
        this.defaultValue = defaultValue;
        this.addComments(comments);
    }

    /**
     * Constructs a new child Option from the give one, applying any specified path replacements.
     *
     * @param option       option to base the new child option on
     * @param replacements path replacements to be applied to the path of {@code option}
     * @param <V>          type of object that is expected to be returned by both the given option and the constructed
     *                     child
     * @return the constructed child option with the given path replacements applied
     * @see #getPath(String...)
     */
    public static <V> Option<V> from(Option<V> option, String... replacements) {
        return new Option<>(option.config, option.getPath(replacements), option.defaultValue, new ArrayList<>(option.comments));
    }

    /**
     * Gets the config in which this option is nested.
     *
     * @return config in which this option is nested
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Gets the default value for this option.
     * <p/>
     * If a requested value (via {@link #getValue(String...)}) for this option is not present in the config file, the
     * default value will be returned instead.
     *
     * @return default value for this option, or null if none is specified
     */
    public V getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the default value for this option
     *
     * @param defaultValue new default value to be applied to this option
     */
    public void setDefaultValue(V defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Gets the configuration comments for this option.
     * <p/>
     * Comments are visible above this option when saved to the {@code config}
     *
     * @return comments for this option
     */
    public List<String> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public String[] getCommentsArray() {
        return Collections.unmodifiableList(comments).toArray(new String[0]);
    }

    // TODO: proper comment editing API. These methods are 'protected' as they are not intended to be used/depended on yet

    protected void setComments(List<String> comments) {
        this.comments = comments != null ? comments : new ArrayList<String>();
    }

    protected void addComments(String... comments) {
        Affirm.notNull(comments);
        Collections.addAll(this.comments, comments);
    }

    protected void addComments(List<String> comments) {
        Affirm.notNull(comments);
        this.comments.addAll(comments);
    }

    /**
     * Retrieves the path at which configuration values for this Option can be found.
     * <p/>
     * The given values in the {@code replacements} array will be used to complete the path if any substitutable
     * elements are present.
     *
     * @param replacements Arguments to be injected into the returned path for any substitutable elements present. As
     *                     per {@link String#format(String, Object...)}, any additional arguments will be ignored and an
     *                     {@link java.util.IllegalFormatException} will be thrown if an insufficient number of
     *                     arguments is provided.
     * @return The path for this Option, injected with the given {@code replacements}
     * @throws java.util.IllegalFormatException if an insufficient number of arguments is provided in {@code
     *                                          replacements}, leaving an incomplete path
     */
    public String getPath(String... replacements) {
        if (replacements == null) {
            return path;
        }
        String path = String.format(this.path, replacements);
        while (path.endsWith(".")) {
            path = path.substring(0, path.length() - 2);
        }
        return path;
    }

    /**
     * Retrieves a value for this Option from the configuration file under which it is nested. The path specified during
     * the construction of this Option will be injected with the supplied {@code replacements}.
     *
     * @param replacements Arguments to be injected into the configuration path for any substitutable elements present.
     *                     See {@link #getPath(String...)} for documentation on this parameter.
     * @return A value for this option mapped to the generated path
     * @see #getPath(String...) for documentation on the {@code replacements} parameter.
     */
    public V getValue(String... replacements) {
        return config.get(getPath(replacements), defaultValue);
    }

    /**
     * Sets the value associated with this Option in the configuration file under which it is nested. The path specified
     * during the construction of this Option will be injected with the supplied {@code replacements}.
     *
     * @param value        Value to set this Option to in the configuration file
     * @param replacements Arguments to be injected into the configuration path for any substitutable elements present.
     *                     See {@link #getPath(String...)} for documentation on this parameter.
     * @see #getPath(String...) for documentation on the {@code replacements} parameter.
     */
    public void setValue(V value, String... replacements) {
        config.set(getPath(replacements), value, true, comments.toArray(new String[0]));
    }

    /**
     * Gets whether this option is currently locked to a specific value when the path is injected with the given {@code
     * replacements}. The path specified during the construction of this Option will be injected with the supplied
     * {@code replacements}.
     * <p/>
     * The return value of this method is dependent on the path replacements provided.
     * <p/>
     * This method proxies {@link Config#isLocked(String)}.
     *
     * @param replacements Arguments to be injected into the configuration path for any substitutable elements present.
     *                     See {@link #getPath(String...)} for documentation on this parameter.
     * @return True if this option is currently locked, false if not.
     * @see #getPath(String...) for documentation on the {@code replacements} parameter.
     * @see Config#isLocked(String)
     */
    public boolean isLocked(String... replacements) {
        return config.isLocked(getPath(replacements));
    }

    /**
     * Gets the locked value for this option when the path is injected with the given {@code replacements}. The path
     * specified during the construction of this Option will be injected with the supplied {@code replacements}.
     * <p/>
     * The return value of this method is dependent on the path replacements provided.
     * <p/>
     * This method proxies {@link Config#getLockedValue(String)}
     *
     * @param replacements Arguments to be injected into the configuration path for any substitutable elements present.
     *                     See {@link #getPath(String...)} for documentation on this parameter.
     * @return The locked value for this option
     * @see #getPath(String...) for documentation on the {@code replacements} parameter.
     * @see Config#getLockedValue(String)
     */
    public V getLockedValue(String... replacements) {
        try {
            return (V) config.getLockedValue(getPath(replacements));
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Illegal type for locked value.", e);
        }
    }

    /**
     * Locks this option to a certain value when the path is injected with the given {@code replacements}. The path
     * specified during the construction of this Option will be injected with the supplied {@code replacements}.
     * <p/>
     * The return value of this method is dependent on the path replacements provided.
     * <p/>
     * This method proxies {@link Config#lock(String, Object)}
     *
     * @param value        Value to lock this option to
     * @param replacements Arguments to be injected into the configuration path for any substitutable elements present.
     *                     See {@link #getPath(String...)} for documentation on this parameter.
     * @see #getPath(String...) for documentation on the {@code replacements} parameter.
     * @see Config#lock(String, Object)
     */
    public void lock(V value, String... replacements) {
        config.lock(getPath(replacements), value);
    }

    /**
     * Unlocks this Option if it was previously locked. If this Option was not previously locked, this method does not
     * achieve any further functionality. The path specified during the construction of this Option will be injected
     * with the supplied {@code replacements}.
     * <p/>
     * The return value of this method is dependent on the path replacements provided.
     * <p/>
     * This method proxies {@link Config#unlock(String)}
     *
     * @param replacements Arguments to be injected into the configuration path for any substitutable elements present.
     *                     See {@link #getPath(String...)} for documentation on this parameter.
     * @see #getPath(String...) for documentation on the {@code replacements} parameter.
     * @see Config#unlock(String)
     */
    public void unlock(String... replacements) {
        config.unlock(getPath(replacements));
    }
}