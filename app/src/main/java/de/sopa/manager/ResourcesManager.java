package de.sopa.manager;

import de.sopa.MainActivity;
import de.sopa.TileResourceLoader;
import java.util.Map;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

/**
 * David Schilling - davejs92@gmail.com
 */
public class ResourcesManager {
    private static final ResourcesManager INSTANCE = new ResourcesManager();

    public Engine engine;
    public MainActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    public ITextureRegion splash_region;
    private BitmapTextureAtlas splashTextureAtlas;

    public ITextureRegion play_region;

    public Map<Character, TextureRegion> regionTileMap;
    private BuildableBitmapTextureAtlas menuTextureAtlas;
    private TileResourceLoader tileResourceLoader;
    public ITextureRegion loadingScreenBackgroundRegion;
    public ITextureRegion level_mode_region;
    public ITextureRegion gameBackgroundRegion;


    public void loadMenuResources() {
        loadMenuGraphics();
    }

    public void loadGameResources() {
        loadGameGraphics();
    }

    private void loadMenuGraphics() {
        menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "screens/JustPlayA.png");
        level_mode_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "screens/LevelModeA.png");

        try
        {
            this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.menuTextureAtlas.load();
        }
        catch (final ITextureAtlasBuilder.TextureAtlasBuilderException e)
        {
            Debug.e(e);
        }
    }
    private void loadGameGraphics() {
        regionTileMap = this.tileResourceLoader.getTileTextures();
        gameBackgroundRegion = tileResourceLoader.getTexture("screens/stonebackoground.png");
    }

    public void loadLoadingResources() {
        loadingScreenBackgroundRegion = tileResourceLoader.getTexture("screens/LoadingScreen.png");
    }

    public void loadSplashScreen() {
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 200, 200, TextureOptions.BILINEAR);
        splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash/splash.png", 0, 0);
        splashTextureAtlas.load();
    }

    public void unloadSplashScreen() {
        splashTextureAtlas.unload();
        splash_region = null;
    }

    public static void prepareManager(Engine engine, MainActivity activity, Camera camera, VertexBufferObjectManager vbom, TileResourceLoader tileResourceLoader) {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
        getInstance().tileResourceLoader = tileResourceLoader;
    }

    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------

    public static ResourcesManager getInstance() {
        return INSTANCE;
    }

    public void unloadGameTextures() {
        for (TextureRegion textureRegion : regionTileMap.values()) {
            textureRegion.getTexture().unload();
        }
        regionTileMap = null;
    }

    public void unloadMenuTextures() {

    }
}
