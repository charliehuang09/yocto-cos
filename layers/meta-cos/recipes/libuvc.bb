SUMMARY = "library for USB video devices built atop libusb"
HOMEPAGE = "https://github.com/libuvc/libuvc.git"
SECTION = "libs"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=2f1963e0bb88c93463af750daf9ba0c2"

DEPENDS = "libusb-compat jpeg"

SRC_URI = ""
SRC_URI += "git://github.com/libuvc/libuvc.git;protocol=https;branch=master"
SRC_URI += "file://libuvc.patch"

SRCREV = "047920bcdfb1dac42424c90de5cc77dfc9fba04d"

inherit cmake pkgconfig

EXTRA_OECMAKE:append = " -DCMAKE_POLICY_VERSION_MINIMUM=3.5"
