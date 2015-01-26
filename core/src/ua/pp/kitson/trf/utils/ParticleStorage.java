package ua.pp.kitson.trf.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

import ua.pp.kitson.trf.rockets.RocketColor;

/**
 * Created by serhii on 1/24/15.
 */
public class ParticleStorage {
    HashMap<RocketColor, ParticleEffectPool> storage;
    private static ParticleStorage ourInstance = new ParticleStorage();

    public static ParticleStorage getInstance() {
        return ourInstance;
    }

    private ParticleStorage() {
        storage = new HashMap<>();
        HashMap<RocketColor, String> names = new HashMap<>();
        names.put(RocketColor.WHITE,"effects/little.p");
        names.put(RocketColor.YELLOW,"effects/little1.p");
        names.put(RocketColor.BLUE,"effects/little2.p");
        //fill storage
        for (RocketColor rc: RocketColor.values()) {
            ParticleEffect particleEffect = new ParticleEffect();
            particleEffect.load(Gdx.files.internal(names.get(rc)), Gdx.files.internal("effects"));
            ParticleEffectPool pool = new ParticleEffectPool(particleEffect, 3, 3 * 36 + 3);
            storage.put(rc,pool);
        }
    }

    /*
     * Afer use must be called .free()
     */
    public ParticleEffectPool.PooledEffect getEffect(RocketColor rocketColor, Vector2 position) {
        ParticleEffectPool.PooledEffect eff = storage.get(rocketColor).obtain();
        eff.setPosition(position.x,position.y);
        return eff;
    }
}
/*
ParticleEffectPool bombEffectPool;
Array<PooledEffect> effects = new Array();
...
ParticleEffect bombEffect = new ParticleEffect();
bombEffect.load(Gdx.files.internal("particles/bomb.p"), atlas);
bombEffectPool = new ParticleEffectPool(bombEffect, 1, 2);
...
// Create effect:
PooledEffect effect = bombEffectPool.obtain();
effect.setPosition(x, y);
effects.add(effect);
...
// Update and draw effects:
for (int i = effects.size - 1; i >= 0; i--) {
    PooledEffect effect = effects.get(i);
    effect.draw(batch, delta);
    if (effect.isComplete()) {
        effect.free();
        effects.removeIndex(i);
    }
}
...
// Reset all effects:
for (int i = effects.size - 1; i >= 0; i--)
    effects.get(i).free();
effects.clear();
 */