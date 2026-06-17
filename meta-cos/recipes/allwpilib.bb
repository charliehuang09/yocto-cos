SUMMARY = "allwpilib"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE.md"

SRC_URI = ""
SRC_URI += "git://github.com/wpilibsuite/allwpilib.git;protocol=https;branch=main"
SRC_URI += "file://wpilibConfig.cmake"

SRCREV = "5a7d7d50ee54eaa12e8d44d273641a236c05d903"

DEPENDS += "opencv"
DEPENDS += "ninja-native"
DEPENDS += "patchelf-native"

inherit cmake pkgconfig

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
EXTRA_OECMAKE:append = " -DWITH_JAVA=OFF"

do_install:append(){
    find ${D} -type f -name "*.so*" -exec patchelf --remove-rpath {} \;
    rm -r ${D}/usr/share
    install -d ${D}${libdir}/cmake/wpilib
    install -m 0644 ${WORKDIR}/sources/wpilibConfig.cmake ${D}${libdir}/cmake/wpilib/
}

FILES:${PN} += "/usr/lib/*.so"
FILES:${PN} += "/usr/include/*"
FILES:${PN} += "/usr/lib/cmake/**"

FILES:${PN}-dev = "/usr/include/*"
