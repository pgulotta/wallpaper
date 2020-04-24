package com.example.wallpaper;

import android.util.Log;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.WallpaperManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

public final class WallpaperGenerator implements Runnable
{
  public static final String ID = "WallpaperGenerator";
  public static Context mContext;

  public static void generateWallpaper(Context context)
  {
    try {
      Thread thread = new Thread( new WallpaperGenerator( context ) );
      thread.start();
      thread.join();
      Log.i( ID, "WallpaperGenerator.generateWallpaper: set wallpaper " );
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }

  public WallpaperGenerator( Context context )
  {
    mContext = context;
  }

  @Override
  public void run()
  {
    try {
      byte[] array = getWallpaper();

      if ( array.length == 0 ) {
        Log.e( ID, "WallpaperGenerator.run failed. Wallpaper array is empty" );
        return;
      }

      setWallpaper( array );
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }

  public static byte[] getWallpaper()
  {
    final String REQUEST_METHOD = "GET";
    final int READ_TIMEOUT = 15000;
    final int CONNECTION_TIMEOUT = 15000;
    final String GET_REQUEST = "http://65.60.187.8:60564/wallpaper";

    ByteArrayOutputStream responseBody = null;

    try {
      final URL url = new URL( GET_REQUEST );
      Log.i( ID, "WallpaperGenerator.getWallpaper:  url = " + url );
      HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();

      if ( connection == null ) {
        Log.e( ID, "WallpaperGenerator.getWallpaper:  connection= null" );
        return null;
      }

      connection.setRequestMethod( REQUEST_METHOD );
      connection.setReadTimeout( READ_TIMEOUT );
      connection.setConnectTimeout( CONNECTION_TIMEOUT );
      connection.connect();

      InputStream   stream = connection.getInputStream();

      if ( stream == null ) {
        Log.e( ID, "WallpaperGenerator.getWallpaper:  stream= null" );
        return null;
      }

      byte buffer[] = new byte[1024];
      int bytesRead = 0;

      responseBody = new ByteArrayOutputStream();

      while ( ( bytesRead = stream.read( buffer ) ) > 0 ) {
        responseBody.write( buffer, 0, bytesRead );
      }

      connection.disconnect();
      stream.close();

    } catch ( Exception e ) {
      e.printStackTrace();
    }

    return responseBody == null ? null :  responseBody.toByteArray();
  }

  public static void setWallpaper( byte[] array )
  {
    try {

      if ( array.length == 0 ) {
        Log.e( ID, "WallpaperGenerator.setWallpaper:  array is empty" );
        return;
      }

      Log.i( ID, "WallpaperGenerator.setWallpaper:  array length = " + array.length );

      Bitmap destinationBitmap =  BitmapFactory.decodeByteArray( array, 0, array.length );

      if ( destinationBitmap == null ) {
        Log.e( ID, "WallpaperGenerator.setWallpaper: destinationBitmap = null" );
        return;
      }

      if ( mContext == null ) {
        Log.e( ID, "WallpaperGenerator.setWallpaper: mContext is null" );
        return;
      }

      final WallpaperManager wallpaperManager = WallpaperManager.getInstance( mContext );

      if ( wallpaperManager == null ) {
        Log.e( ID, "WallpaperGenerator.setWallpaper: wallpaperManager = null" );
        return;
      }

      Log.i( ID, "WallpaperGenerator.setWallpaper: destinationBitmap width = " + destinationBitmap.getWidth() );
      wallpaperManager.setBitmap ( destinationBitmap );

    } catch ( IOException e ) {
      e.printStackTrace();
    } catch ( Exception ex ) {
      ex.printStackTrace();
    }

  }

}

//   http://127.0.0.1:60564/test?lines
//   http://127.0.0.1:60564/test?quasiPeriodicStripes
//   http://127.0.0.1:60564/test?quasiTrap
//   http://127.0.0.1:60564/test?quasiTrapPoly
//   http://127.0.0.1:60564/test?walk2
//   http://127.0.0.1:60564/test?trap
//   http://127.0.0.1:60564/test?stripes
//   http://127.0.0.1:60564/test?squiggles



