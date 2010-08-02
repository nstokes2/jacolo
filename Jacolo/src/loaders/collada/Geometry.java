package loaders.collada;

public class Geometry {
    private Mesh mesh;
    private String id, name;

    public Geometry(Mesh mesh, String id, String name) {
        this.mesh = mesh;
        this.id = id;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
