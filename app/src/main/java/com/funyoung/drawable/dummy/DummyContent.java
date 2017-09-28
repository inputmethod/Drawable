package com.funyoung.drawable.dummy;

import com.funyoung.drawable.R;
import com.funyoung.sound.DataItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static final List<String> ICONS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        // Add some sample items.
        addItem(createDummyItem(1, R.string.item_color_drawable_title, R.string.item_color_drawable_content, R.string.item_color_drawable_detail));
        addItem(createDummyItem(2, R.string.item_gradient_drawable_title, R.string.item_gradient_drawable_content, R.string.item_gradient_drawable_detail));
        addItem(createDummyItem(3, R.string.item_bitmap_drawable_title, R.string.item_bitmap_drawable_content, R.string.item_bitmap_drawable_detail));
        addItem(createDummyItem(4, R.string.item_nine_patch_drawable_title, R.string.item_nine_patch_drawable_content, R.string.item_nine_patch_drawable_detail));
        addItem(createDummyItem(5, R.string.item_inset_drawable_title, R.string.item_inset_drawable_content, R.string.item_inset_drawable_detail));
        addItem(createDummyItem(6, R.string.item_clip_drawable_title, R.string.item_clip_drawable_content, R.string.item_clip_drawable_detail));
        addItem(createDummyItem(7, R.string.item_scale_drawable_title, R.string.item_scale_drawable_content, R.string.item_scale_drawable_detail));
        addItem(createDummyItem(8, R.string.item_rotate_drawable_title, R.string.item_rotate_drawable_content, R.string.item_rotate_drawable_detail));
        addItem(createDummyItem(9, R.string.item_animation_drawable_title, R.string.item_animation_drawable_content, R.string.item_animation_drawable_detail));
        addItem(createDummyItem(10, R.string.item_layer_drawable_title, R.string.item_layer_drawable_content, R.string.item_layer_drawable_detail));
        addItem(createDummyItem(11, R.string.item_level_drawable_title, R.string.item_level_drawable_content, R.string.item_level_drawable_detail));
        addItem(createDummyItem(12, R.string.item_state_drawable_title, R.string.item_state_drawable_content, R.string.item_state_drawable_detail));
        addItem(createDummyItem(13, R.string.item_transition_drawable_title, R.string.item_transition_drawable_content, R.string.item_transition_drawable_detail));

        ICONS.add("sound_lolipop.png");
        ICONS.add("sound_machine.png");
        ICONS.add("sound_mashmollow.png");
        ICONS.add("sound_piano.png");
        ICONS.add("sound_pickaku.png");
        ICONS.add("sound_supermarry.png");
        ICONS.add("sound_system.png");
        ICONS.add("sound_water.png");
    }

    private static DummyItem createDummyItem(int id, int title, int content, int detail) {
        return new DummyItem(String.valueOf(id), title, content, detail);
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static List<DataItem> generateData() {
        int size = 10 + new Random(99999).nextInt(20);
        List<DataItem> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            DataItem item = new DataItem();
            item.str = ICONS.get(i % ICONS.size());
            result.add(item);
        }
        return result;
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final int title;
        public final int content;
        public final int details;

        public DummyItem(String id, int title, int content, int details) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
