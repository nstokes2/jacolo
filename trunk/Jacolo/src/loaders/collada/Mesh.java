package loaders.collada;

public class Mesh {
    private float[]
            vertexArray,
            normalArray,
            texCoordArray;

    private int[][] polygons;

    private int
            vertexStride,
            normalStride,
            texCoordStride;

    public Mesh(
            float[] vertexArray, int vertexStride,
            float[] normalArray, int normalStride,
            float[] texCoordArray, int texCoordStride,
            int[][] polygons) {
        this.vertexArray = vertexArray;
        this.normalArray = normalArray;
        this.texCoordArray = texCoordArray;
        this.polygons = polygons;
        this.vertexStride = vertexStride;
        this.normalStride = normalStride;
        this.texCoordStride = texCoordStride;
    }

    public void setPolygons(int[][] polygons) {
        this.polygons = polygons;
    }

    public int[][] getPolygons() {
        return polygons;
    }

    public void setVertexArray(float[] vertexArray, int vertexStride) {
        this.vertexArray = vertexArray;
        this.vertexStride = vertexStride;

    }

    public void setNormalArray(float[] normalArray, int normalStride) {
        this.normalArray = normalArray;
        this.normalStride = normalStride;
    }

    public void setTexCoordArray(float[] texCoordArray, int texCoordStride) {
        this.texCoordArray = texCoordArray;
        this.texCoordStride = texCoordStride;
    }

    public float[] getNormalArray() {
        return normalArray;
    }

    public int getNormalStride() {
        return normalStride;
    }

    public float[] getTexCoordArray() {
        return texCoordArray;
    }

    public int getTexCoordStride() {
        return texCoordStride;
    }

    public float[] getVertexArray() {
        return vertexArray;
    }

    public int getVertexStride() {
        return vertexStride;
    }

    public int getVertexCount(){
        return vertexArray.length/vertexStride;
    }

    public int getNormalCount(){
        return normalArray.length/normalStride;
    }

    public int getTexCoordCount(){
        return texCoordArray.length/texCoordStride;
    }
}