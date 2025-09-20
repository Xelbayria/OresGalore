package net.xelbayria.ores_galore.api.intergration;


import net.xelbayria.ores_galore.api.set.OreTypeRegistry;
import org.jetbrains.annotations.ApiStatus;

import java.util.Set;

/**
 * Put all undetected OreType here. The following reasons can be seen via Definition of REASONS
 * And a few examples
 **/
// Put all undetected OreTypes here
@ApiStatus.Internal
public class HardcodedOreType {

    public static final Set<String> BLACKLISTED_ORETYPES = Set.of();

    /* Definition of REASONS:
     *
     * Spelling-Convention: a typo in the Id, no underscore
     *
     * 2-Words: The name of OreType is 2-Words instead of 1-Word
     */
    public static void init() {}

    static {

        OreTypeRegistry OreReg = OreTypeRegistry.INSTANCE;

    }
}
