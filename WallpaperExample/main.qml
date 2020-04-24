import QtQuick 2.14
import QtQuick.Window 2.14
import QtQuick.Controls 2.14

Window {
    readonly property bool isAndroidDevice: Qt.platform.os === "android"

    visible: true
    width: 200
    height: 200
    title: qsTr("Wallpaper Example")

    Button {
        id: wallpaperButtonId
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
        text: "Set Wallpaper"
        onClicked: {
            messageLabelId.text = null
            if (isAndroidDevice)
                Controller.generateWallpaper()
            messageLabelId.text = isAndroidDevice ? "Wallpaper set" : "Feature not supported"
        }
    }
    Label {
        id: messageLabelId
        anchors {
            top: wallpaperButtonId.bottom
            topMargin: 20
            horizontalCenter: parent.horizontalCenter
        }
    }
}
