#pragma once

#include <QObject>

class FrontController : public QObject
{
  Q_OBJECT
public:
  explicit FrontController( QObject* parent = nullptr );
  Q_INVOKABLE void generateWallpaper() const;


};


