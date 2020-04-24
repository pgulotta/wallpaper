#include <QGuiApplication>
#include <QQmlApplicationEngine>
#include <QQmlContext>
#include "frontcontroller.hpp"


int main( int argc, char* argv[] )
{
  QCoreApplication::setAttribute( Qt::AA_EnableHighDpiScaling );

  QGuiApplication app( argc, argv );

  QQmlApplicationEngine engine;
  const QUrl url( QStringLiteral( "qrc:/main.qml" ) );
  QObject::connect( &engine, &QQmlApplicationEngine::objectCreated,
  &app, [url]( QObject * obj, const QUrl & objUrl ) {
    if ( !obj && url == objUrl ) {
      QCoreApplication::exit( -1 );
    }
  }, Qt::QueuedConnection );

  FrontController frontController{&app};
  engine.rootContext()->setContextProperty( "Controller", &frontController );
  engine.load( url );

  return app.exec();
}
