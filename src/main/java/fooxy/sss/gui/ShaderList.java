package fooxy.sss.gui;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Comparator;

public enum ShaderList {
    NONE(-1, "none"),
    NOTCH(0, "notch"),
    FXAA(1, "fxaa"),
    ART(2, "art"),
    BUMPY(3, "bumpy"),
    BLOBS2(4, "blobs2"),
    PENCIL(5, "pencil"),
    COLOR_CONVOLVE(6, "color_convolve"),
    DECONVERGE(7, "deconverge"),
    FLIP(8, "flip"),
    INVERT(9, "invert"),
    NTSC(10, "ntsc"),
    OUTLINE(11, "outline"),
    PHOSPHOR(12, "phosphor"),
    SCAN_PINCUSHION(13, "scan_pincushion"),
    SOBEL(14, "sobel"),
    BITS(15, "bits"),
    DESATURATE(16, "desaturate"),
    GREEN(17, "green"),
    BLUR(18, "blur"),
    WOBBLE(19, "wobble"),
    BLOBS(20, "blobs"),
    ANTIALIAS(21, "antialias"),
    CREEPER(22, "creeper"),
    SPIDER(23, "spider");

    private final int id;
    private final String name;
    public static final ShaderList[] l = Arrays.stream(ShaderList.values()).sorted(Comparator.comparingInt(ShaderList::getId)).toArray(ShaderList[]::new);
    public static ShaderList cur = NONE;

    ShaderList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ShaderList next() {
        int j = cur.getId() + 1;
        if (j > 23)
            j = -1;
        cur = l[j + 1];
        return cur;
    }

    public static ShaderList previous() {
        int j = cur.getId() - 1;
        if (j < -1)
            j = 23;
        cur = l[j + 1];
        return cur;
    }

    public Text getName() {
        return Text.translatable("fooxy.sss.shader." + this.name);
    }

    public int getId() {
        return this.id;
    }

    public Identifier getResource() {
        return new Identifier("shaders/post/" + this.name + ".json");
    }
}
