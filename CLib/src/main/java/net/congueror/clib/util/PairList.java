package net.congueror.clib.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class PairList<K, V> extends ArrayList<Pair<K, V>> {

    public PairList() {
        super();
    }

    public abstract void addMutable(K key, V value);

    public abstract void addImmutable(K key, V value);

    public abstract void setValue(K key, V value);

    public ListTag save(BiConsumer<CompoundTag, K> keyWriter, BiConsumer<CompoundTag, V> valueWriter) {
        ListTag list = new ListTag();
        forEach(p -> {
            CompoundTag c = new CompoundTag();
            keyWriter.accept(c, p.getKey());
            valueWriter.accept(c, p.getValue());
            list.add(c);
        });
        return list;
    }

    public void write(FriendlyByteBuf buf, BiConsumer<FriendlyByteBuf, K> keyWriter, BiConsumer<FriendlyByteBuf, V> valueWriter) {
        buf.writeCollection(this, (b, p) -> {
            keyWriter.accept(b, p.getKey());
            valueWriter.accept(b, p.getValue());
        });
    }

    public static class Default<K, V> extends PairList<K, V> {

        public Default() {
            super();
        }

        @Override
        public void addMutable(K key, V value) {
            this.add(new MutablePair<>(key, value));
        }

        @Override
        public void addImmutable(K key, V value) {
            this.add(new ImmutablePair<>(key, value));
        }

        @Override
        public void setValue(K key, V value) {
            for (int i = 0; i < this.size(); i++) {
                Pair<K, V> k = this.get(i);
                if (!(k instanceof ImmutablePair<K, V>) && k.getKey().equals(key)) {
                    k.setValue(value);
                }
            }
        }

        public List<V> get(K key) {
            List<V> list = new ArrayList<>();
            for (var k : this) {
                if (k.getKey().equals(key)) {
                    list.add(k.getValue());
                }
            }
            return list;
        }

        public static <K, V> PairList.Default<K, V> read(FriendlyByteBuf buf, Function<FriendlyByteBuf, K> keyReader, Function<FriendlyByteBuf, V> valueReader) {
            return buf.readCollection(value -> new PairList.Default<>(), friendlyByteBuf -> new MutablePair<>(keyReader.apply(friendlyByteBuf), valueReader.apply(friendlyByteBuf)));
        }

        public static <K, V> PairList.Default<K, V> load(CompoundTag nbt, String savedName, Function<CompoundTag, K> keyReader, Function<CompoundTag, V> valueReader) {
            ListTag list = nbt.getList(savedName, Tag.TAG_COMPOUND);
            PairList.Default<K, V> p = new PairList.Default<>();
            for (Tag i : list) {
                K key = keyReader.apply(((CompoundTag) i));
                V value = valueReader.apply(((CompoundTag) i));
                p.addMutable(key, value);
            }
            return p;
        }
    }

    public static class Unique<K, V> extends PairList<K, V> {

        public Unique() {
            super();
        }

        @Override
        public void addMutable(K key, V value) {
            if (this.get(key) == null)
                this.add(new MutablePair<>(key, value));
            else {
                throw new IllegalArgumentException("Attempted to add duplicate element in unique PairList");
            }
        }

        @Override
        public void addImmutable(K key, V value) {
            if (this.get(key) == null)
                this.add(new ImmutablePair<>(key, value));
            else {
                throw new IllegalArgumentException("Attempted to add duplicate element in unique PairList");
            }
        }

        @Override
        public void setValue(K key, V value) {
            for (int i = 0; i < this.size(); i++) {
                Pair<K, V> k = this.get(i);
                if (!(k instanceof ImmutablePair<K, V>) && k.getKey().equals(key)) {
                    k.setValue(value);
                    break;
                }
            }
        }

        public V get(K key) {
            for (var k : this) {
                if (k.getKey().equals(key)) {
                    return k.getValue();
                }
            }
            return null;
        }

        public static <K, V> PairList.Unique<K, V> read(FriendlyByteBuf buf, Function<FriendlyByteBuf, K> keyReader, Function<FriendlyByteBuf, V> valueReader) {
            return buf.readCollection(value -> new PairList.Unique<>(), friendlyByteBuf -> new MutablePair<>(keyReader.apply(friendlyByteBuf), valueReader.apply(friendlyByteBuf)));
        }

        public static <K, V> PairList.Unique<K, V> load(CompoundTag nbt, String savedName, Function<CompoundTag, K> keyReader, Function<CompoundTag, V> valueReader) {
            ListTag list = nbt.getList(savedName, Tag.TAG_COMPOUND);
            PairList.Unique<K, V> p = new PairList.Unique<>();
            for (Tag i : list) {
                K key = keyReader.apply(((CompoundTag) i));
                V value = valueReader.apply(((CompoundTag) i));
                p.addMutable(key, value);
            }
            return p;
        }
    }
}
