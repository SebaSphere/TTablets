package dev.sebastianb.ttablets.api;

import dev.sebastianb.ttablets.helper.ByteBuffer2D;
import dev.sebastianb.ttablets.helper.RunningTime;
import net.minecraft.util.Identifier;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

/**
 * Extend this class to create a custom TTablets application.
 */
public abstract class Application {

    private final String ID;
    private final TranslatableText NAME;
    private final Identifier ICON;
    private final Identifier LOADING_SCREEN;

    private boolean ACTIVE;
    final RunningTime RUNTIME;

    /**
     * Creates a new application, and registers it. You should load all your resources in this constructor, to eliminate lag when opening the app.
     * Applications should be registered when the client is finished loading.
     * @param ID Your application must have a unique ID. I suggest using "modid_nameofapp".
     * @param name The user-friendly name of the app.
     * @param icon The path to the user-friendly icon of the app. Size must be a power of 2. Recommended size is 64x64.
     * @param loadingScreen The path to the screen. It is currently 1144 x 912 pixels. It will display when your app starts.
     */
    public Application(@NotNull String ID, @NotNull TranslatableText name, @NotNull Identifier icon, @NotNull Identifier loadingScreen) {
        this.ID = ID;
        this.NAME = name;
        this.ICON = icon;
        this.LOADING_SCREEN = loadingScreen;
        this.RUNTIME = new RunningTime();
        this.register();
    }

    /**
     * Initialize all Minecraft resource-related things here. This function runs when loading a world.
     */
    public abstract void initResources();

    /**
     * Registers the application.
     */
    private void register() {
        ApplicationRegistry.APPLICATION_REGISTRY.addApplication(this);
    }

    /**
     * @return The ID of the application.
     */
    @NotNull
    public final String getID() {
        return this.ID;
    };

    /**
     * @return The user-friendly name of the app.
     */
    @NotNull
    public final TranslatableText getDisplayName() {
        return this.NAME;
    };

    /**
     * @return The path to the user-friendly icon of the app. Size must be a power of 2. Recommended size is 64x64.
     */
    @NotNull
    public final Identifier getIcon() {
        return this.ICON;
    };

    /**
     * @return The path to the loading screen of the application.
     */
    @NotNull
    public final Identifier getLoadingScreen() {
        return this.LOADING_SCREEN;
    };

    /**
     * @return Whether this application is running or not.
     */
    public final boolean getActive() {
        return this.ACTIVE;
    }

    /**
     * This function should only be called by the active Tablet OS. Please do not touch it!
     * @param active Whether the application is active or not.
     */
    public final void setActive(boolean active) {
        this.ACTIVE = active;
    }

    /**
     * @return The uptime of the application, in milliseconds.
     */
    public final long getUpTime() {
        return this.RUNTIME.getTime();
    }

    /**
     * @return The uptime of the application, in seconds.
     */
    public final int getUpTimeSeconds() {
        return this.RUNTIME.getTimeSeconds();
    }

    /**
     * Resets the uptime.
     */
    public final void resetUpTime() {
        this.RUNTIME.run();
    }

    /**
     * Renders the application to the screen. It is only called when the application is active. Please return the input image if nothing has been modified.
     * You can use a helper method in ByteBuffer2D to convert a BufferedImage to a ByteBuffer2D.
     *
     * TODO: it doesn't actually give you the real last frame right now, sorry about that
     *
     * @param previousFrame The previously drawn frame.
     * @param mouseX The X position of the mouse, from 0 to 1144.
     * @param mouseY The Y position of the mouse, from 0 to 912.
     * @return The ByteBuffer2D to render.
     */
    @NotNull
    public abstract ByteBuffer2D render(@NotNull final BufferedImage previousFrame, int mouseX, int mouseY);

    /**
     * Fired when the mouse is pressed and the application is active.
     * @param mouseX The X position of the mouse, from 0 to 1144.
     * @param mouseY The Y position of the mouse, from 0 to 912.
     */
    public void onMousePressed(int mouseX, int mouseY) {}

    /**
     * Fired when a key is pressed and the application is active.
     * @param keycode The key pressed.
     */
    public void onKeyPressed(int keycode) {}
}
