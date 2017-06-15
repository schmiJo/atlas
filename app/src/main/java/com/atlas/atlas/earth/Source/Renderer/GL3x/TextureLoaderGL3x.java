package com.atlas.atlas.earth.Source.Renderer.GL3x;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES31;
import android.opengl.GLUtils;

import com.atlas.atlas.earth.Source.Renderer.Texture;

public class TextureLoaderGL3x {

    public static void loadTexture(Context context, Texture texture) {
        Bitmap bitmap;
        if(texture.getResourceID()!=0) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling
              bitmap = BitmapFactory.decodeResource(context.getResources(), texture.getResourceID(), options);
        }else{
            bitmap = texture.getBitmap();
        }
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, texture.getTextureID());
        // Set filtering
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_NEAREST);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MAG_FILTER, GLES31.GL_NEAREST);
        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES31.GL_TEXTURE_2D, 0, bitmap, 0);
        // Recycle the bitmap, since its data has been loaded into OpenGL.
        bitmap.recycle();
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, 0);
    }
}
