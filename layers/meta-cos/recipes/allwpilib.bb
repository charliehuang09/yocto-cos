SUMMARY = "allwpilib"
# WPILib and the bundled components enabled below (GUI and wpical are disabled).
LICENSE = "Apache-2.0 AND BSD-2-Clause AND BSD-3-Clause AND MIT AND MPL-2.0 AND NCSA AND Zlib AND Apache-2.0 WITH LLVM-exception"
LIC_FILES_CHKSUM = " \
    file://LICENSE.md;md5=c97a7ef0562fe3f2a810e9696d353b3e \
    file://ThirdPartyNotices.txt;md5=71e98200c8018ce1d3cfa9c7de5ed21f \
"

SRC_URI = "git://github.com/wpilibsuite/allwpilib.git;protocol=https;branch=main \
           file://0001-remove-checks-for-atomic.patch \
           "

SRCREV = "c89401250fbd412ba050f595551d0a451b7307a2"

DEPENDS += "opencv"
DEPENDS += "ninja-native"
DEPENDS += "patchelf-native"
DEPENDS += "protobuf protobuf-native"

inherit cmake pkgconfig cuda

EXTRA_OECMAKE = ""
EXTRA_OECMAKE:append = " -DBUILD_SHARED_LIBS=ON"
EXTRA_OECMAKE:append = " -DWITH_CSCORE=ON"
EXTRA_OECMAKE:append = " -DWITH_EXAMPLES=OFF"
EXTRA_OECMAKE:append = " -DWITH_GUI=OFF"
EXTRA_OECMAKE:append = " -DWITH_NTCORE=ON"
EXTRA_OECMAKE:append = " -DWITH_SIMULATION_MODULES=OFF"
EXTRA_OECMAKE:append = " -DWITH_TESTS=OFF"
EXTRA_OECMAKE:append = " -DWITH_WPILIB=ON"
EXTRA_OECMAKE:append = " -DWITH_WPIMATH=ON"
EXTRA_OECMAKE:append = " -DNO_WERROR=ON"
EXTRA_OECMAKE:append = " -DWITH_PROTOBUF=ON"
EXTRA_OECMAKE:append = " -DWITH_JAVA=OFF"
EXTRA_OECMAKE:append = " -DProtobuf_PROTOC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/protoc"

do_install:append() {
    install -d ${D}${docdir}/${PN}
    install -m 0644 ${S}/LICENSE.md ${S}/ThirdPartyNotices.txt ${D}${docdir}/${PN}/
}

FILES:${PN} += " ${libdir}/*.so"
FILES:${PN} += " /usr/share/**"
FILES:${PN} += " /usr/include/**"
FILES:${PN}-dev = " "

INSANE_SKIP:${PN} += "useless-rpaths"
