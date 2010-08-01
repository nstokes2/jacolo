package loaders.collada;

public class Asset {
    private String
            author,
            authoringTool,
            comments,
            created,
            modified,
            revision,
            title,
            subject,
            keywords,
            upAxis,
            unitName;
    private float unitValue;

    public Asset() {}

    public String getAuthor() {
        return author;
    }

    public String getAuthoringTool() {
        return authoringTool;
    }

    public String getComments() {
        return comments;
    }

    public String getKeywords() {
        return keywords;
    }

    public float getUnitValue() {
        return unitValue;
    }

    public String getRevision() {
        return revision;
    }

    public String getSubject() {
        return subject;
    }

    public String getTitle() {
        return title;
    }

    public String getUpAxis() {
        return upAxis;
    }

    public String getCreated() {
        return created;
    }

    public String getModified() {
        return modified;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAuthoringTool(String authoringTool) {
        this.authoringTool = authoringTool;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setUnitValue(float unitValue) {
        this.unitValue = unitValue;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpAxis(String upAxis) {
        this.upAxis = upAxis;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        String n = "    ASSET \n";
        n += "      author:           "+author+"\n";
        n += "      authoring_tool:   "+authoringTool+"\n";
        n += "      comments:         "+comments+"\n";
        n += "      created:          "+created+"\n";
        n += "      modified:         "+modified+"\n";
        n += "      revision:         "+revision+"\n";
        n += "      title:            "+title+"\n";
        n += "      subject:          "+subject+"\n";
        n += "      keywords:         "+keywords+"\n";
        n += "      unit:             "+unitName+" = "+unitValue+"\n";
        n += "      title:            "+upAxis+"\n";
        return n;
    }
}