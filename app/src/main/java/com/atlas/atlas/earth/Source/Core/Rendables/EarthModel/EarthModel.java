package com.atlas.atlas.earth.Source.Core.Rendables.EarthModel;


import android.content.Context;

import com.atlas.atlas.R;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlas.earth.Source.Renderer.Texture;


public class EarthModel extends GeographicGlobe {


    public EarthModel(Context context) {
        super(new Vector3F(0, 0, 0), 90,0,0, 1, context);
        super.setTexture(configureDayTexture(R.drawable.topographic_day));
        super.setTexture(configureNightTexture(R.drawable.topographic_night));
    }

    /**
     * Setup
     */

    private Texture configureDayTexture(int path) {
        Texture texture = new Texture(path);
        texture.setReflectivity(0.05f);
        texture.setShineDamper(6f);
        return texture;
    }

    private Texture configureNightTexture(int path) {
        Texture texture = new Texture(path);
        texture.setReflectivity(0.05f);
        texture.setShineDamper(6f);
        return texture;
    }


    @Override
    public void increaseRotation(float dx, float dy, float dz) {
        if (super.getRotX() + dx > 360) {
            dx -= 360;
        }
        if (super.getRotY() + dy > 360) {
            dy -= 360;
        }
        if (super.getRotZ() + dz > 360) {
            dz -= 360;
        }
        if (super.getRotX() + dx < -360) {
            dx += 360;
        }
        if (super.getRotY() + dy < -360) {
            dy += 360;
        }
        if (super.getRotZ() + dz < -360) {
            dz += 360;
        }
        if (super.getRotX() + dz >= 180 && dx > 0) {
            dx = 0;
            super.setRotX(180);
        }
        if (super.getRotX() + dx < 0 && dx < 0) {
            dx = 0;
            super.setRotX(0);
        }
        super.increaseRotation(dx, dy, dz);
    }


}
