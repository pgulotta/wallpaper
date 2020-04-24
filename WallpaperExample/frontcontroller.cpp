#include "frontcontroller.hpp"

#ifdef Q_OS_ANDROID
#include <QAndroidJniObject>
#include <QAndroidJniEnvironment>
#include <QtAndroid>
#endif


FrontController::FrontController( QObject* parent ) : QObject( parent )
{

}
#ifdef Q_OS_ANDROID
void FrontController::generateWallpaper() const
{
  QAndroidJniObject::callStaticMethod<void>( "com/example/wallpaper/WallpaperGenerator",
                                             "generateWallpaper",
                                             "(Landroid/content/Context;)V",
                                             QtAndroid::androidActivity().object() );
}
#else
void FrontController::generateWallpaper() const
{
  //this should never get called
}
#endif
