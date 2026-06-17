SUMMARY = "Fix opencv4 for cos"
DESCRIPTION = "Recipe created by bitbake-layers"
LICENSE = "MIT"

EXTRA_OECMAKE:remove = " -DWITH_CUDA=ON"
EXTRA_OECMAKE:append = " -DWITH_CUDA=OFF"
