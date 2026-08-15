. ./setup-env --machine p3768-0000-p3767-0001
cd ..
bitbake-layers add-layer layers/meta-cos/
bitbake-layers add-layer repos/meta-openembedded/meta-webserver

