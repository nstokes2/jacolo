package loaders.collada;

public class Collada {
    private Asset asset;
    private Geometry[] geometries;
    private Animation[] animations;
    private Light[] lights;
    private VisualScene[] visualScenes;

    Collada(Asset asset, Geometry[] geometries, Animation[] animations, Light[] lights, VisualScene[] visualScenes) {
        this.asset = asset;
        this.geometries = geometries;
        this.animations = animations;
        this.lights = lights;
        this.visualScenes = visualScenes;
    }

    public Collada() {}

    public Asset getAsset() {
        return asset;
    }

    public Geometry[] getGeometries() {
        return geometries;
    }

    public Animation[] getAnimations() {
        return animations;
    }

    public Light[] getLights() {
        return lights;
    }

    public VisualScene[] getVisualScenes() {
        return visualScenes;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public void setGeometries(Geometry[] geometries) {
        this.geometries = geometries;
    }

    public void setAnimations(Animation[] animations) {
        this.animations = animations;
    }

    public void setLights(Light[] lights) {
        this.lights = lights;
    }

    public void setVisualScenes(VisualScene[] visualScenes) {
        this.visualScenes = visualScenes;
    }
}